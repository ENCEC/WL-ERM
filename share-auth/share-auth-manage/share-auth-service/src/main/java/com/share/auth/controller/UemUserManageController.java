package com.share.auth.controller;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.center.api.AuthCenterInterface;
import com.share.auth.domain.UemUserDto;
import com.share.auth.domain.UemUserEditDTO;
import com.share.auth.service.UemUserManageService;
import com.share.support.model.User;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * @author xrp
 * @date 2020/11/3 0003
 */
@Api("用户管理控制器")
@Controller
@RequestMapping("uemUserManage")
public class UemUserManageController {

    @Autowired
    private UemUserManageService uemUserManageService;
    @Autowired
    private AuthCenterInterface authCenterInterface;

    /**
     * 用户管理
     *
     * @param uemUserDto 用户表封装类
     * @return Page<UemUserDto>
     * @author xrp
     */
    @ApiOperation("用户管理")
    @ApiImplicitParam(name = "uemUserDto", value = "用户信息封装类", required = true, dataType = "UemUserDto", paramType = "queryUemUser")
    @PostMapping("/queryUemUser")
    @ResponseBody
    public ResultHelper<Page<UemUserDto>> queryUemUser(@RequestBody UemUserDto uemUserDto) {
        //HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        User user = authCenterInterface.getUserInfo();
        if(Objects.isNull(user) || !"SYSADMIN".equals(user.getRoleCode())){
            return CommonResult.getFaildResultData("访问受限，用户没有接口访问权限！");
        }
        Page<UemUserDto> page = uemUserManageService.queryUemUser(uemUserDto);
        return CommonResult.getSuccessResultData(page);
    }

    /**
     * 用户管理详情
     *
     * @param uemUserId 用户ID
     * @return List<UemUserDto>
     * @author xrp
     */
    @ApiOperation("用户管理详情")
    @ApiImplicitParam(name = "uemUserId", value = "用户ID", required = true, dataType = "String", paramType = "getUemUser")
    @GetMapping("/getUemUser")
    @ResponseBody
    public ResultHelper<List<UemUserDto>> getUemUser(@RequestParam String uemUserId) {
        List<UemUserDto> uemUserDtoList = uemUserManageService.getUemUser(uemUserId);
        return CommonResult.getSuccessResultData(uemUserDtoList);
    }

    /**
     * 用户管理 启停
     *
     * @param uemUserDto 用户表封装类
     * @return Map<String, Object>
     * @author xrp
     */
    @ApiOperation("用户管理 启停")
    @ApiImplicitParam(name = "uemUserDto", value = "用户信息封装类", required = true, dataType = "UemUserDto", paramType = "uemUserStartStop")
    @PostMapping("/uemUserStartStop")
    @ResponseBody
    public ResultHelper<Object> uemUserStartStop(@RequestBody UemUserDto uemUserDto) {
        return uemUserManageService.uemUserStartStop(uemUserDto);
    }


    /**
     * 修改用户信息
     *
     * @param uemUserDto
     * @return
     * @throws
     * @author tujx
     */
    @ApiOperation("修改用户信息")
    @PostMapping("/editUemUser")
    @ResponseBody
    public ResultHelper<Object> editUemUser(@RequestBody @Valid UemUserEditDTO uemUserDto, BindingResult results) {
        if (results.hasErrors()) {
            //数据校验不通过
            FieldError fieldError = results.getFieldError();
            if (Objects.nonNull(fieldError)) {
                return CommonResult.getFaildResultData(fieldError.getDefaultMessage());
            }
        }
        return uemUserManageService.editUemUser(uemUserDto);
    }


    /**
     * 删除用户信息
     *
     * @param uemUserId
     * @return
     * @throws
     * @author tujx
     */
    @ApiOperation("删除用户信息")
    @PostMapping("/deleteUemUser")
    @ResponseBody
    public ResultHelper<Object> deleteUemUser(Long uemUserId) {
        return uemUserManageService.deleteUemUser(uemUserId);
    }

    /**
     * 平台客服新增用户
     * @param uemUserDto 用户信息
     * @return 新增结果
     */
    @ApiOperation("平台客服新增用户")
    @ResponseBody
    @PostMapping(value = "/saveUemUser")
    public ResultHelper<String> saveUemUser(@RequestBody UemUserDto uemUserDto) {
        return uemUserManageService.saveUemUser(uemUserDto);
    }

    /**
     * 平台客服重置用户密码
     * @param uemUserId 用户id
     * @return 重置结果
     */
    @ApiOperation("平台客服重置用户密码")
    @ResponseBody
    @GetMapping(value = "/resetUemUserPassword")
    public ResultHelper<String> resetUemUserPassword(Long uemUserId) {
        return uemUserManageService.resetUemUserPassword(uemUserId);
    }

}
