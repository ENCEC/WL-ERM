package com.gillion.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.gillion.ds.client.DSContext;
import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.gillion.model.domain.TaskDetailInfoDto;
import com.gillion.model.domain.TaskInfoDto;
import com.gillion.model.entity.TaskDetailInfo;
import com.gillion.model.entity.TaskInfo;
import com.gillion.model.querymodels.QTaskDetailInfo;
import com.gillion.model.querymodels.QTaskInfo;
import com.gillion.model.vo.StandardDetailVo;
import com.gillion.service.TaskInfoService;
import com.share.auth.api.UemUserInterface;
import com.share.auth.domain.UemUserDto;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 任务信息管理
 * @author xuzt <xuzt@gillion.com.cn>
 * @date 2022/8/1
 */
@Service
public class TaskInfoServiceImpl implements TaskInfoService {

    @Autowired
    private UemUserInterface uemUserInterface;

    @Override
    public ResultHelper<Page<TaskInfoDto>> queryTaskInfoPage(TaskInfoDto taskInfoDto) {
        String taskTitle = taskInfoDto.getTaskTitle();
        if (!Objects.isNull(taskTitle)) {
            taskInfoDto.setTaskTitle("%" + taskTitle + "%");
        }
        int pageNo = taskInfoDto.getPageNo() == null ? 1 : taskInfoDto.getPageNo();
        int pageSize = taskInfoDto.getPageSize() == null ? 10 : taskInfoDto.getPageSize();
        Page<TaskInfoDto> taskInfoDtoPage = QTaskInfo.taskInfo
                .select()
                .where(QTaskInfo.taskTitle.like(":taskTitle")
                        .and(QTaskInfo.taskType.eq(":taskType"))
                        .and(QTaskInfo.executor.eq(":executor"))
                        .and(QTaskInfo.status.eq(":status")))
                .paging(pageNo, pageSize)
                .mapperTo(TaskInfoDto.class)
                .execute(taskInfoDto);
        return CommonResult.getSuccessResultData(taskInfoDtoPage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultHelper<Object> saveTaskInfo(TaskInfoDto taskInfoDto) {
        // 校验必填参数
        if (StrUtil.isEmpty(taskInfoDto.getTaskTitle())) {
            return CommonResult.getFaildResultData("任务标题不能为空");
        }
        if (Objects.isNull(taskInfoDto.getExecutor())) {
            return CommonResult.getFaildResultData("执行人不能为空");
        }
        if (StrUtil.isEmpty(taskInfoDto.getTaskType())) {
            return CommonResult.getFaildResultData("在职状态不能为空");
        }
        // 获取执行人信息
        ResultHelper<UemUserDto> uemUserDtoResultHelper = uemUserInterface.getUemUser(taskInfoDto.getExecutor());
        UemUserDto uemUserDto = uemUserDtoResultHelper.getData();
        if (Objects.isNull(uemUserDto)) {
            return CommonResult.getFaildResultData("执行人用户信息不存在");
        }
        // 获取入职时间
        Date entryDate = uemUserDto.getEntryDate();
        // 设置主表信息
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setTaskTitle(taskInfoDto.getTaskTitle());
        taskInfo.setExecutor(taskInfoDto.getExecutor());
        taskInfo.setTaskType(taskInfoDto.getTaskType());
        taskInfo.setStatus(0);
        taskInfo.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        QTaskInfo.taskInfo.save(taskInfo);
        // 遍历选中标准条目细则并生成任务细节
        List<TaskDetailInfo> taskDetailInfoList = new LinkedList<>();
        int seriesNum = 0;
        Date planStartDate = null;
        Date planEndDate = null;
        for (TaskDetailInfoDto taskDetailInfoDto : taskInfoDto.getTaskDetailInfoDtoList()) {
            // 查询标准主表和子表内容
            StandardDetailVo standardDetailVo = DSContext
                    .customization("WL-ERM_selectStandardDetailById")
                    .selectOne()
                    .mapperTo(StandardDetailVo.class)
                    .execute(taskDetailInfoDto);
            // 计算条目起始时间
            Date startDate = DateUtil.offsetDay(entryDate, standardDetailVo.getActionTime()).toJdkDate();
            Date endDate = DateUtil.offsetHour(startDate, standardDetailVo.getActionPeriod()).toJdkDate();
            // 设置任务细则
            TaskDetailInfo taskDetailInfo = new TaskDetailInfo();
            taskDetailInfo.setTaskInfoId(taskInfo.getTaskInfoId());
            taskDetailInfo.setLeader(taskDetailInfoDto.getLeader());
            taskDetailInfo.setOrdinator(Long.parseLong(standardDetailVo.getOrdinatorId()));
            taskDetailInfo.setStandardEntryId(standardDetailVo.getStandardEntryId());
            taskDetailInfo.setStandardEntryName(standardDetailVo.getEntryName());
            taskDetailInfo.setStandardDetailId(standardDetailVo.getStandardDetailId());
            taskDetailInfo.setStandardDetailName(standardDetailVo.getDetailName());
            taskDetailInfo.setActionTime(standardDetailVo.getActionTime());
            taskDetailInfo.setActionPeriod(standardDetailVo.getActionPeriod());
            taskDetailInfo.setActionSerialNum(seriesNum++);
            taskDetailInfo.setPlanStartDate(startDate);
            taskDetailInfo.setPlanEndDate(endDate);
            taskDetailInfo.setStatus(0);
            taskDetailInfo.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
            // 设置总体起始时间
            if (planStartDate == null) {
                planStartDate = startDate;
            }
            planEndDate = endDate;
        }
        // 保存任务并更新任务细则
        QTaskDetailInfo.taskDetailInfo.save(taskDetailInfoList);
        taskInfo.setPlanStartDate(planStartDate);
        taskInfo.setPlanEndDate(planEndDate);
        taskInfo.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        QTaskInfo.taskInfo.save(taskInfo);
        return CommonResult.getSuccessResultData("新增条目成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultHelper<Object> updateTaskInfo(TaskInfoDto taskInfoDto) {
        Long taskInfoId = taskInfoDto.getTaskInfoId();
        // 校验必填参数
        if (Objects.isNull(taskInfoId)) {
            return CommonResult.getFaildResultData("任务信息ID不能为空");
        }
        if (StrUtil.isEmpty(taskInfoDto.getTaskTitle())) {
            return CommonResult.getFaildResultData("任务标题不能为空");
        }
        if (Objects.isNull(taskInfoDto.getExecutor())) {
            return CommonResult.getFaildResultData("执行人不能为空");
        }
        if (StrUtil.isEmpty(taskInfoDto.getTaskType())) {
            return CommonResult.getFaildResultData("在职状态不能为空");
        }
        // 获取执行人信息
        ResultHelper<UemUserDto> uemUserDtoResultHelper = uemUserInterface.getUemUser(taskInfoDto.getExecutor());
        UemUserDto uemUserDto = uemUserDtoResultHelper.getData();
        if (Objects.isNull(uemUserDto)) {
            return CommonResult.getFaildResultData("执行人用户信息不存在");
        }
        // 查询任务信息
        TaskInfo taskInfo = QTaskInfo.taskInfo.selectOne().byId(taskInfoId);
        // 清空原有选中
        QTaskDetailInfo.taskDetailInfo
                .delete()
                .where(QTaskDetailInfo.taskInfoId.eq$(taskInfoId))
                .execute();
        // 获取入职时间
        Date entryDate = uemUserDto.getEntryDate();
        // 遍历选中标准条目细则并生成任务细节
        List<TaskDetailInfo> taskDetailInfoList = new LinkedList<>();
        int seriesNum = 0;
        Date planStartDate = null;
        Date planEndDate = null;
        for (TaskDetailInfoDto taskDetailInfoDto : taskInfoDto.getTaskDetailInfoDtoList()) {
            // 查询标准主表和子表内容
            StandardDetailVo standardDetailVo = DSContext
                    .customization("WL-ERM_selectStandardDetailById")
                    .selectOne()
                    .mapperTo(StandardDetailVo.class)
                    .execute(taskDetailInfoDto);
            // 计算条目起始时间
            Date startDate = DateUtil.offsetDay(entryDate, standardDetailVo.getActionTime()).toJdkDate();
            Date endDate = DateUtil.offsetHour(startDate, standardDetailVo.getActionPeriod()).toJdkDate();
            // 设置任务细则
            TaskDetailInfo taskDetailInfo = new TaskDetailInfo();
            taskDetailInfo.setTaskInfoId(taskInfo.getTaskInfoId());
            taskDetailInfo.setLeader(taskDetailInfoDto.getLeader());
            taskDetailInfo.setOrdinator(Long.parseLong(standardDetailVo.getOrdinatorId()));
            taskDetailInfo.setStandardEntryId(standardDetailVo.getStandardEntryId());
            taskDetailInfo.setStandardEntryName(standardDetailVo.getEntryName());
            taskDetailInfo.setStandardDetailId(standardDetailVo.getStandardDetailId());
            taskDetailInfo.setStandardDetailName(standardDetailVo.getDetailName());
            taskDetailInfo.setActionTime(standardDetailVo.getActionTime());
            taskDetailInfo.setActionPeriod(standardDetailVo.getActionPeriod());
            taskDetailInfo.setActionSerialNum(seriesNum++);
            taskDetailInfo.setPlanStartDate(startDate);
            taskDetailInfo.setPlanEndDate(endDate);
            taskDetailInfo.setStatus(0);
            taskDetailInfo.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
            // 设置总体起始时间
            if (planStartDate == null) {
                planStartDate = startDate;
            }
            planEndDate = endDate;
        }
        // 保存任务并更新任务细则
        QTaskDetailInfo.taskDetailInfo.save(taskDetailInfoList);
        taskInfo.setTaskTitle(taskInfoDto.getTaskTitle());
        taskInfo.setExecutor(taskInfoDto.getExecutor());
        taskInfo.setTaskType(taskInfoDto.getTaskType());
        taskInfo.setPlanStartDate(planStartDate);
        taskInfo.setPlanEndDate(planEndDate);
        taskInfo.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        QTaskInfo.taskInfo.save(taskInfo);
        return CommonResult.getSuccessResultData("更新条目成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultHelper<TaskInfoDto> getTaskInfoDetail(Long taskInfoId) {
        if (Objects.isNull(taskInfoId)) {
            return CommonResult.getFaildResultData("任务ID不能为空");
        }
        TaskInfoDto taskInfoDto = QTaskInfo.taskInfo
                .selectOne()
                .where(QTaskInfo.taskInfoId.eq$(taskInfoId))
                .mapperTo(TaskInfoDto.class)
                .execute();
        List<TaskDetailInfoDto> taskDetailInfoDtoList = QTaskDetailInfo.taskDetailInfo
                .select()
                .where(QTaskDetailInfo.taskInfoId.eq$(taskInfoId))
                .sorting(QTaskDetailInfo.actionSerialNum.asc())
                .mapperTo(TaskDetailInfoDto.class)
                .execute();
        taskInfoDto.setTaskDetailInfoDtoList(taskDetailInfoDtoList);
        return CommonResult.getSuccessResultData(taskInfoDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultHelper<Object> deleteTaskInfo(Long taskInfoId) {
        QTaskDetailInfo.taskDetailInfo
                .delete()
                .where(QTaskDetailInfo.taskInfoId.eq$(taskInfoId))
                .execute();
        QTaskInfo.taskInfo.deleteById(taskInfoId);
        return CommonResult.getSuccessResultData("删除条目成功");
    }
}
