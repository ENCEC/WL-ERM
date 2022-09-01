package com.share.auth.api;

import com.share.auth.domain.UemUserDto;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    /**
     * 不受数据权限控制---查询user表信息
     *
     * @param uemUserDto
     * @date 2022-08-31
     */

    @PostMapping("/uemUserManage/queryUemUserListById")
    ResultHelper<List<UemUserDto>> queryUemUserListById(@RequestBody UemUserDto uemUserDto);

    /**
     * 获取当前登陆用户信息
     *
     * @param
     * @date 2022-09-01
     */
    @GetMapping("/user/getLoginUserInfo")
    @ResponseBody
    ResultHelper<UemUserDto> getLoginUserInfo();
}
