package com.gillion.service.impl;

import cn.hutool.core.date.DateTime;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.gillion.model.entity.TaskDetailInfo;
import com.gillion.model.entity.TaskInfo;
import com.gillion.model.querymodels.QStandardDetail;
import com.gillion.model.querymodels.QStudent;
import com.gillion.model.querymodels.QTaskDetailInfo;
import com.gillion.model.querymodels.QTaskInfo;
import com.gillion.service.StandardDetailService;
import com.gillion.service.TaskDetailInfoService;
import com.gillion.train.api.model.vo.TaskDetailInfoDTO;
import com.share.auth.api.StandardEntryInterface;
import com.share.auth.domain.UemUserDto;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName TaskDetailInfoServiceImpl
 * @Author weiq
 * @Date 2022/8/8 18:45
 * @Version 1.0
 **/
@Service
public class TaskDetailInfoServiceImpl implements TaskDetailInfoService {

    @Autowired
    private StandardEntryInterface standardEntryInterface;

    @Override
    public ResultHelper<?> saveOffer(TaskDetailInfoDTO taskDetailInfoDTO) {
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        taskInfo.setTaskType("员工转正");
        taskInfo.setTaskTitle(taskDetailInfoDTO.getUemUserName()+"转正申请");
        taskInfo.setCreateTime(new DateTime());
        taskInfo.setStatus(0);
        QTaskInfo.taskInfo.save(taskInfo);
        TaskDetailInfo taskDetailInfo = new TaskDetailInfo();
        taskDetailInfo.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        taskDetailInfo.setApplyDate(taskDetailInfoDTO.getApplyDate());
        taskDetailInfo.setOfferType(taskDetailInfoDTO.getOfferType());
        taskDetailInfo.setApprover(taskDetailInfoDTO.getApprover());
        taskDetailInfo.setStandardDetailId(taskDetailInfoDTO.getStandardDetailId());
        taskDetailInfo.setStandardEntryId(taskDetailInfoDTO.getStandardEntryId());
        taskDetailInfo.setTaskInfoId(taskInfo.getTaskInfoId());
        QTaskDetailInfo.taskDetailInfo.save(taskDetailInfo);
        return CommonResult.getSuccessResultData("提交转正申请成功");
    }

    @Override
    public ResultHelper<?> saveLeave(TaskDetailInfoDTO taskDetailInfoDTO) {
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        taskInfo.setTaskType("员工离职");
        UemUserDto uemUserDto = standardEntryInterface.queryStaffById(taskDetailInfoDTO.getUemUserId());
        uemUserDto.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
        taskInfo.setTaskTitle(taskDetailInfoDTO.getUemUserName()+"离职申请");
        taskInfo.setCreateTime(new DateTime());
        taskInfo.setStatus(0);
        QTaskInfo.taskInfo.save(taskInfo);
        uemUserDto.setName(taskDetailInfoDTO.getUemUserName());
        uemUserDto.setLeaveReason(taskDetailInfoDTO.getLeaveReason());
        standardEntryInterface.updateLeaveReason(uemUserDto);
        TaskDetailInfo taskDetailInfo = new TaskDetailInfo();
        taskDetailInfo.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        taskDetailInfo.setApplyDate(taskDetailInfoDTO.getApplyDate());
        taskDetailInfo.setApprover(taskDetailInfoDTO.getApprover());
        taskDetailInfo.setStandardDetailId(taskDetailInfoDTO.getStandardDetailId());
        taskDetailInfo.setStandardEntryId(taskDetailInfoDTO.getStandardEntryId());
        taskDetailInfo.setTaskInfoId(taskInfo.getTaskInfoId());
        QTaskDetailInfo.taskDetailInfo.save(taskDetailInfo);
        return CommonResult.getSuccessResultData("提交离职申请成功");
    }
}
