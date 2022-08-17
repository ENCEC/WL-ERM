package com.share.auth.api;

import com.share.auth.domain.UemUserDto;
import com.share.support.result.ResultHelper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName TaskDetailInfoInterface
 * @Author weiq
 * @Date 2022/8/16 16:48
 * @Version 1.0
 **/
@Component
@FeignClient(value = "${application.name.auth}")
public interface TaskDetailInfoInterface {

    /**
     * 查看转正评语部分信息
     * @param uemUserId
     * @return
     */
    @RequestMapping("/uemUserManage/queryOfferInfo")
    UemUserDto queryOfferInfo(@RequestParam(value = "uemUserId") Long uemUserId);

    /**
     * 离职申请添加离职理由
     * @param
     * @return
     */
    @GetMapping("/uemUserManage/updateLeaveReason")
    ResultHelper<?> updateLeaveReason(@RequestParam(value = "uemUserId") Long uemUserId, @RequestParam(value = "leaveReason")String leaveReason);
}
