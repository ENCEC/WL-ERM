package com.gillion.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
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
import com.share.auth.api.ShareAuthInterface;
import com.share.auth.api.UemUserInterface;
import com.share.auth.center.api.AuthCenterInterface;
import com.share.auth.domain.UemUserDto;
import com.share.support.model.User;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 任务信息管理
 * @author xuzt <xuzt@gillion.com.cn>
 * @date 2022/8/1
 */
@Service
public class TaskInfoServiceImpl implements TaskInfoService {

    @Autowired
    private UemUserInterface uemUserInterface;

    @Autowired
    private AuthCenterInterface authCenterInterface;

    @Autowired
    private ShareAuthInterface shareAuthInterface;

    private Snowflake snowflake = IdUtil.createSnowflake(RandomUtil.randomLong(1, 31), 1L);

    @Override
    public ResultHelper<Page<TaskInfoDto>> queryTaskInfoPage(TaskInfoDto taskInfoDto) {
        String taskTitle = taskInfoDto.getTaskTitle();
        if (!Objects.isNull(taskTitle)) {
            taskInfoDto.setTaskTitle("%" + taskTitle + "%");
        }
        int pageNo = taskInfoDto.getPageNo() == null ? 1 : taskInfoDto.getPageNo();
        int pageSize = taskInfoDto.getPageSize() == null ? 10 : taskInfoDto.getPageSize();
        Page<TaskInfoDto> taskInfoDtoPage = QTaskInfo.taskInfo
                .select(QTaskInfo.taskInfo.fieldContainer())
                .where(QTaskInfo.taskTitle.like(":taskTitle")
                        .and(QTaskInfo.taskType.eq(":taskType"))
                        .and(QTaskInfo.executor.eq(":executor"))
                        .and(QTaskInfo.status.eq(":status"))
                        .and(QTaskInfo.taskInfoId.notNull())) // 规避全表查询
                .paging(pageNo, pageSize)
                .mapperTo(TaskInfoDto.class)
                .sorting(QTaskInfo.createTime.desc())
                .execute(taskInfoDto);
        return CommonResult.getSuccessResultData(taskInfoDtoPage);
    }

    /**
     * 校验新增修改任务信息的参数
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-05
     */
    private String validateTaskInfo(TaskInfoDto taskInfoDto) {
        if (StrUtil.isEmpty(taskInfoDto.getTaskTitle())) {
            return "任务标题不能为空";
        }
        if (Objects.isNull(taskInfoDto.getExecutor())) {
            return "执行人不能为空";
        }
        if (StrUtil.isEmpty(taskInfoDto.getTaskType())) {
            return "在职状态不能为空";
        }
        if (Objects.isNull(taskInfoDto.getTaskDetailInfoDtoList()) || taskInfoDto.getTaskDetailInfoDtoList().size() <= 0)  {
            return "任务细则列表不能为空";
        }
        return null;
    }

    private String setTaskDetailInfoByStandardDetail(
            TaskInfoDto taskInfoDto, TaskInfo retTaskInfo, List<TaskDetailInfo> retTaskDetailInfoList
    ) {
        // 获取执行人信息
        UemUserDto executorUemUserDto = uemUserInterface.getUemUser(taskInfoDto.getExecutor()).getData();
        if (Objects.isNull(executorUemUserDto)) {
            return "执行人用户信息不存在";
        }
        // 获取分配人用户信息
        UemUserDto dispatchersUemUserDto = shareAuthInterface.getLoginUserInfo().getData();
        if (Objects.isNull(dispatchersUemUserDto) || Objects.isNull(dispatchersUemUserDto.getUemUserId())) {
            return "分配人用户信息不存在";
        }
        // 获取入职时间
        Date entryDate = executorUemUserDto.getEntryDate();
        // 生成ID
        long snowflakeId = snowflake.nextId();
        // 遍历选中标准条目细则并生成任务细节
        int seriesNum = 0;
        Date planStartDate = null;
        Date planEndDate = null;
        for (TaskDetailInfoDto taskDetailInfoDto : taskInfoDto.getTaskDetailInfoDtoList()) {
            if (taskDetailInfoDto.getStandardDetailId() == null) {
                return "规范细则ID不能为空";
            }
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
            taskDetailInfo.setTaskInfoId(snowflakeId);
            if (taskDetailInfo.getLeader() != null) {
                taskDetailInfo.setLeader(taskDetailInfoDto.getLeader());
            }
            taskDetailInfo.setOrdinator(standardDetailVo.getOrdinatorId());
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
            retTaskDetailInfoList.add(taskDetailInfo);
        }
        // 设置主表信息
        retTaskInfo.setTaskInfoId(snowflakeId);
        retTaskInfo.setTaskTitle(taskInfoDto.getTaskTitle());
        retTaskInfo.setExecutor(taskInfoDto.getExecutor());
        retTaskInfo.setTaskType(taskInfoDto.getTaskType());
        retTaskInfo.setExecutorName(executorUemUserDto.getName());
        retTaskInfo.setDispatchers(dispatchersUemUserDto.getUemUserId());
        retTaskInfo.setDispatchersName(dispatchersUemUserDto.getName());
        retTaskInfo.setPlanStartDate(planStartDate);
        retTaskInfo.setPlanEndDate(planEndDate);
        retTaskInfo.setStatus(0);
        retTaskInfo.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultHelper<Object> saveTaskInfo(TaskInfoDto taskInfoDto) {
        // 校验必填参数
        String errMsg = validateTaskInfo(taskInfoDto);
        if (errMsg != null) {
            return CommonResult.getFaildResultData(errMsg);
        }
        // 设置任务信息
        TaskInfo taskInfo = new TaskInfo();
        List<TaskDetailInfo> taskDetailInfoList = new LinkedList<>();
        errMsg = setTaskDetailInfoByStandardDetail(taskInfoDto, taskInfo, taskDetailInfoList);
        if (errMsg != null) {
            return CommonResult.getFaildResultData(errMsg);
        }
        QTaskInfo.taskInfo.save(taskInfo);
        QTaskDetailInfo.taskDetailInfo.save(taskDetailInfoList);
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
        String errMsg = validateTaskInfo(taskInfoDto);
        if (errMsg != null) {
            return CommonResult.getFaildResultData(errMsg);
        }
        // 清空原有选中
        QTaskDetailInfo.taskDetailInfo
                .delete()
                .where(QTaskDetailInfo.taskInfoId.eq$(taskInfoId))
                .execute();
        // 设置任务信息
        TaskInfo taskInfo = new TaskInfo();
        List<TaskDetailInfo> taskDetailInfoList = new LinkedList<>();
        errMsg = setTaskDetailInfoByStandardDetail(taskInfoDto, taskInfo, taskDetailInfoList);
        if (errMsg != null) {
            return CommonResult.getFaildResultData(errMsg);
        }
        QTaskInfo.taskInfo.save(taskInfo);
        QTaskDetailInfo.taskDetailInfo.save(taskDetailInfoList);
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

    @Override
    public ResultHelper<Page<StandardDetailVo>> queryStandardFullDetailByTaskType(TaskInfoDto taskInfoDto) {
        if (StrUtil.isEmpty(taskInfoDto.getTaskType())) {
            return CommonResult.getFaildResultData("任务类型不能为空");
        }
        int pageNo = taskInfoDto.getPageNo() == null ? 1 : taskInfoDto.getPageNo();
        int pageSize = taskInfoDto.getPageSize() == null ? 10 : taskInfoDto.getPageSize();
        Page<StandardDetailVo> standardDetailVoPage = DSContext
                .customization("WL-ERM_selectStandardDetailByTaskType")
                .select()
                .paging(pageNo, pageSize)
                .mapperTo(StandardDetailVo.class)
                .execute(taskInfoDto);
        return CommonResult.getSuccessResultData(standardDetailVoPage);
    }

    @Override
    public ResultHelper<List<StandardDetailVo>> queryNeedStandardFullDetailByTaskType(TaskInfoDto taskInfoDto) {
        if (StrUtil.isEmpty(taskInfoDto.getTaskType())) {
            return CommonResult.getFaildResultData("任务类型不能为空");
        }
        List<StandardDetailVo> standardDetailVoPage = DSContext
                .customization("WL-ERM_selectNeedStandardDetailByTaskType")
                .select()
                .mapperTo(StandardDetailVo.class)
                .execute(taskInfoDto);
        return CommonResult.getSuccessResultData(standardDetailVoPage);
    }

    @Override
    public ResultHelper<Page<StandardDetailVo>> queryNotNeedStandardFullDetailByTaskType(TaskInfoDto taskInfoDto) {
        if (StrUtil.isEmpty(taskInfoDto.getTaskType())) {
            return CommonResult.getFaildResultData("任务类型不能为空");
        }
        int pageNo = taskInfoDto.getPageNo() == null ? 1 : taskInfoDto.getPageNo();
        int pageSize = taskInfoDto.getPageSize() == null ? 10 : taskInfoDto.getPageSize();
        Page<StandardDetailVo> standardDetailVoPage = DSContext
                .customization("WL-ERM_selectNotNeedStandardDetailByTaskType")
                .select()
                .mapperTo(StandardDetailVo.class)
                .paging(pageNo, pageSize)
                .execute(taskInfoDto);
        return CommonResult.getSuccessResultData(standardDetailVoPage);
    }

    /**
     * 查询员工任务信息
     *
     * @param taskInfoDto 查询入参
     * @return com.share.support.result.ResultHelper<com.gillion.ds.client.api.queryobject.model.Page < com.gillion.model.domain.TaskInfoDto>>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-05
     */
    @Override
    public ResultHelper<Page<TaskInfoDto>> queryStaffTaskInfo(TaskInfoDto taskInfoDto) {
        User userModelInfo = authCenterInterface.getUserInfo();
        if (Objects.isNull(userModelInfo) || Objects.isNull(userModelInfo.getUemUserId())) {
            return CommonResult.getFaildResultData("请先登录！");
        }
        if (StrUtil.isNotBlank(taskInfoDto.getTaskTitle())) {
            taskInfoDto.setTaskTitle("%" + taskInfoDto.getTaskTitle() + "%");
        }
        Page<TaskInfoDto> taskInfoDtoList = QTaskInfo.taskInfo
                .select(QTaskInfo.taskInfo.fieldContainer())
                .where(QTaskInfo.executor.eq$(userModelInfo.getUemUserId())
                        .and(QTaskInfo.taskTitle.like(":taskTitle"))
                        .and(QTaskInfo.status.eq(":status")))
                .sorting(QTaskInfo.createTime.desc())
                .paging(taskInfoDto.getPageNo(), taskInfoDto.getPageSize())
                .mapperTo(TaskInfoDto.class)
                .execute(taskInfoDto);
        return CommonResult.getSuccessResultData(taskInfoDtoList);
    }

    /**
     * 查询员工任务详情信息
     *
     * @param taskInfoDto 查询入参
     * @return com.share.support.result.ResultHelper<com.gillion.ds.client.api.queryobject.model.Page < com.gillion.model.domain.TaskInfoDto>>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-08
     */
    @Override
    public ResultHelper<Page<TaskDetailInfoDto>> queryStaffTaskDetail(TaskInfoDto taskInfoDto) {
        Page<TaskDetailInfoDto> taskDetailInfoDtoPage = QTaskDetailInfo.taskDetailInfo
                .select(QTaskDetailInfo.taskDetailInfo.fieldContainer())
                .where(QTaskDetailInfo.taskInfoId.eq$(taskInfoDto.getTaskInfoId()))
                .paging(taskInfoDto.getPageNo(), taskInfoDto.getPageSize())
                .mapperTo(TaskDetailInfoDto.class)
                .execute();
        return CommonResult.getSuccessResultData(taskDetailInfoDtoPage);
    }

    /**
     * 更新任务进度
     *
     * @param taskDetailInfoDtoList 任务细则ID和进度数值
     * @return com.share.support.result.ResultHelper<java.lang.String>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-08
     */
    @Override
    public ResultHelper<String> updateTaskDetailProgress(List<TaskDetailInfoDto> taskDetailInfoDtoList) {
        if (taskDetailInfoDtoList == null || taskDetailInfoDtoList.isEmpty()) {
            return CommonResult.getFaildResultData("传入参数不能为空");
        }
        List<TaskDetailInfo> taskDetailInfoList = new LinkedList<>();
        for (TaskDetailInfoDto taskDetailInfoDto : taskDetailInfoDtoList) {
            Long taskDetailId = taskDetailInfoDto.getTaskDetailId();
            String progress = taskDetailInfoDto.getProgress();
            if (taskDetailId == null || progress == null) {
                return CommonResult.getFaildResultData("任务细则ID和进度不能为空");
            }
            TaskDetailInfo taskDetailInfo = new TaskDetailInfo();
            taskDetailInfo.setTaskDetailId(taskDetailId);
            taskDetailInfo.setProgress(progress);
            taskDetailInfo.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
            taskDetailInfoList.add(taskDetailInfo);
        }
        int rowCount = QTaskDetailInfo.taskDetailInfo
                .selective(QTaskDetailInfo.progress)
                .update(taskDetailInfoList);
        if (rowCount > 0) {
            return CommonResult.getSuccessResultData("更新成功");
        } else {
            return CommonResult.getFaildResultData("更新失败");
        }
    }

    /**
     * 查询负责人任务信息
     *
     * @param taskInfoDto 查询入参
     * @return com.share.support.result.ResultHelper<com.gillion.ds.client.api.queryobject.model.Page < com.gillion.model.domain.TaskInfoDto>>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-05
     */
    @Override
    public ResultHelper<Page<TaskInfoDto>> queryLeaderTaskInfo(TaskInfoDto taskInfoDto) {
        User userModelInfo = authCenterInterface.getUserInfo();
        Map<String, Object> params = new HashMap<>();
        if (Objects.isNull(userModelInfo) || Objects.isNull(userModelInfo.getUemUserId())) {
            return CommonResult.getFaildResultData("请先登录！");
        }
        String taskTitle = StrUtil.isEmpty(taskInfoDto.getTaskTitle()) ? "" : "%" + taskInfoDto.getTaskTitle() + "%";
        List<Integer> status;
        if (Objects.isNull(taskInfoDto.getStatus())) {
            status = Arrays.asList(0,1,2,3);
        } else  {
            status = Arrays.asList(taskInfoDto.getStatus());
        }
        params.put("leader", userModelInfo.getUemUserId());
        params.put("taskTitle", taskTitle);
        params.put("status", status);
        Page<TaskInfoDto> taskInfoDtoPage = DSContext
                .customization("WL-ERM_selectTaskInfoByLeader")
                .select()
                .paging(taskInfoDto.getPageNo(), taskInfoDto.getPageSize())
                .mapperTo(TaskInfoDto.class)
                .execute(params);
        return CommonResult.getSuccessResultData(taskInfoDtoPage);
    }

    /**
     * 更新任务完成状态
     *
     * @param taskDetailInfoDtoList 查询入参
     * @return com.share.support.result.ResultHelper<java.lang.String>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-08
     */
    @Override
    public ResultHelper<String> updateTaskDetailStatus(List<TaskDetailInfoDto> taskDetailInfoDtoList) {
        if (taskDetailInfoDtoList == null || taskDetailInfoDtoList.isEmpty()) {
            return CommonResult.getFaildResultData("传入参数不能为空");
        }
        List<TaskDetailInfo> taskDetailInfoList = new LinkedList<>();
        for (TaskDetailInfoDto taskDetailInfoDto : taskDetailInfoDtoList) {
            Long taskDetailId = taskDetailInfoDto.getTaskDetailId();
            Integer status = taskDetailInfoDto.getStatus();
            String resultAccess = taskDetailInfoDto.getResultAccess();
            if (taskDetailId == null || status == null || StrUtil.isEmpty(resultAccess)) {
                return CommonResult.getFaildResultData("任务细则ID、完成情况和完成结果不能为空");
            }
            TaskDetailInfo taskDetailInfo = new TaskDetailInfo();
            taskDetailInfo.setTaskDetailId(taskDetailId);
            taskDetailInfo.setStatus(status);
            taskDetailInfo.setResultAccess(resultAccess);
            taskDetailInfo.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
            taskDetailInfoList.add(taskDetailInfo);
        }
        int rowCount = QTaskDetailInfo.taskDetailInfo
                .selective(QTaskDetailInfo.status, QTaskDetailInfo.resultAccess)
                .update(taskDetailInfoList);
        TaskDetailInfo taskDetailInfo = QTaskDetailInfo.taskDetailInfo
                .selectOne(QTaskDetailInfo.taskInfoId)
                .byId(taskDetailInfoList.get(0).getTaskDetailId());
        Long taskInfoId = taskDetailInfo.getTaskInfoId();
        long finishCount = QTaskDetailInfo.taskDetailInfo
                .selectCount(QTaskDetailInfo.taskInfoId.count())
                .where(QTaskDetailInfo.taskInfoId.eq$(taskDetailInfo.getTaskInfoId())
                        .and(QTaskDetailInfo.status.eq$(2)))
                .execute();
        long totalCount = QTaskDetailInfo.taskDetailInfo
                .selectCount(QTaskDetailInfo.taskInfoId.count())
                .where(QTaskDetailInfo.taskInfoId.eq$(taskDetailInfo.getTaskInfoId()))
                .execute();
        if (finishCount == totalCount) {
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setTaskInfoId(taskInfoId);
            taskInfo.setStatus(2);
            taskInfo.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
            QTaskInfo.taskInfo.selective(QTaskInfo.status)
                    .update(taskInfo);
        }
        if (rowCount > 0) {
            return CommonResult.getSuccessResultData("更新成功");
        } else {
            return CommonResult.getFaildResultData("更新失败");
        }
    }

    /**
     * 查询统筹人任务信息
     *
     * @param taskInfoDto 查询入参
     * @return com.share.support.result.ResultHelper<com.gillion.ds.client.api.queryobject.model.Page < com.gillion.model.domain.TaskInfoDto>>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-05
     */
    @Override
    public ResultHelper<Page<TaskInfoDto>> queryOrdinatorTaskInfo(TaskInfoDto taskInfoDto) {
        User userModelInfo = authCenterInterface.getUserInfo();
        Map<String, Object> params = new HashMap<>();
        if (Objects.isNull(userModelInfo) || Objects.isNull(userModelInfo.getUemUserId())) {
            return CommonResult.getFaildResultData("请先登录！");
        }
        String taskTitle = StrUtil.isEmpty(taskInfoDto.getTaskTitle()) ? "" : "%" + taskInfoDto.getTaskTitle() + "%";
        String ordinator = "%" + userModelInfo.getUemUserId() + "%";
        List<Integer> status;
        if (Objects.isNull(taskInfoDto.getStatus())) {
            status = Arrays.asList(0,1,2);
        } else  {
            status = Arrays.asList(taskInfoDto.getStatus());
        }
        params.put("ordinator", ordinator);
        params.put("taskTitle", taskTitle);
        params.put("status", status);
        Page<TaskInfoDto> taskInfoDtoPage = DSContext
                .customization("WL-ERM_selectTaskInfoByOrdinator")
                .select()
                .paging(taskInfoDto.getPageNo(), taskInfoDto.getPageSize())
                .mapperTo(TaskInfoDto.class)
                .execute(params);
        return CommonResult.getSuccessResultData(taskInfoDtoPage);
    }
}
