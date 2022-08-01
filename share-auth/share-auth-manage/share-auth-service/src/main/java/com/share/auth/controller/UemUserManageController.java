package com.share.auth.controller;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.center.api.AuthCenterInterface;
import com.share.auth.domain.SysRoleDTO;
import com.share.auth.domain.UemUserDto;
import com.share.auth.domain.UemUserEditDTO;
import com.share.auth.domain.UemUserRoleDto;
import com.share.auth.service.UemUserManageService;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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
     * @return ResultHelper<Page < UemUserDto>>
     * @date 2022-07-25
     */
    @ApiOperation("根据用户名、姓名或启禁用状态查询用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "用户名", dataTypeClass = String.class, paramType = "body"),
            @ApiImplicitParam(name = "name", value = "真实姓名", dataTypeClass = String.class, paramType = "body"),
            @ApiImplicitParam(name = "isValid", value = "启用/禁用状态", dataTypeClass = Boolean.class, paramType = "body")
    })
    @PostMapping("/queryUemUser")
    public ResultHelper<Page<UemUserDto>> queryUemUser(@RequestBody UemUserDto uemUserDto) {
        return uemUserManageService.queryUemUser(uemUserDto);
    }

    /**
     * 获取用户信息
     *
     * @date 2022-07-25
     */
    @ApiOperation("获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uemUserId", value = "用户ID", required = true, dataTypeClass = Long.class, paramType = "body")
    })
    @GetMapping("/getUemUser")
    public ResultHelper<UemUserDto> getUemUser(@RequestParam Long uemUserId) {
        return uemUserManageService.getUemUser(uemUserId);
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
     * @date 2022-07-25
     */
    @ApiOperation("删除用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uemUserId", value = "用户ID", required = true, dataTypeClass = Long.class, paramType = "body")
    })
    @PostMapping("/deleteUemUser")
    public ResultHelper<?> deleteUemUser(@RequestBody UemUserDto uemUserDto) {
        return uemUserManageService.deleteUemUser(uemUserDto.getUemUserId());
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
     * @date 2022-07-25
     */
    @ApiOperation("管理员重置用户密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uemUserId", value = "用户ID", required = true, dataTypeClass = Long.class, paramType = "body")
    })
    @PostMapping(value = "/resetUemUserPassword")
    public ResultHelper<?> resetUemUserPassword(@RequestBody UemUserDto uemUserDto) {
        return uemUserManageService.resetUemUserPassword(uemUserDto.getUemUserId());
    }

    /**
     * 根据用户ID获取角色列表
     *
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-28
     */
    @PostMapping("/queryRoleListByUser")
    @ApiOperation("根据用户ID获取角色列表")
    @ApiImplicitParam(name = "uemUserId", value = "用户ID", required = true, dataTypeClass = Long.class, paramType = "body")
    public ResultHelper<List<SysRoleDTO>> queryRoleListByUser(@RequestBody UemUserDto uemUserDto) {
        return uemUserManageService.queryRoleListByUser(uemUserDto);
    }

    /**
     * 赋予用户角色
     *
     * @param uemUserRoleDtoList 获取uemUserId和sysRoleId
     * @return com.share.support.result.ResultHelper<?>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-28
     */
    @PostMapping("/bindUserAndRole")
    @ApiOperation("赋予用户角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uemUserId", value = "用户ID", required = true, dataType = "Long", paramType = "body"),
            @ApiImplicitParam(name = "sysRoleId", value = "角色ID", required = true, dataType = "Long", paramType = "body")
    })
    public ResultHelper<?> bindUserAndRole(@RequestBody List<UemUserRoleDto> uemUserRoleDtoList) {
        return uemUserManageService.bindUserAndRole(uemUserRoleDtoList);
    }

    /**
     * 清除一个用户的所有角色
     *
     * @param uemUserDto 用户信息
     * @return com.share.support.result.ResultHelper<?>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-28
     */
    @PostMapping("/unbindAllRoleOfUser")
    @ApiOperation("清除一个用户的所有角色")
    @ApiImplicitParam(name = "uemUserId", value = "用户ID", required = true, dataType = "Long", paramType = "body")
    public ResultHelper<?> unbindAllRoleOfUser(@RequestBody UemUserDto uemUserDto) {
        return uemUserManageService.unbindAllRoleOfUser(uemUserDto.getUemUserId());
    }

    /**
     * 分页待条件查询员工信息
     *
     * @param uemUserDto 员工信息
     * @author wzr
     * @date 2022-08-01
     */
    @PostMapping("/queryStaffByPage")
    @ApiOperation(value = "分页带条件查询所有员工的信息")
    public Page<UemUserDto> queryStaffByPage(@RequestBody UemUserDto uemUserDto) {
        Page<UemUserDto> uemUserDtoPage = uemUserManageService.queryStaffByPage(uemUserDto);
        return uemUserDtoPage;
    }
}
