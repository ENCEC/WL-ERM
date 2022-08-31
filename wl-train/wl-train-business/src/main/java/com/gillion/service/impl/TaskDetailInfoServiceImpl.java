package com.gillion.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.gillion.model.domain.TaskInfoDto;
import com.gillion.model.entity.StandardEntry;
import com.gillion.model.entity.TaskDetailInfo;
import com.gillion.model.entity.TaskInfo;
import com.gillion.model.querymodels.QStandardEntry;
import com.gillion.model.querymodels.QTaskDetailInfo;
import com.gillion.model.querymodels.QTaskInfo;
import com.gillion.service.TaskDetailInfoService;
import com.gillion.train.api.model.vo.TaskDetailInfoDTO;
import com.share.auth.api.StandardEntryInterface;
import com.share.auth.api.TaskDetailInfoInterface;
import com.share.auth.api.TaskInfoInterface;
import com.share.auth.api.UemUserInterface;
import com.share.auth.domain.UemUserDto;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName TaskDetailInfoServiceImpl
 * @Author weiq
 * @Date 2022/8/8 18:45
 * @Version 1.0
 **/
@Service
public class TaskDetailInfoServiceImpl implements TaskDetailInfoService {

//    @Autowired
//    private StandardEntryInterface standardEntryInterface;

    @Autowired
    private TaskInfoInterface taskInfoInterface;

    @Autowired
    private TaskDetailInfoInterface taskDetailInfoInterface;

    @Autowired
    private UemUserInterface uemUserInterface;
    /**
     * 转正申请
     *
     * @param taskDetailInfoDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultHelper<?> saveOffer(TaskDetailInfoDTO taskDetailInfoDTO) {
        if (Objects.isNull(taskDetailInfoDTO.getApplyDate())
                || StrUtil.isEmpty(taskDetailInfoDTO.getOfferType())
                || Objects.isNull(taskDetailInfoDTO.getApprover())) {
            return CommonResult.getFaildResultData("必填项不能为空");
        }
        List<TaskInfo> taskInfos = QTaskInfo.taskInfo.select().where(QTaskInfo.taskTitle.eq$(taskDetailInfoDTO.getUemUserName() + "转正申请")).execute();
        if (CollectionUtils.isNotEmpty(taskInfos)) {
            return CommonResult.getFaildResultData("该员工已经申请了");
        }
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        taskInfo.setDispatchers(taskDetailInfoDTO.getUemUserId());
        taskInfo.setDispatchersName(taskDetailInfoDTO.getUemUserName());
        taskInfo.setTaskType("员工转正");
        taskInfo.setTaskTitle(taskDetailInfoDTO.getUemUserName() + "转正申请");
        //员工提交申请之后状态变为审批中
        taskInfo.setStatus(3);
        QTaskInfo.taskInfo.save(taskInfo);
        TaskDetailInfo taskDetailInfo = new TaskDetailInfo();
        taskDetailInfo.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        taskDetailInfo.setApplyDate(taskDetailInfoDTO.getApplyDate());
        taskDetailInfo.setOfferType(taskDetailInfoDTO.getOfferType());
        taskDetailInfo.setApprover(taskDetailInfoDTO.getApprover());
        List<Long> list = new ArrayList<>();
        list.add(taskDetailInfoDTO.getApprover());
        UemUserDto uemUserDto = new UemUserDto();
        uemUserDto.setUemUserIdList(list);
        ResultHelper<List<UemUserDto>> listResultHelper = uemUserInterface.queryUemUserListById(uemUserDto);
        String name = listResultHelper.getData().get(0).getName();
        taskDetailInfo.setApproverName(name);
        taskDetailInfo.setTaskInfoId(taskInfo.getTaskInfoId());
        QTaskDetailInfo.taskDetailInfo.save(taskDetailInfo);
        return CommonResult.getSuccessResultData("提交转正申请成功");
    }

    /**
     * 离职申请
     *
     * @param taskDetailInfoDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultHelper<?> saveLeave(TaskDetailInfoDTO taskDetailInfoDTO) {
        if (Objects.isNull(taskDetailInfoDTO.getApplyDate())
                || Objects.isNull(taskDetailInfoDTO.getApprover())
                || StrUtil.isEmpty(taskDetailInfoDTO.getLeaveReason())) {
            return CommonResult.getFaildResultData("必填项不能为空");
        }
        List<TaskInfo> taskInfos = QTaskInfo.taskInfo.select().where(QTaskInfo.taskTitle.eq$(taskDetailInfoDTO.getUemUserName() + "离职申请")).execute();
        if (CollectionUtils.isNotEmpty(taskInfos)) {
            return CommonResult.getFaildResultData("该员工已经申请了");
        }
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        taskInfo.setDispatchers(taskDetailInfoDTO.getUemUserId());
        taskInfo.setDispatchersName(taskDetailInfoDTO.getUemUserName());
        taskInfo.setTaskType("员工离职");
        taskInfo.setTaskTitle(taskDetailInfoDTO.getUemUserName() + "离职申请");
        //员工提交申请后状态变为审批中
        taskInfo.setStatus(3);
        QTaskInfo.taskInfo.save(taskInfo);
        taskDetailInfoInterface.updateLeaveReason(taskDetailInfoDTO.getUemUserId(), taskDetailInfoDTO.getLeaveReason());
        TaskDetailInfo taskDetailInfo = new TaskDetailInfo();
        taskDetailInfo.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        taskDetailInfo.setApplyDate(taskDetailInfoDTO.getApplyDate());
        taskDetailInfo.setApprover(taskDetailInfoDTO.getApprover());
        List<Long> list = new ArrayList<>();
        list.add(taskDetailInfoDTO.getApprover());
        UemUserDto uemUserDto = new UemUserDto();
        uemUserDto.setUemUserIdList(list);
        ResultHelper<List<UemUserDto>> listResultHelper = uemUserInterface.queryUemUserListById(uemUserDto);
        String name = listResultHelper.getData().get(0).getName();
        taskDetailInfo.setApproverName(name);
        taskDetailInfo.setTaskInfoId(taskInfo.getTaskInfoId());
        QTaskDetailInfo.taskDetailInfo.save(taskDetailInfo);
        return CommonResult.getSuccessResultData("提交离职申请成功");
    }

    /**
     * 查看转正评语
     *
     * @param dispatchers
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultHelper<List> queryOffer(Long dispatchers, String name) {
        String taskTitle = name + "转正申请";
        List<TaskInfo> taskInfos = QTaskInfo.taskInfo.select().where(QTaskInfo.taskInfoId.goe$(1L)).execute();
        List lists = new LinkedList();
        for (TaskInfo taskInfo : taskInfos) {
            if (taskInfo.getTaskTitle().equals(taskTitle)) {
                lists.add(taskInfo);
            }
        }
        if (lists.size() == 1) {
            UemUserDto uemUserDto = taskDetailInfoInterface.queryOfferInfo(dispatchers);
            TaskInfoDto taskInfoDto = QTaskInfo.taskInfo.selectOne(
                            QTaskInfo.taskInfoId)
                    .where(QTaskInfo.dispatchers.eq$(dispatchers).and(QTaskInfo.taskTitle.eq$(taskTitle)))
                    .mapperTo(TaskInfoDto.class)
                    .execute();
            TaskDetailInfo taskDetailInfo = QTaskDetailInfo.taskDetailInfo.selectOne()
                    .where(QTaskDetailInfo.taskInfoId.eq$(taskInfoDto.getTaskInfoId()))
                    .execute();
            List list = new LinkedList();
            list.add(taskDetailInfo);
            list.add(uemUserDto);
            return CommonResult.getSuccessResultData(list);
        } else {
            return CommonResult.getFaildResultData("查询失败");
        }
    }
}
