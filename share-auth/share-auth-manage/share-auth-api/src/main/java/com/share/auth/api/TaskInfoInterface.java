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
     * @param uemUserId
     * @date 2022-08-16
     */
    @GetMapping("/uemUserManage/queryStaffInfo")
    UemUserDto queryStaffInfo(@RequestParam(value = "uemUserId") String uemUserId);


    /**
     * 服务调用---添加离职信息同时生成一个新任务
     *
     * @param uemUserDto
     * @date 2022-08-17
     */
    @PostMapping("/uemUserManage/saveResignInfo")
    ResultHelper<Object> saveResignInfo(@RequestBody UemUserDto uemUserDto);

    /**
     * 服务调用---添加辞退信息同时生成一个新任务
     *
     * @param uemUserDto
     * @date 2022-08-17
     */
    @PostMapping("/uemUserManage/saveDismissInfo")
    ResultHelper<Object> saveDismissInfo(@RequestBody UemUserDto uemUserDto);

    /**
     * 更改员工状态
     *
     * @param uemUserId
     * @date 2022-08-22
     */

    @GetMapping("/uemUserManage/updateJobStatus")
    ResultHelper<Object> updateJobStatus(@RequestParam(value = "uemUserId") Long uemUserId);
}
