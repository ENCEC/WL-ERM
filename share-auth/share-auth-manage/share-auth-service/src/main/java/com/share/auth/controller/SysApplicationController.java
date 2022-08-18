package com.share.auth.controller;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.QueryApplicationDTO;
import com.share.auth.domain.SysApplicationDto;
import com.share.auth.domain.UemUserRoleDto;
import com.share.auth.model.entity.SysApplication;
import com.share.auth.model.entity.UemUser;
import com.share.auth.service.SysApplicationService;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author chenxf
 * @date 2020-10-26 16:16
 */
@Api("应用管理控制器")
@RestController
public class SysApplicationController {

    @Autowired
    private SysApplicationService sysApplicationService;

    /**
     * 获取所有的应用
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-08-17
     */
    @GetMapping("/application/queryAllApplication")
    @ApiOperation(value = "获取所有的应用", notes = "获取所有的应用")
    public ResultHelper<List<QueryApplicationDTO>> queryAllApplication() {
        return CommonResult.getSuccessResultData(sysApplicationService.queryAllApplication());
    }

    /**
     * @Author:chenxf
     * @Description: 根据opTyp查询应用接口
     * @Date: 17:54 2020/11/28
     * @Param: [opType]
     * @Return:java.util.List<com.share.auth.domain.QueryApplicationDTO>
     *
     */
    @PostMapping(value = "/application/queryApplicationForOpType")
    @ApiOperation(value = "查询应用",notes = "返回应用信息")
    @ApiImplicitParam(name = "opType", value = "查询应用", required = true, dataType = "String", paramType = "queryApplicationForOpType")
    public List<QueryApplicationDTO> queryApplicationForOpType(@RequestParam String opType){
        return sysApplicationService.queryApplicationForOpType(opType);
    }

    /**应用管理
     * @param sysApplicationDto 应用管理表映射表
     * @return Page<SysApplication>
     * @author xrp
     * */
    @ApiOperation("应用管理")
    @ApiImplicitParam(name = "sysApplicationDto", value = "应用管理入参", required = true, dataType = "SysApplicationDto", paramType = "querySysApplication")
    @GetMapping("/application/querySysApplication")
    public ResultHelper<Page<SysApplicationDto>> querySysApplication(SysApplicationDto sysApplicationDto) {
        Page<SysApplicationDto> sysApplicationPage = sysApplicationService.querySysApplication(sysApplicationDto);
        return CommonResult.getSuccessResultData(sysApplicationPage);
    }

    /**
     * 保存应用信息表数据接口
     *
     * @param sysApplicationDto 应用管理表映射表
     * @return
     * @author xrp
     */
    @ApiOperation("应用管理新增")
    @ApiImplicitParam(name = "sysApplicationDto", value = "应用实体", required = true, dataType = "SysApplicationDto", paramType = "saveSysApplication")
    @PostMapping("/application/saveSysApplication")
    public ResultHelper<Object> saveSysApplication(@RequestBody SysApplicationDto sysApplicationDto) {
        return sysApplicationService.saveSysApplication(sysApplicationDto);
    }

    /**
     * 更新应用信息表数据接口
     *
     * @param sysApplicationDto 应用管理表映射表
     * @return
     * @author xrp
     */
    @ApiOperation("应用管理修改")
    @ApiImplicitParam(name = "sysApplicationDto", value = "应用实体", required = true, dataType = "SysApplicationDto", paramType = "updateSysApplication")
    @PostMapping("/application/updateSysApplication")
    public ResultHelper<Object> updateSysApplication(@RequestBody SysApplicationDto sysApplicationDto) {
        return sysApplicationService.updateSysApplication(sysApplicationDto);
    }

    /**应用管理  应用详情
     * @param sysApplicationId 应用表的ID
     * @return
     * @author xrp
     * */
    @ApiOperation("应用管理  应用详情")
    @ApiImplicitParam(name = "sysApplicationId", value = "应用Id", required = true, dataType = "String", paramType = "getSysApplication")
    @GetMapping("/application/getSysApplication")
    public ResultHelper<List<SysApplication>> getSysApplication(@RequestParam String sysApplicationId){
        List<SysApplication> sysApplicationDtoList = sysApplicationService.getSysApplication(sysApplicationId);
        return CommonResult.getSuccessResultData(sysApplicationDtoList);
    }

    /**
     * @Author:chenxf
     * @Description: 权限分配应用下拉框查询接口
     * @Date: 18:58 2021/1/30
     * @Param: []
     * @Return:com.share.support.result.ResultHelper
     */
    @GetMapping("/application/queryApplicationForCompanyRole")
    @ResponseBody
    @ApiOperation(value = "权限分配应用下拉框查询接口", notes = "权限分配应用下拉框查询接口")
    public ResultHelper<List<SysApplication>> queryApplicationForCompanyRole() {
        return sysApplicationService.queryApplicationForCompanyRole();
    }

    /**
     * @Author: cjh
     * @Description: 权限分配应用下拉框查询接口-用户管理
     * @Date: 18:58 2021/11/30
     * @Param: []
     * @Return:com.share.support.result.ResultHelper
     */
    @GetMapping("/application/queryApplicationForCompanyRoleByUser")
    @ResponseBody
    @ApiOperation(value = "权限分配应用下拉框查询接口", notes = "权限分配应用下拉框查询接口")
    @ApiImplicitParam(name = "uemUserId", value = "用户ID", required = true, dataType = "String", paramType = "queryApplicationForCompanyRoleByUser")
    public ResultHelper<List<SysApplication>> queryApplicationForCompanyRoleByUser(@RequestParam String uemUserId) {
        return sysApplicationService.queryApplicationForCompanyRoleByUser(Long.valueOf(uemUserId));
    }

    /**
     * 根据clientId查询应用
     * @param clientId 应用代码
     * @return 应用
     */
    @GetMapping("/application/getSysApplicationByClientId")
    @ApiOperation("权限申请应用信息查询")
    @ApiImplicitParam(name = "clientId", value = "应用编码", required = true, dataType = "String", paramType = "getSysApplicationByClientId")
    public ResultHelper<SysApplication> getSysApplicationByClientId(@RequestParam String clientId) {
        return sysApplicationService.getSysApplicationByClientId(clientId);
    }

}
