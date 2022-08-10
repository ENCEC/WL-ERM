package com.gillion.controller;

import com.gillion.model.domain.TaskInfoDto;
import com.gillion.model.entity.TaskDetailInfo;
import com.gillion.model.entity.TaskInfo;
import com.gillion.service.TaskDetailInfoService;
import com.gillion.train.api.model.vo.TaskDetailInfoDTO;
import com.share.auth.api.StandardEntryInterface;
import com.share.auth.domain.UemUserDto;
import com.share.support.result.ResultHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName TaskDetailInfoController
 * @Author weiq
 * @Date 2022/8/8 20:20
 * @Version 1.0
 **/
@RestController
@Slf4j
@RequestMapping("/taskDetailInfo")
public class TaskDetailInfoController {
@Autowired
private StandardEntryInterface standardEntryInterface;
    @Autowired
    private TaskDetailInfoService taskDetailInfoService;

    /**
     * 转正申请
     * @param taskDetailInfoDTO
     * @return
     */
    @PostMapping("/saveOffer")
    public ResultHelper<?> saveOffer(@RequestBody TaskDetailInfoDTO taskDetailInfoDTO) {
        return taskDetailInfoService.saveOffer(taskDetailInfoDTO);
    }

    /**
     * 离职申请
     * @param taskDetailInfoDTO
     * @return
     */
    @PostMapping("/saveLeave")
    public ResultHelper<?> saveLeave(@RequestBody TaskDetailInfoDTO taskDetailInfoDTO) {
        return taskDetailInfoService.saveLeave(taskDetailInfoDTO);
    }

    /**
     * 查看转正评语
     * @param dispatchers
     * @return
     */
    @RequestMapping ("/queryOffer")
    public List queryOffer(Long dispatchers) {
        return taskDetailInfoService.queryOffer(dispatchers);
    }

    /**
     * 离职申请添加离职理由到用户表
     * @param uemUserId
     * @param leaveReason
     * @return
     */
    @PostMapping("/uemUserManage/updateLeaveReason")
    public ResultHelper<?> updateLeaveReason(@RequestParam(value = "uemUserId") Long uemUserId,@RequestParam(value = "leaveReason")String leaveReason) {
        return standardEntryInterface.updateLeaveReason(uemUserId, leaveReason);
    }
}
