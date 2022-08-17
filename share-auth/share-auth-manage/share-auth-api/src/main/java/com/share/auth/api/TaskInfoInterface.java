package com.share.auth.api;

import com.share.auth.domain.UemUserDto;
import com.share.support.result.ResultHelper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName TaskInfoInterface
 * @Author wzr
 * @Date 2022/8/10
 * <p>
 * <p>
 * wl-train模块进行服务调用
 **/

@FeignClient(value = "${application.name.auth}")
public interface TaskInfoInterface {
    /**
     * 查看辞退原因
     *
     * @param uemUserId
     * @return
     */
    @GetMapping("/uemUserManage/queryLeaveInfo")
    ResultHelper<UemUserDto> queryLeaveInfo(@RequestParam(value = "uemUserId") Long uemUserId);

    /**
     * 联想控件---user表id查name
     *
     * @param uemUserId
     * @return
     */
    @RequestMapping("/uemUserManage/queryUemUserById")
    UemUserDto queryUemUserById(@RequestParam(value = "uemUserId") Long uemUserId);

    /**
     * 服务调用---转正，离职，辞退---查看信息
     *
     * @author wzr
     * @date 2022-08-04
     */
    @GetMapping("/uemUserManage/queryStaffInfo")
    UemUserDto queryStaffInfo(@RequestParam(value = "uemUserId") Long uemUserId);
}
