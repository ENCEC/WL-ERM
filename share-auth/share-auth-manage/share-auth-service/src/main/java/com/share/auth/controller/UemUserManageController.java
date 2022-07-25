package com.share.auth.controller;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.center.api.AuthCenterInterface;
import com.share.auth.domain.UemUserDto;
import com.share.auth.domain.UemUserEditDTO;
import com.share.auth.service.UemUserManageService;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

/**
 * 用户管理接口
 *
 * @author xuzt <xuzt@gillion.com.cn>
 * @date 2022-07-25
 */
@Api("用户管理")
@RestController
@RequestMapping("/uemUserManage")
public class UemUserManageController {

    @Autowired
    private UemUserManageService uemUserManageService;

    @Autowired
    private AuthCenterInterface authCenterInterface;

    /**
     * 根据用户名、姓名或启禁用状态查询用户信息
     *
     * @param uemUserDto 用户信息封装类
     * @return ResultHelper<Page<UemUserDto>>
     * @date 2022-07-25
     */
    @ApiOperation("根据用户名、姓名或启禁用状态查询用户信息")
    @ApiImplicitParam(name = "uemUserDto", value = "用户信息封装类", required = true, dataType = "UemUserDto")
    @PostMapping("/queryUemUser")
    public ResultHelper<Page<UemUserDto>> queryUemUser(@RequestBody UemUserDto uemUserDto) {
        Page<UemUserDto> page = uemUserManageService.queryUemUser(uemUserDto);
        return CommonResult.getSuccessResultData(page);
    }

    /**
     * 获取用户信息
     *
     * @param uemUserId 用户ID
     * @return com.share.support.result.ResultHelper<java.util.List<com.share.auth.domain.UemUserDto>>
     * @date 2022-07-25
     */
    @ApiOperation("获取用户信息")
    @ApiImplicitParam(name = "uemUserId", value = "用户ID", required = true, dataType = "Long")
    @GetMapping("/getUemUser")
    public ResultHelper<UemUserDto> getUemUser(@RequestParam Long uemUserId) {
        UemUserDto uemUserDto = uemUserManageService.getUemUser(uemUserId);
        return CommonResult.getSuccessResultData(uemUserDto);
    }

    /**
     * 启用/禁用用户
     *
     * @param uemUserDto 用户表封装类
     * @return com.share.support.result.ResultHelper<?>
     * @date 2022-07-25
     */
    @ApiOperation("启用/禁用用户")
    @ApiImplicitParam(name = "uemUserDto", value = "用户信息封装类", required = true, dataType = "UemUserDto")
    @PostMapping("/uemUserStartStop")
    public ResultHelper<?> uemUserStartStop(@RequestBody UemUserDto uemUserDto) {
        return uemUserManageService.uemUserStartStop(uemUserDto);
    }

    /**
     * 修改用户信息
     *
     * @param uemUserEditDTO 用户信息新增修改接口入参
     * @return com.share.support.result.ResultHelper<?>
     * @date 2022-07-25
     */
    @ApiOperation("修改用户信息")
    @PostMapping("/editUemUser")
    @ApiImplicitParam(name = "uemUserEditDTO", value = "用户信息新增修改接口入参", required = true, dataType = "UemUserEditDTO")
    public ResultHelper<?> editUemUser(@RequestBody @Valid UemUserEditDTO uemUserEditDTO, BindingResult results) {
        if (results.hasErrors()) {
            //数据校验不通过
            FieldError fieldError = results.getFieldError();
            if (Objects.nonNull(fieldError)) {
                return CommonResult.getFaildResultData(fieldError.getDefaultMessage());
            }
        }
        return uemUserManageService.editUemUser(uemUserEditDTO);
    }

    /**
     * 删除用户信息
     *
     * @param uemUserId 用户ID
     * @return com.share.support.result.ResultHelper<?>
     * @date 2022-07-25
     */
    @ApiOperation("删除用户信息")
    @ApiImplicitParam(name = "uemUserId", value = "用户ID", required = true, dataType = "Long")
    @PostMapping("/deleteUemUser")
    public ResultHelper<?> deleteUemUser(Long uemUserId) {
        return uemUserManageService.deleteUemUser(uemUserId);
    }

    /**
     * 管理员新增用户
     *
     * @param uemUserEditDTO 用户信息新增修改接口入参
     * @return com.share.support.result.ResultHelper<?>
     * @date 2022-07-25
     */
    @ApiOperation("管理员新增用户")
    @ApiImplicitParam(name = "uemUserEditDTO", value = "用户信息新增修改接口入参", required = true, dataType = "UemUserEditDTO")
    @PostMapping(value = "/saveUemUser")
    public ResultHelper<?> saveUemUser(@RequestBody @Valid UemUserEditDTO uemUserEditDTO, BindingResult results) {
        if (results.hasErrors()) {
            //数据校验不通过
            FieldError fieldError = results.getFieldError();
            if (Objects.nonNull(fieldError)) {
                return CommonResult.getFaildResultData(fieldError.getDefaultMessage());
            }
        }
        return uemUserManageService.saveUemUser(uemUserEditDTO);
    }

    /**
     * 管理员重置用户密码
     *
     * @param uemUserId 用户id
     * @return com.share.support.result.ResultHelper<java.lang.String>
     * @date 2022-07-25
     */
    @ApiOperation("管理员重置用户密码")
    @PostMapping(value = "/resetUemUserPassword")
    public ResultHelper<?> resetUemUserPassword(Long uemUserId) {
        return uemUserManageService.resetUemUserPassword(uemUserId);
    }
}
