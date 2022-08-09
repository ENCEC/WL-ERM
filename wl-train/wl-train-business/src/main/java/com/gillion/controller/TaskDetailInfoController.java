package com.gillion.controller;

import com.gillion.service.TaskDetailInfoService;
import com.gillion.train.api.model.vo.TaskDetailInfoDTO;
import com.share.auth.api.StandardEntryInterface;
import com.share.auth.domain.UemUserDto;
import com.share.support.result.ResultHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName TaskDetailInfoController
 * @Description TODO
 * @Author asus
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

    @GetMapping("/uemUserManage/queryStaffById")
    public UemUserDto queryStaffById(@RequestParam Long uemUserId) {
        return standardEntryInterface.queryStaffById(uemUserId);
    }

    @PostMapping("/saveOffer")
    public ResultHelper<?> saveOffer(@RequestBody TaskDetailInfoDTO taskDetailInfoDTO) {
        return taskDetailInfoService.saveOffer(taskDetailInfoDTO);
    }

    @PostMapping("/saveLeave")
    public ResultHelper<?> saveLeave(@RequestBody TaskDetailInfoDTO taskDetailInfoDTO) {
        return taskDetailInfoService.saveLeave(taskDetailInfoDTO);
    }
}
