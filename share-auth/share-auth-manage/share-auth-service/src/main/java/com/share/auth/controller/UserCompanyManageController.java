package com.share.auth.controller;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.exception.BusinessRuntimeException;
import com.share.auth.domain.*;
import com.share.auth.service.UserCompanyManageService;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author xrp
 * @date 2020/11/3 0003
 */
@Api("企业用户管理模块")
@Controller
@RequestMapping("userCompany")
public class UserCompanyManageController {

    @Autowired
    private UserCompanyManageService userCompanyManageService;

    /**企业用户管理
     * @param uemUserDto 用户表封装类
     * @return Page<UemUserDto>
     * @author xrp
     * */
    @ApiOperation("企业用户管理")
    @ApiImplicitParam(name = "uemUserDto", value = "用户表封装类", required = true, dataType = "UemUserDto", paramType = "userCompanyManage")
    @GetMapping("/queryUserCompanyManage")
    @ResponseBody
    public ResultHelper<Page<UemUserDto>> queryUserCompanyManage(UemUserDto uemUserDto){
        Page<UemUserDto> uemUserDtoPage = userCompanyManageService.queryUserCompanyManage(uemUserDto);
        return CommonResult.getSuccessResultData(uemUserDtoPage);
    }

    /**
     * 查询企业解绑用户
     * @param uemUserCompanyDto 解绑表封装类
     * @return Page<UemUserDto>
     * @author xrp
     * */
    @ApiOperation("查询企业解绑用户")
    @ApiImplicitParam(name = "uemUserCompanyDto", value = "解绑表封装类", required = true, dataType = "UemUserCompanyDto", paramType = "queryUnBindUser")
    @GetMapping("/queryUnBindUser")
    @ResponseBody
    public ResultHelper<Page<UemUserCompanyDto>> queryUnBindUser(UemUserCompanyDto uemUserCompanyDto){
        Page<UemUserCompanyDto> uemUserCompanyDtoPage = userCompanyManageService.queryUnBindUser(uemUserCompanyDto);
        return CommonResult.getSuccessResultData(uemUserCompanyDtoPage);
    }

    /**
     * 新增企业用户
     *
     * @param uemUserDto 用户表封装类
     * @return Map<String, Object>
     * @author xrp
     */
    @ApiOperation("新增企业用户")
    @ApiImplicitParam(name = "uemUserDto", value = "用户表封装类", required = true, dataType = "UemUserDto", paramType = "saveUemUser")
    @PostMapping("/saveUemUser")
    @ResponseBody
    public ResultHelper<Object> saveUemUser(@RequestBody UemUserDto uemUserDto) {
        return userCompanyManageService.saveUemUser(uemUserDto);
    }

    /**
     * 用户启停
     *
     * @param uemUserDto 用户表封装类
     * @return Map<String, Object>
     * @author xrp
     */
    @ApiOperation("用户启停")
    @ApiImplicitParam(name = "uemUserDto", value = "用户表封装类", required = true, dataType = "UemUserDto", paramType = "startStop")
    @PostMapping("/startStop")
    @ResponseBody
    public ResultHelper<Object> startStop(@RequestBody UemUserDto uemUserDto) {
        return userCompanyManageService.startStop(uemUserDto);
    }

    /**
     * 解除绑定
     *
     * @param uemUserCompanyDto 用户企业绑定表封装类
     * @return Map<String, Object>
     * @author xrp
     */
    @ApiOperation("企业用户解除绑定")
    @ApiImplicitParam(name = "uemUserCompanyDto", value = "用户企业绑定表封装类", required = true, dataType = "UemUserCompanyDto", paramType = "unbindUemUser")
    @PostMapping("/unbindUemUser")
    @ResponseBody
    public ResultHelper<Object> unbindUemUser(@RequestBody UemUserCompanyDto uemUserCompanyDto) {
        return userCompanyManageService.unbindUemUser(uemUserCompanyDto.getUemUserId());
    }

    /**权限分配
     * @param uemUserId 用户表ID
     * @return List<UemUserRoleDto>
     * @author xrp
     * */
    @ApiOperation("企业用户权限分配")
    @ApiImplicitParam(name = "uemUserId", value = "用户ID", required = true, dataType = "String", paramType = "authorityAssignment")
    @GetMapping("/authorityAssignment")
    @ResponseBody
    public ResultHelper<List<UemUserRoleDto>> authorityAssignment(@RequestParam String uemUserId){
        return userCompanyManageService.authorityAssignment(uemUserId);
    }

    /**
     * 权限分配 获取详情
     * @param uemUserRoleId 用户角色表ID
     * @return List<UemUserRoleDto>
     * @author xrp
     * */
    @ApiOperation("权限分配 详情")
    @ApiImplicitParam(name = "uemUserRoleId", value = "用户角色表ID", required = true, dataType = "String", paramType = "getAuthorityAssignment")
    @GetMapping("/getAuthorityAssignment")
    @ResponseBody
    public ResultHelper<List<UemUserRoleDto>> getAuthorityAssignment(@RequestParam String uemUserRoleId) {
        List<UemUserRoleDto> uemUserRoleDtoList = userCompanyManageService.getAuthorityAssignment(uemUserRoleId);
        return CommonResult.getSuccessResultData(uemUserRoleDtoList);
    }

    /**
     * 权限分配 修改
     *
     * @param uemUserRoleDto 用户角色表
     * @return Map<String, Object>
     * @author xrp
     */
    @ApiOperation("权限分配 修改")
    @ApiImplicitParam(name = "uemUserRoleDto", value = "用户角色表", required = true, dataType = "UemUserRoleDto", paramType = "updateAuthorityAssignment")
    @PostMapping("/updateAuthorityAssignment")
    @ResponseBody
    public ResultHelper<Object> updateAuthorityAssignment(@RequestBody UemUserRoleDto uemUserRoleDto) {
        return userCompanyManageService.updateAuthorityAssignment(uemUserRoleDto);
    }

    /**
     * 权限分配 启停
     *
     * @param uemUserRoleDto 用户角色表
     * @return Map<String, Object>
     * @author xrp
     */
    @ApiOperation("权限分配 启停")
    @ApiImplicitParam(name = "uemUserRoleDto", value = "用户角色表", required = true, dataType = "UemUserRoleDto", paramType = "startStopByAuthorityAssignment")
    @PostMapping("/startStopByAuthorityAssignment")
    @ResponseBody
    public ResultHelper<Object> startStopByAuthorityAssignment(@RequestBody UemUserRoleDto uemUserRoleDto) {
        return userCompanyManageService.startStopByAuthorityAssignment(uemUserRoleDto);
    }

    /**
     * 权限分配 新加
     *
     * @param uemUserRoleDto 用户角色表
     * @return Map<String, Object>
     * @author xrp
     */
    @ApiOperation("权限分配 新加")
    @ApiImplicitParam(name = "uemUserRoleDto", value = "用户角色表", required = true, dataType = "UemUserRoleDto", paramType = "saveAuthorityAssignment")
    @PostMapping("/saveAuthorityAssignment")
    @ResponseBody
    public ResultHelper<Object> saveAuthorityAssignment(@RequestBody UemUserRoleDto uemUserRoleDto) {
        return userCompanyManageService.saveAuthorityAssignment(uemUserRoleDto);
    }

    /**
     * 根据手机号生成验证码
     * @param mobile 手机号
     * @return Map<String, Object>
     * @author xrp
     * */
    @Deprecated
    @ApiOperation("手机号获取验证码")
    @ApiImplicitParam(name = "mobile", value = "手机号", required = true, dataType = "String", paramType = "generateAuthCodeByMobile")
    @GetMapping("/generateAuthCodeByMobile")
    @ResponseBody
    public ResultHelper generateAuthCodeByMobile(@RequestParam String mobile){
        return userCompanyManageService.generateAuthCodeByMobile(mobile);
    }

    /**
     * 验证手机验证码是否正确
     *
     * @param uemUserDto 用户表
     * @return Map<String, Object>
     * @author xrp
     */
    @ApiOperation("验证手机验证码是否正确")
    @ApiImplicitParam(name = "uemUserDto", value = "用户表", required = true, dataType = "UemUserDto", paramType = "verifyAuthCodeByMobile")
    @PostMapping("/verifyAuthCodeByMobile")
    @ResponseBody
    public ResultHelper<Object> verifyAuthCodeByMobile(@RequestBody UemUserDto uemUserDto) {
        return userCompanyManageService.verifyAuthCodeByMobile(uemUserDto);
    }

    /**
     * 根据邮箱生成验证码
     *
     * @param email 邮箱
     * @return Map<String, Object>
     * @author xrp
     */
    @ApiOperation("根据邮箱生成验证码")
    @ApiImplicitParam(name = "email", value = "邮箱", required = true, dataType = "String", paramType = "generateAuthCodeByEmail")
    @GetMapping("/generateAuthCodeByEmail")
    @ResponseBody
    public ResultHelper<Object> generateAuthCodeByEmail(@RequestParam String email) {
        return userCompanyManageService.generateAuthCodeByEmail(email);
    }

    /**
     * 验证邮箱验证码是否正确
     *
     * @param uemUserDto 用户表
     * @return Map<String, Object>
     * @author xrp
     */
    @ApiOperation("验证邮箱验证码是否正确")
    @ApiImplicitParam(name = "uemUserDto", value = "用户表", required = true, dataType = "UemUserDto", paramType = "verifyAuthCodeByEmail")
    @PostMapping("/verifyAuthCodeByEmail")
    @ResponseBody
    public ResultHelper<Object> verifyAuthCodeByEmail(@RequestBody UemUserDto uemUserDto) {
        return userCompanyManageService.verifyAuthCodeByEmail(uemUserDto);
    }

    /**
     * 登录日志  个人
     * @param uemLogDto 登录日志表
     * @return Page<UemLogDto>
     * @author xrp
     * */
    @ApiOperation("登录日志  个人")
    @ApiImplicitParam(name = "uemLogDto", value = "登录日志表", required = true, dataType = "UemLogDto", paramType = "loginLogIndividual")
    @GetMapping("/loginLogIndividual")
    @ResponseBody
    public ResultHelper<Page<UemLogDto>> loginLogIndividual(UemLogDto uemLogDto){
        Page<UemLogDto> page = userCompanyManageService.loginLogIndividual(uemLogDto);
        return CommonResult.getSuccessResultData(page);
    }

    /**
     * 登录日志  全部
     * @param uemLogDto 登录日志表
     * @return Page<UemLogDto>
     * @author xrp
     * */
    @ApiOperation("登录日志  全部")
    @ApiImplicitParam(name = "uemLogDto", value = "登录日志表", required = true, dataType = "UemLogDto", paramType = "loginLogAll")
    @GetMapping("/loginLogAll")
    @ResponseBody
    public ResultHelper<Page<UemLogDto>> loginLogAll(UemLogDto uemLogDto){
        Page<UemLogDto> page = userCompanyManageService.loginLogAll(uemLogDto);
        return CommonResult.getSuccessResultData(page);
    }

    /**
     * @Author:chenxf
     * @Description: 企业用户绑定审核
     * @Date: 10:04 2021/2/23
     * @Param: [uemUserDto]
     * @Return:com.share.support.result.ResultHelper
     */
    @PostMapping("/reviewUemUserCompany")
    @ResponseBody
    @ApiOperation(value = "企业用户绑定审核", notes = "企业用户绑定审核")
    @ApiImplicitParam(name = "uemUserDto", value = "用户id", required = true, dataType = "UemUserDto", paramType = "reviewUemUserCompany")
    public ResultHelper<Object> reviewUemUserCompany(@RequestBody UemUserDto uemUserDto) {
        return userCompanyManageService.reviewUemUserCompany(uemUserDto);
    }

    /**
     * 权限开通发送短信
     * @param uemUserId 用户id
     * @param sysApplicationId 开通应用id
     * @return 发送短信结果
     */
    @ResponseBody
    @GetMapping("/sendMessage")
    @ApiOperation("权限开通发送短信")
    public ResultHelper<Object> sendMessage(Long uemUserId, Long sysApplicationId) {
        return userCompanyManageService.sendMessage(uemUserId, sysApplicationId);
    }


}
