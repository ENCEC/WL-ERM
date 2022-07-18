package com.share.auth.controller;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.QueryResourceDTO;
import com.share.auth.domain.SysDictCodeVO;
import com.share.auth.domain.SysResourceDTO;
import com.share.auth.domain.SysResourceQueryVO;
import com.share.auth.model.vo.SysResourceVO;
import com.share.auth.service.SysResourceNewService;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @descriptionSysResource控制层
 * @Author: zhuyp
 * @Date: 2021/12/7 17:49
 */
@Slf4j
@RestController
@Api("资源控制器")
public class SysResourceNewController {
    @Autowired
    private SysResourceNewService sysResourceNewService;

    /**
     * 查询资源分页信息
     * @param sysResourceDto
     * @return ResultHelper<Page<SysResourceVO>>
     */
    @PostMapping("/sysResource/queryResourcePage")
    @ApiOperation(value = "查询应用分页数据", notes = "返回资源分页数据")
    @ApiImplicitParam(name = "sysResourceDTO", value = "查询资源参数", required = true, dataType = "sysResourceDTO", paramType = "queryResourcePage")
    public ResultHelper<Page<SysResourceVO>> queryResourcePage(@RequestBody SysResourceDTO sysResourceDto) {
        return CommonResult.getSuccessResultData(sysResourceNewService.selectSysResourcePage(sysResourceDto));
    }

    /**
     * 根据id查询应用详情
     * @param sysResourceId
     * @return
     */
    @GetMapping("/sysResource/queryResourceById")
    @ApiOperation(value = "根据id查询应用数据", notes = "返回资源单条数据")
    @ApiImplicitParam(name = "sysResourceId", value = "查询资源参数Id", required = true, dataType = "Long", paramType = "queryResourceById")
    public ResultHelper<SysResourceVO> queryResourceById(@RequestParam(value = "sysResourceId",required = true)Long sysResourceId) {
        return CommonResult.getSuccessResultData(sysResourceNewService.selectSysResourceById(sysResourceId));
    }

    @GetMapping("/sysResource/queryResourceByResourcePid")
    @ApiOperation(value = "根据父页面/菜单id查询资源列表", notes = "返回子资源列表数据")
    @ApiImplicitParam(name = "resourcePid", value = "父页面/菜单id", required = true, dataType = "Long", paramType = "queryResourceByResourcePid")
    public ResultHelper<SysResourceVO> queryResourceByResourcePid(@RequestParam(value = "resourcePid")Long resourcePid) {
        return CommonResult.getSuccessResultData(sysResourceNewService.selectSysResourceByResourcePid(resourcePid));
    }

    /**
     * 保存资源分页信息
     * @param sysResourceDto
     * @return ResultHelper<Page<SysResourceVO>>
     */
    @PostMapping("/sysResource/saveOrUpdate")
    @ApiOperation(value = "保存", notes = "保存")
    @ApiImplicitParam(name = "sysResourceDTO", value = "查询资源参数", required = true, dataType = "sysResourceDTO", paramType = "saveOrUpdate")
    public ResultHelper<Integer> saveOrUpdate(@RequestBody SysResourceDTO sysResourceDto) {
        return CommonResult.getSuccessResultData(sysResourceNewService.saveOrUpdate(sysResourceDto));
    }
    /**
     * 查询父级菜单作为下拉框
     * @param
     * @return
     */
    @GetMapping("/sysResource/selectSysParentsResourceList")
    @ApiOperation(value = "查询父级菜单作为下拉框", notes = "查询父级菜单作为下拉框")
    public ResultHelper<Map<String, List<SysDictCodeVO>>> selectSysParentsResourceList() {
        return CommonResult.getSuccessResultData(sysResourceNewService.selectSysParentsResourceList());
    }
    /**
     * 查询关联应用作为下拉框
     * @param
     * @return
     */
    @GetMapping("/sysResource/selectSysApplicationList")
    @ApiOperation(value = "查询关联应用作为下拉框", notes = "查询关联应用作为下拉框")
    public ResultHelper<Map<String, List<SysDictCodeVO>>> selectSysApplicationList() {
        return CommonResult.getSuccessResultData(sysResourceNewService.selectSysApplicationList());
    }
    /**
     * 查询关联应用作为下拉框
     * @param
     * @return
     */
    @GetMapping("/sysResource/selectSysResourceList")
    @ApiOperation(value = "查询菜单作为下拉框", notes = "查询菜单作为下拉框")
    @ApiImplicitParam(name = "sysResourceId", value = "查询资源参数Id", required = true, dataType = "Long", paramType = "queryResourceById")
    public ResultHelper<Map<String, List<SysDictCodeVO>>> selectSysResourceList(@RequestParam(value = "sysResourceId",required = true)Long sysResourceId) {
        return CommonResult.getSuccessResultData(sysResourceNewService.selectSysResourceList(sysResourceId));
    }


    /**
     * 修改资源分页信息
     * @param sysResourceDto
     * @return ResultHelper<Integer>
     */
    @PostMapping("/sysResource/updateResource")
    @ApiOperation(value = "保存", notes = "保存")
    @ApiImplicitParam(name = "sysResourceDTO", value = "查询资源参数", required = true, dataType = "sysResourceDTO", paramType = "updateResource")
    public ResultHelper<Integer> updateResource(@RequestBody SysResourceDTO sysResourceDto) {
        return CommonResult.getSuccessResultData(sysResourceNewService.updateResource(sysResourceDto));
    }
    /**
     * 菜单名单和应用唯一性校验
     * @param sysResourceDto
     * @return ResultHelper<Boolean>
     */
    @PostMapping("/sysResource/uniqueValidate")
    @ApiOperation(value = "菜单名单和应用唯一性校验", notes = "菜单名单和应用唯一性校验")
    @ApiImplicitParam(name = "sysResourceDTO", value = "查询资源参数", required = true, dataType = "sysResourceDTO", paramType = "uniqueValidate")
    public ResultHelper<Boolean> uniqueValidate(@RequestBody SysResourceDTO sysResourceDto) {
        return CommonResult.getSuccessResultData(sysResourceNewService.uniqueValidate(sysResourceDto));
    }
    /**
     * 启用状态下的父级菜单如果其下子菜单存在启用状态的子菜单，不能禁用
     * @param sysResourceId
     * @return ResultHelper<Boolean>
     */
    @GetMapping("/sysResource/forbiddenValidate")
    @ApiOperation(value = "启用状态下的父级菜单如果其下子菜单存在启用状态的子菜单，不能禁用", notes = "启用状态下的父级菜单如果其下子菜单存在启用状态的子菜单，不能禁用")
    @ApiImplicitParam(name = "forbiddenValidate", value = "查询参数", required = true, dataType = "Long", paramType = "forbiddenValidate")
    public ResultHelper<Boolean> forbiddenValidate(@RequestParam(value = "sysResourceId",required = true) Long sysResourceId) {
        return CommonResult.getSuccessResultData(sysResourceNewService.forbiddenValidate(sysResourceId));
    }
    /**
     * 禁用状态下的子菜单如果其父菜单为禁用状态，不能启用
     * @param sysResourceId
     * @return ResultHelper<Boolean>
     */
    @GetMapping("/sysResource/openValidate")
    @ApiOperation(value = "禁用状态下的子菜单如果其父菜单为禁用状态，不能启用", notes = "禁用状态下的子菜单如果其父菜单为禁用状态，不能启用")
    @ApiImplicitParam(name = "openValidate", value = "查询参数", required = true, dataType = "Long", paramType = "openValidate")
    public ResultHelper<Boolean> openValidate(@RequestParam(value = "sysResourceId",required = true) Long sysResourceId) {
        return CommonResult.getSuccessResultData(sysResourceNewService.openValidate(sysResourceId));
    }
    /**
     * 禁用状态下的子菜单如果其父菜单为禁用状态，不能启用
     * @param resourcePid
     * @return ResultHelper<Boolean>
     */
    @GetMapping("/sysResource/existSonResource")
    @ApiOperation(value = "父菜单是否存在子菜单", notes = "父菜单是否存在子菜单")
    @ApiImplicitParam(name = "existSonResource", value = "查询参数", required = true, dataType = "Long", paramType = "existSonResource")
    public ResultHelper<Boolean> existSonResource(@RequestParam(value = "resourcePid",required = true) String resourcePid) {
        Long resourcePidLong = new Long(resourcePid);
        return CommonResult.getSuccessResultData(sysResourceNewService.existSonResource(resourcePidLong));
    }
}
