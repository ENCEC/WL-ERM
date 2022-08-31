package com.gillion.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.gillion.ds.client.DSContext;
import com.gillion.ds.client.api.queryobject.expressions.AndExpression;
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
import com.gillion.train.api.model.vo.TaskDetailInfoDTO;
import com.share.auth.api.TaskInfoInterface;
import com.share.auth.api.UemUserInterface;
import com.share.auth.center.api.AuthCenterInterface;
import com.share.auth.domain.UemUserDto;
import com.share.support.model.User;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 任务信息管理
 *
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
    private TaskInfoInterface taskInfoInterface;

    private final List<String> taskTypes1 = Arrays.asList("试用任务", "培训任务", "学习任务", "其他任务");
    private final List<String> taskTypes2 = Arrays.asList("员工转正", "员工离职");
    private final List<String> taskTypesAll = Arrays.asList("试用任务", "培训任务", "学习任务", "其他任务", "员工转正", "员工离职");

    /**
     * 任务管理模块条件查询
     *
     * @param taskInfoDto 查询参数
     * @return com.share.support.result.ResultHelper<com.gillion.ds.client.api.queryobject.model.Page < com.gillion.model.domain.TaskInfoDto>>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-19
     */
    @Override
    public ResultHelper<Page<TaskInfoDto>> queryTaskInfoPage(TaskInfoDto taskInfoDto) {
        User user = authCenterInterface.getUserInfo();
        if (user == null || user.getUemUserId() == null) {
            return CommonResult.getFaildResultData("用户未登录或没有权限！");
        }
        Long uemUserId = user.getUemUserId();
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
                        .and(QTaskInfo.dispatchers.eq$(uemUserId))
                        .and(QTaskInfo.taskType.in$(taskTypes1)))
                .paging(pageNo, pageSize)
                .mapperTo(TaskInfoDto.class)
                .sorting(QTaskInfo.createTime.desc())
                .execute(taskInfoDto);
        return CommonResult.getSuccessResultData(taskInfoDtoPage);
    }

    /**
     * 校验新增修改任务信息的参数
     *
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
        if (Objects.isNull(taskInfoDto.getTaskDetailInfoDtoList()) || taskInfoDto.getTaskDetailInfoDtoList().size() <= 0) {
            return "任务细则列表不能为空";
        }
        return null;
    }

    /**
     * 新增和更新（培训任务、学习任务、试用任务、其他任务）逻辑
     *
     * @param taskInfoDto           上传数据
     * @param retTaskInfo           生成任务主表数据
     * @param retTaskDetailInfoList 生成任务子表数据
     * @param isUpdate              是否为更新
     * @return java.lang.String
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-19
     */
    private String setTaskDetailInfoByStandardDetail(
            TaskInfoDto taskInfoDto, TaskInfo retTaskInfo, List<TaskDetailInfo> retTaskDetailInfoList, boolean isUpdate
    ) {
        String taskType = taskInfoDto.getTaskType();
        if (!taskTypes1.contains(taskType)) {
            return "任务类型不存在";
        }
        HashMap<Long, UemUserDto> queryUemUserMap = new HashMap<>();
        List<Long> queryUemUserIdList = new ArrayList<>();
        // 获取执行人信息
        UemUserDto executorUemUserDto = uemUserInterface.getUemUser(taskInfoDto.getExecutor()).getData();
        if (Objects.isNull(executorUemUserDto)) {
            return "执行人用户信息不存在";
        }
        queryUemUserMap.put(executorUemUserDto.getUemUserId(), executorUemUserDto);
        // 获取分配人用户信息
        User dispatcherUserInfo = authCenterInterface.getUserInfo();
        if (Objects.isNull(dispatcherUserInfo) || Objects.isNull(dispatcherUserInfo.getUemUserId())) {
            return "分配人用户信息不存在";
        }
        // 获取入职时间
        Date entryDate = executorUemUserDto.getEntryDate();
        // 遍历选中标准条目细则并生成任务细节
        int seriesNum = 1;
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
            if (taskDetailInfoDto.getLeader() == null) {
                return "负责人不能为空";
            }
            TaskDetailInfo taskDetailInfo = new TaskDetailInfo();
            taskDetailInfo.setTaskInfoId(retTaskInfo.getTaskInfoId());
            // 设置负责人
            taskDetailInfo.setLeader(taskDetailInfoDto.getLeader());
            queryUemUserIdList.add(taskDetailInfoDto.getLeader());
            // 设置统筹人
            List<String> ordinatorIdList = StrUtil.splitTrim(standardDetailVo.getOrdinatorId(), ",");
            for (String ordinatorId : ordinatorIdList) {
                queryUemUserIdList.add(Long.parseLong(ordinatorId));
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
            taskDetailInfo.setStartDate(startDate);
            taskDetailInfo.setStatus(0);
            taskDetailInfo.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
            // 设置总体起始时间
            if (planStartDate == null) {
                planStartDate = startDate;
            }
            planEndDate = endDate;
            retTaskDetailInfoList.add(taskDetailInfo);
        }
        // 查询用户
        UemUserDto queryData = new UemUserDto();
        queryData.setUemUserIdList(queryUemUserIdList);
        ResultHelper<List<UemUserDto>> userListResult = uemUserInterface.queryUemUserListById(queryData);
        if (userListResult.getSuccess()) {
            for (UemUserDto uemUserDto : userListResult.getData()) {
                queryUemUserMap.put(uemUserDto.getUemUserId(), uemUserDto);
            }
        }
        for (TaskDetailInfo taskDetailInfo : retTaskDetailInfoList) {
            Long leaderId = taskDetailInfo.getLeader();
            String leaderName = queryUemUserMap.get(leaderId).getName();
            if (leaderName == null) {
                return "获取用户信息失败！";
            }
            taskDetailInfo.setLeaderName(leaderName);
            List<String> ordinatorIdList = StrUtil.splitTrim(taskDetailInfo.getOrdinator(), ",");
            StringBuilder ordinatorNames = new StringBuilder();
            for (String ordinatorIdStr : ordinatorIdList) {
                Long ordinatorId = Long.parseLong(ordinatorIdStr);
                String ordinatorName = queryUemUserMap.get(ordinatorId).getName();
                if (ordinatorName == null) {
                    return "获取用户信息失败！";
                }
                ordinatorNames.append(ordinatorName);
                ordinatorNames.append(',');
            }
            taskDetailInfo.setOrdinatorName(ordinatorNames.toString());
        }
        // 设置主表信息
        retTaskInfo.setTaskTitle(taskInfoDto.getTaskTitle());
        retTaskInfo.setExecutor(taskInfoDto.getExecutor());
        retTaskInfo.setTaskType(taskInfoDto.getTaskType());
        retTaskInfo.setExecutorName(executorUemUserDto.getName());
        retTaskInfo.setDispatchers(dispatcherUserInfo.getUemUserId());
        retTaskInfo.setDispatchersName(dispatcherUserInfo.getName());
        retTaskInfo.setPlanStartDate(planStartDate);
        retTaskInfo.setPlanEndDate(planEndDate);
        retTaskInfo.setPublishDate(new Date());
        retTaskInfo.setStartDate(planStartDate);
        retTaskInfo.setStatus(0);
        retTaskInfo.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        return null;
    }

    /**
     * 新增培训任务、学习任务、试用任务、其他任务
     *
     * @param taskInfoDto 入参
     * @return com.share.support.result.ResultHelper<java.lang.Object>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-19
     */
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
        taskInfo.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        QTaskInfo.taskInfo.save(taskInfo);
        List<TaskDetailInfo> taskDetailInfoList = new LinkedList<>();
        errMsg = setTaskDetailInfoByStandardDetail(taskInfoDto, taskInfo, taskDetailInfoList, false);
        if (errMsg != null) {
            return CommonResult.getFaildResultData(errMsg);
        }
        QTaskInfo.taskInfo.save(taskInfo);
        QTaskDetailInfo.taskDetailInfo.save(taskDetailInfoList);
        return CommonResult.getSuccessResultData("新增条目成功");
    }

    /**
     * 更新培训任务、学习任务、试用任务、其他任务
     *
     * @param taskInfoDto 入参
     * @return com.share.support.result.ResultHelper<java.lang.Object>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-19
     */
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
        TaskInfo taskInfo = QTaskInfo.taskInfo
                .selectOne()
                .where(QTaskInfo.taskInfoId.eq$(taskInfoId))
                .execute();
        if (taskInfo == null) {
            return CommonResult.getFaildResultData("任务不存在！");
        }
        List<TaskDetailInfo> taskDetailInfoList = new LinkedList<>();
        errMsg = setTaskDetailInfoByStandardDetail(taskInfoDto, taskInfo, taskDetailInfoList, true);
        if (errMsg != null) {
            return CommonResult.getFaildResultData(errMsg);
        }
        QTaskInfo.taskInfo.save(taskInfo);
        QTaskDetailInfo.taskDetailInfo.save(taskDetailInfoList);
        return CommonResult.getSuccessResultData("更新条目成功");
    }

    /**
     * 获取任务详情
     *
     * @param taskInfoId 任务主表ID
     * @return com.share.support.result.ResultHelper<com.gillion.model.domain.TaskInfoDto>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-19
     */
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

    /**
     * 删除任务
     *
     * @param taskInfoId 任务主表ID
     * @return com.share.support.result.ResultHelper<java.lang.Object>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-19
     */
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

    /**
     * 根据任务类型查询任务细则
     *
     * @param taskInfoDto 查询入参
     * @return com.share.support.result.ResultHelper<com.gillion.ds.client.api.queryobject.model.Page < com.gillion.model.vo.StandardDetailVo>>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-19
     */
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

    /**
     * 根据任务类型查询必选任务细则
     *
     * @param taskInfoDto 查询入参
     * @return com.share.support.result.ResultHelper<com.gillion.ds.client.api.queryobject.model.Page < com.gillion.model.vo.StandardDetailVo>>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-19
     */
    @Override
    public ResultHelper<List<StandardDetailVo>> queryNeedStandardFullDetailByTaskType(TaskInfoDto taskInfoDto) {
        if (StrUtil.isEmpty(taskInfoDto.getTaskType())) {
            return CommonResult.getFaildResultData("任务类型不能为空");
        }
        List<StandardDetailVo> standardDetailVoList = DSContext
                .customization("WL-ERM_selectNeedStandardDetailByTaskType")
                .select()
                .mapperTo(StandardDetailVo.class)
                .execute(taskInfoDto);
        return CommonResult.getSuccessResultData(standardDetailVoList);
    }

    /**
     * 根据任务类型查询非必选任务细则
     *
     * @param taskInfoDto 查询入参
     * @return com.share.support.result.ResultHelper<com.gillion.ds.client.api.queryobject.model.Page < com.gillion.model.vo.StandardDetailVo>>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-19
     */
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
    @Deprecated
    public ResultHelper<Page<TaskInfoDto>> queryStaffTaskInfo(TaskInfoDto taskInfoDto) {
        User userModelInfo = authCenterInterface.getUserInfo();
        if (Objects.isNull(userModelInfo) || Objects.isNull(userModelInfo.getUemUserId())) {
            return CommonResult.getFaildResultData("请先登录！");
        }
        if (StrUtil.isNotBlank(taskInfoDto.getTaskTitle())) {
            taskInfoDto.setTaskTitle("%" + taskInfoDto.getTaskTitle() + "%");
        }
        AndExpression expression1 = QTaskInfo.executor.eq$(userModelInfo.getUemUserId())
                .and(QTaskInfo.taskType.in$(taskTypes1));
//        AndExpression expression2 = QTaskInfo.dispatchers.eq$(userModelInfo.getUemUserId())
//                .and(QTaskInfo.taskType.in$(taskTypes2));
        Page<TaskInfoDto> taskInfoDtoList = QTaskInfo.taskInfo
                .select(QTaskInfo.taskInfo.fieldContainer())
                .where(QTaskInfo.taskTitle.like(":taskTitle")
                        .and(QTaskInfo.status.eq(":status"))
                        .and(expression1))
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
    @Deprecated
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
    @Deprecated
    public ResultHelper<Page<TaskInfoDto>> queryLeaderTaskInfo(TaskInfoDto taskInfoDto) {
        User userModelInfo = authCenterInterface.getUserInfo();
        Map<String, Object> params = new HashMap<>();
        if (Objects.isNull(userModelInfo) || Objects.isNull(userModelInfo.getUemUserId())) {
            return CommonResult.getFaildResultData("请先登录！");
        }
        String taskTitle = StrUtil.isEmpty(taskInfoDto.getTaskTitle()) ? "" : "%" + taskInfoDto.getTaskTitle() + "%";
        List<Integer> status;
        if (Objects.isNull(taskInfoDto.getStatus())) {
            status = Arrays.asList(0, 1, 2, 3);
        } else {
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
     * 查询用户任务信息
     *
     * @param taskInfoDto 查询入参
     * @return com.share.support.result.ResultHelper<com.gillion.ds.client.api.queryobject.model.Page < com.gillion.model.domain.TaskInfoDto>>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-29
     */
    @Override
    public ResultHelper<Page<TaskInfoDto>> queryTaskInfoPageByUemUser(TaskInfoDto taskInfoDto) {
        User userModelInfo = authCenterInterface.getUserInfo();
        Map<String, Object> params = new HashMap<>();
        if (Objects.isNull(userModelInfo) || Objects.isNull(userModelInfo.getUemUserId())) {
            return CommonResult.getFaildResultData("请先登录！");
        }
        Long uemUserId = userModelInfo.getUemUserId();
        String taskTitle = StrUtil.isEmpty(taskInfoDto.getTaskTitle()) ? "%" : "%" + taskInfoDto.getTaskTitle() + "%";
        List<Integer> status;
        if (Objects.isNull(taskInfoDto.getStatus())) {
            status = Arrays.asList(0, 1, 2, 3);
        } else {
            status = Collections.singletonList(taskInfoDto.getStatus());
        }
        params.put("taskTitle", taskTitle);
        params.put("status", status);
        params.put("uemUserId", uemUserId);
        params.put("uemUserIdStr", "%" + uemUserId + "%");
        Page<TaskInfoDto> taskInfoDtoPage = DSContext
                .customization("WL-ERM_queryTaskInfoByUemUser")
                .select()
                .paging(taskInfoDto.getPageNo(), taskInfoDto.getPageSize())
                .mapperTo(TaskInfoDto.class)
                .execute(params);
        return CommonResult.getSuccessResultData(taskInfoDtoPage);
    }

    /**
     * 查询任务信息和任务细则列表
     *
     * @param queryTaskInfoDto 查询入参
     * @return com.share.support.result.ResultHelper<com.gillion.ds.client.api.queryobject.model.Page < com.gillion.model.domain.TaskInfoDto>>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-29
     */
    @Override
    public ResultHelper<TaskInfoDto> queryTaskDetailInfo(TaskInfoDto queryTaskInfoDto) {
        if (queryTaskInfoDto.getTaskInfoId() == null) {
            return CommonResult.getFaildResultData("必填参数不能为空");
        }
        User userModelInfo = authCenterInterface.getUserInfo();
        if (Objects.isNull(userModelInfo) || Objects.isNull(userModelInfo.getUemUserId())) {
            return CommonResult.getFaildResultData("请先登录！");
        }
        Long uemUserId = userModelInfo.getUemUserId();
        TaskInfoDto taskInfoDto = QTaskInfo.taskInfo.selectOne()
                .mapperTo(TaskInfoDto.class)
                .byId(queryTaskInfoDto.getTaskInfoId());
        List<TaskDetailInfoDto> taskDetailInfoDtoList = DSContext
                .customization("WL-ERM_queryTaskDetailInfoById")
                .select()
                .mapperTo(TaskDetailInfoDto.class)
                .execute(queryTaskInfoDto);
        for (TaskDetailInfoDto taskDetailInfoDto : taskDetailInfoDtoList) {
            if (uemUserId.equals(taskDetailInfoDto.getApprover())) {
                taskInfoDto.setUserType("approver");
                break;
            } else if (uemUserId.equals(taskDetailInfoDto.getAuditId())) {
                taskInfoDto.setUserType("auditor");
                break;
            } else if (uemUserId.equals(taskDetailInfoDto.getExecutor())) {
                taskInfoDto.setUserType("executor");
                break;
            } else if (uemUserId.equals(taskDetailInfoDto.getLeader())) {
                taskInfoDto.setUserType("leader");
                break;
            } else if (uemUserId.equals(taskDetailInfoDto.getDispatchers())) {
                taskInfoDto.setUserType("dispatcher");
            } else if (taskDetailInfoDto.getOrdinator().contains(uemUserId.toString())) {
                taskInfoDto.setUserType("ordinator");
            }
        }
        taskInfoDto.setTaskDetailInfoDtoList(taskDetailInfoDtoList);
        return CommonResult.getSuccessResultData(taskInfoDto);
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
            if (status == 2) {
                taskDetailInfo.setEndDate(new Date());
            } else {
                taskDetailInfo.setEndDate(null);
            }
            taskDetailInfo.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
            taskDetailInfoList.add(taskDetailInfo);
        }
        int rowCount = QTaskDetailInfo.taskDetailInfo
                .selective(QTaskDetailInfo.status, QTaskDetailInfo.resultAccess, QTaskDetailInfo.endDate)
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
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setTaskInfoId(taskInfoId);
        if (finishCount == totalCount) {
            taskInfo.setStatus(2);
            taskInfo.setEndDate(new Date());
        } else {
            taskInfo.setStatus(1);
            taskInfo.setEndDate(null);
        }
        taskInfo.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        QTaskInfo.taskInfo.selective(QTaskInfo.status, QTaskInfo.endDate)
                .update(taskInfo);
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
    @Deprecated
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
            status = Arrays.asList(0, 1, 2, 3);
        } else {
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

    /**
     * 我的任务（项目经理初次审核--部门领导初次审核） 根据任务id查询所分配的执行人所需要的信息
     *
     * @param taskInfoId 查询入参
     * @author wzr
     * @date 2022-08-09
     */
    @Override
    public ResultHelper<TaskDetailInfoDto> queryPositiveApply(Long taskInfoId) {
        Map<String, Long> parms = new HashMap<>(2);
        parms.put("taskInfoId", taskInfoId);
        TaskDetailInfoDto result = DSContext.customization("WL-ERM_queryPositiveApply")
                .selectOne().mapperTo(TaskDetailInfoDto.class)
                .execute(parms);
        return CommonResult.getSuccessResultData(result);
    }

    /**
     * 我的任务（项目经理初次审核） 查出规范条目所对应的规范细则 用作转正程序
     *
     * @author wzr
     * @date 2022-08-09
     */
    @Override
    public ResultHelper<List<StandardDetailVo>> queryAllStandardDetail() {
        List<StandardDetailVo> result = DSContext.customization("WL-ERM_queryAllStandard")
                .select().mapperTo(StandardDetailVo.class).execute();
        return CommonResult.getSuccessResultData(result);
    }

    /**
     * 我的任务（项目经理初次审核） 添加基本转正信息
     *
     * @author wzr
     * @date 2022-08-09
     */
    @Override
    public ResultHelper<Object> savePositiveInfo(TaskDetailInfoDto taskDetailInfoDto) {
        Long uemUserId = taskDetailInfoDto.getUemUserId();
        if (uemUserId == null) {
            return CommonResult.getFaildResultData("用户id不能为空!");
        }
        UemUserDto uemUserDto = taskInfoInterface.queryStaffInfo(String.valueOf(uemUserId));
        String name = uemUserDto.getName();
        Long taskDetailId = taskDetailInfoDto.getTaskDetailId();
        Date faceTime = taskDetailInfoDto.getFaceTime();
        String faceResult = taskDetailInfoDto.getFaceResult();
        String faceScore = taskDetailInfoDto.getFaceScore();
        String faceRemark = taskDetailInfoDto.getFaceRemark();
        TaskDetailInfo taskDetailInfo = QTaskDetailInfo.taskDetailInfo.selectOne(QTaskDetailInfo.taskDetailInfo.fieldContainer()).byId(taskDetailId);
        if (Objects.isNull(taskDetailInfo)) {
            return CommonResult.getFaildResultData("查询结果为空!");
        }
        taskDetailInfo.setApprover(uemUserId);
        taskDetailInfo.setApproverName(name);
        taskDetailInfo.setFaceTime(faceTime);
        taskDetailInfo.setFaceResult(faceResult);
        taskDetailInfo.setFaceScore(faceScore);
        taskDetailInfo.setFaceRemark(faceRemark);
        taskDetailInfo.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        int reslut = QTaskDetailInfo.taskDetailInfo.save(taskDetailInfo);
        if (reslut > 0) {
            return CommonResult.getSuccessResultData("添加成功");
        } else {
            return CommonResult.getFaildResultData("添加失败!");
        }
    }

    /**
     * 我的任务（项目经理初次审核） 添加离职申请基本信息
     *
     * @author wzr
     * @date 2022-08-09
     */
    @Override
    public ResultHelper<Object> saveLeaveInfo(TaskDetailInfoDto taskDetailInfoDto) {
        Long uemUserId = taskDetailInfoDto.getUemUserId();
        if (uemUserId == null) {
            return CommonResult.getFaildResultData("用户id不能为空!");
        }
        UemUserDto uemUserDto = taskInfoInterface.queryStaffInfo(String.valueOf(uemUserId));
        String name = uemUserDto.getName();
        Long taskInfoId = taskDetailInfoDto.getTaskInfoId();
        Date auditDate = taskDetailInfoDto.getAuditDate();
        String auditResult = taskDetailInfoDto.getAuditResult();
        String auditRemark = taskDetailInfoDto.getAuditRemark();
        TaskDetailInfo taskDetailInfo = QTaskDetailInfo.taskDetailInfo.selectOne()
                .where(QTaskDetailInfo.taskInfoId.eq$(taskInfoId)).execute();
        if (Objects.isNull(taskDetailInfo)) {
            return CommonResult.getFaildResultData("查询结果为空!");
        }
        taskDetailInfo.setAuditId(uemUserId);
        taskDetailInfo.setAuditName(name);
        taskDetailInfo.setAuditDate(auditDate);
        taskDetailInfo.setAuditRemark(auditRemark);
        taskDetailInfo.setAuditResult(auditResult);
        taskDetailInfo.setStatus(2);
        taskDetailInfo.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        int reslut = QTaskDetailInfo.taskDetailInfo.save(taskDetailInfo);
        if (reslut > 0) {
            return CommonResult.getSuccessResultData("添加成功");
        } else {
            return CommonResult.getFaildResultData("添加失败!");
        }
    }

    /**
     * 我的任务（部门领导最终审核） 添加转正申请基本信息
     *
     * @author wzr
     * @date 2022-08-09
     */

    @Override
    public ResultHelper<Object> savePositiveInfoByLeader(TaskDetailInfoDto taskDetailInfoDto) {
        Long taskInfoId = taskDetailInfoDto.getTaskInfoId();
        if (taskInfoId == null) {
            return CommonResult.getFaildResultData("任务id不能为空!");
        }
        Date approvalDate = taskDetailInfoDto.getApprovalDate();
        String resultAccess = taskDetailInfoDto.getResultAccess();
        String offerRemark = taskDetailInfoDto.getOfferRemark();
        TaskDetailInfo taskDetailInfo = QTaskDetailInfo.taskDetailInfo.selectOne()
                .where(QTaskDetailInfo.taskInfoId.eq$(taskInfoId)).execute();
        TaskInfo taskInfo = QTaskInfo.taskInfo.selectOne().where(QTaskInfo.taskInfoId.eq$(taskInfoId)).execute();
        if (Objects.isNull(taskInfo)) {
            return CommonResult.getFaildResultData("查询结果为空!");
        }
        taskInfo.setStatus(2);
        taskInfo.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        QTaskInfo.taskInfo.save(taskInfo);
        taskDetailInfo.setApprovalDate(approvalDate);
        taskDetailInfo.setResultAccess(resultAccess);
        taskDetailInfo.setOfferRemark(offerRemark);
        taskDetailInfo.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        int result = QTaskDetailInfo.taskDetailInfo.save(taskDetailInfo);
        if (result > 0) {
            return CommonResult.getSuccessResultData("添加成功");
        } else {
            return CommonResult.getFaildResultData("添加失败");
        }
    }

    /**
     * 我的任务（部门领导最终审核） 添加离职基本信息
     *
     * @author wzr
     * @date 2022-08-09
     */

    @Override
    public ResultHelper<Object> saveLeaveInfoByLeader(TaskDetailInfoDto taskDetailInfoDto) {
        Long taskDetailId = taskDetailInfoDto.getTaskDetailId();
        Long taskInfoId = taskDetailInfoDto.getTaskInfoId();
        if ((taskDetailId == null) || (taskInfoId == null)) {
            return CommonResult.getFaildResultData("任务id与任务详情id都不能为空!");
        }
        Date approvalDate = taskDetailInfoDto.getApprovalDate();
        String resultAccess = taskDetailInfoDto.getResultAccess();
        String approvalRemark = taskDetailInfoDto.getApprovalRemark();
        TaskDetailInfo taskDetailInfo = QTaskDetailInfo.taskDetailInfo.selectOne()
                .where(QTaskDetailInfo.taskInfoId.eq$(taskInfoId)
                        .and(QTaskDetailInfo.taskDetailId.eq$(taskDetailId))).execute();
        if (Objects.isNull(taskDetailInfo)) {
            return CommonResult.getFaildResultData("查询结果为空!");
        }
        taskDetailInfo.setApprovalDate(approvalDate);
        taskDetailInfo.setResultAccess(resultAccess);
        taskDetailInfo.setApprovalRemark(approvalRemark);
        TaskInfo taskInfo = QTaskInfo.taskInfo.selectOne().where(QTaskInfo.taskInfoId.eq$(taskInfoId)).execute();
        taskInfo.setStatus(2);
        taskInfo.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        QTaskInfo.taskInfo.save(taskInfo);
        taskDetailInfo.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        int result = QTaskDetailInfo.taskDetailInfo.save(taskDetailInfo);
        if (result > 0) {
            return CommonResult.getSuccessResultData("添加成功");
        } else {
            return CommonResult.getFaildResultData("添加失败");
        }
    }

    /**
     * 员工管理（服务调用） 添加员工转正信息
     *
     * @author wzr
     * @date 2022-08-11
     */

    @Override
    public ResultHelper<Object> savePositiveInfoByStaff(TaskDetailInfoDTO taskDetailInfoDTO) {
        Long uemUserId = taskDetailInfoDTO.getUemUserId();
        Long taskInfoId = taskDetailInfoDTO.getTaskInfoId();
        if ((uemUserId == null) || (taskInfoId == null)) {
            return CommonResult.getFaildResultData("用户id与任务详情id都不能为空!");
        }
        Date offerDate = taskDetailInfoDTO.getOfferDate();
        String offerType = taskDetailInfoDTO.getOfferType();
        String faceScore = taskDetailInfoDTO.getFaceScore();
        List<String> uemUserIds = taskDetailInfoDTO.getUemUserIds();
        int i = 0;
        //获取用户表的主键作数组，分别插入任务子表审批人id以及面谈人id
        for (String s : uemUserIds) {
            String ids = uemUserIds.get(i);
            if (i == 0) {
                UemUserDto uemUserDto = taskInfoInterface.queryStaffInfo(ids);
                String name = uemUserDto.getName();
                String faceRemark = taskDetailInfoDTO.getFaceRemark();
                TaskDetailInfo taskDetailInfo = QTaskDetailInfo.taskDetailInfo.selectOne()
                        .where(QTaskDetailInfo.taskInfoId.eq$(taskInfoId)).execute();
                taskDetailInfo.setInterviewerId(Long.valueOf(ids));
                taskDetailInfo.setFaceRemark(faceRemark);
                taskDetailInfo.setInterviewerName(name);
                taskDetailInfo.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
                int result = QTaskDetailInfo.taskDetailInfo.save(taskDetailInfo);
                if (result == 1) {
                    i = ++i;
                    continue;
                } else {
                    return CommonResult.getFaildResultData("出错啦!");
                }
            } else if (i == 1) {
                UemUserDto uemUserDto = taskInfoInterface.queryStaffInfo(ids);
                String name = uemUserDto.getName();
                String offerRemark = taskDetailInfoDTO.getOfferRemark();
                TaskDetailInfo taskDetailInfo = QTaskDetailInfo.taskDetailInfo.selectOne()
                        .where(QTaskDetailInfo.taskInfoId.eq$(taskInfoId)).execute();
                taskDetailInfo.setApprover(Long.valueOf(ids));
                taskDetailInfo.setOfferRemark(offerRemark);
                taskDetailInfo.setApproverName(name);
                taskDetailInfo.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
                int result = QTaskDetailInfo.taskDetailInfo.save(taskDetailInfo);
                if (result == 1) {
                    i = ++i;
                    continue;
                } else {
                    return CommonResult.getFaildResultData("出错啦!");
                }
            }
        }
        TaskDetailInfo taskDetailInfo = QTaskDetailInfo.taskDetailInfo.selectOne()
                .where(QTaskDetailInfo.taskInfoId.eq$(taskInfoId)).execute();
        //更改员工在职状态，转正之后改为正式员工
        taskInfoInterface.updateJobStatus(uemUserId);
        taskDetailInfo.setOfferDate(offerDate);
        taskDetailInfo.setOfferType(offerType);
        taskDetailInfo.setFaceScore(faceScore);
        taskDetailInfo.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        int result = QTaskDetailInfo.taskDetailInfo.save(taskDetailInfo);
        if (result == 1) {
            return CommonResult.getSuccessResultData("添加成功");
        } else {
            return CommonResult.getFaildResultData("出错啦!");
        }
    }

    /**
     * 我的任务  员工撤回申请
     *
     * @author wzr
     * @date 2022-08-12
     */

    @Override
    public ResultHelper<?> deletedApplyByStaff(Long taskInfoId) {
        int result = QTaskInfo.taskInfo.deleteById(taskInfoId);
        if (result == 1) {
            return CommonResult.getSuccessResultData("撤回成功");
        } else {
            return CommonResult.getFaildResultData("撤回失败！");
        }
    }

    /**
     * 我的任务 --分页查询员工信息
     *
     * @author wzr
     * @date 2022-08-18
     */
    @Override
    public ResultHelper<TaskInfoDto> queryTaskInfoByPage(TaskInfoDto taskInfoDto) {
        String taskTitle = taskInfoDto.getTaskTitle();
        if (!Objects.isNull(taskTitle)) {
            taskInfoDto.setTaskTitle("%" + taskTitle + "%");
        }
        int pageNo = taskInfoDto.getPageNo() == null ? 1 : taskInfoDto.getPageNo();
        int pageSize = taskInfoDto.getPageSize() == null ? 10 : taskInfoDto.getPageSize();
        Page<TaskInfoDto> taskInfoDtoPage = QTaskInfo.taskInfo
                .select(QTaskInfo.taskInfo.fieldContainer())
                .where(QTaskInfo.taskTitle.like(":taskTitle")
                        .and(QTaskInfo.status.eq(":status"))
                        .and(QTaskInfo.taskType.notIn$("员工辞退")))
                .paging(pageNo, pageSize)
                .mapperTo(TaskInfoDto.class)
                .sorting(QTaskInfo.createTime.desc())
                .execute(taskInfoDto);
        return CommonResult.getSuccessResultData(taskInfoDtoPage);
    }

    @Override
    public List<TaskInfoDto> queryPositiveInfo(Long dispatchers) {
        List<TaskInfoDto> list = QTaskInfo.taskInfo
                .select(QTaskInfo.taskInfoId, QTaskInfo.taskTitle, QTaskInfo.dispatchers)
                .where(QTaskInfo.dispatchers.eq$(dispatchers)
                        .and(QTaskInfo.taskType.eq$("员工转正"))).mapperTo(TaskInfoDto.class).execute();
        if (CollectionUtils.isNotEmpty(list)) {
            return list;
        } else {
            return null;
        }
    }

    @Override
    public TaskDetailInfoDTO queryPositiveInfoByTaskId(Long taskInfoId) {
        HashMap<String, Long> params = new HashMap<>();
        params.put("taskInfoId", taskInfoId);
        TaskDetailInfoDTO result = DSContext.customization("WL-ERM_queryPositiveInfo").selectOne()
                .mapperTo(TaskDetailInfoDTO.class).execute(params);
        if (result != null) {
            return result;
        } else {
            return null;
        }
    }
}
