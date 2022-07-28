package com.share.auth.controller;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.*;
import com.share.auth.model.entity.SysResource;
import com.share.auth.service.SysResourceService;
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
 * @author chenxf
 * @date 2020-09-28 11:00
 */
@Slf4j
@RestController
@Api(value = "资源控制器")
public class SysResourceController {

    @Autowired
    private SysResourceService sysResourceService;

    /**
     * @Author:chenxf
     * @Description: 根据应用id，用户id获取资源集合
     * @Date: 16:58 2020/9/28
     * @Param: []
     * @Return:java.util.List<com.share.auth.model.entity.SysResource>
     */
    @PostMapping("/sysResource/queryResource")
    @ApiOperation(value = "根据应用clientId，用户id，父级权限id，获取资源", notes = "返回资源")
    @ApiImplicitParam(name = "sysResourceQueryVO", value = "查询资源参数", required = true, dataType = "SysResourceQueryVO", paramType = "queryResource")
    public ResultHelper<List<QueryResourceDTO>> queryResource(@RequestBody SysResourceQueryVO sysResourceQueryVO) {
        return sysResourceService.queryResource(sysResourceQueryVO);
    }

    /**
     * @Author:kzb
     * @Description: 根据应用id，用户id获取所有系统的资源集合
     * @Date: 16:58 2021/9/02
     * @Param: []
     * @Return:java.util.List<com.share.auth.model.entity.SysResource>
     */
    @PostMapping("/sysResource/queryResourceAllSystem")
    @ApiOperation(value = "根据应用clientId，用户id，父级权限id，获取资源", notes = "返回资源")
    @ApiImplicitParam(name = "sysResourceQueryVO", value = "查询资源参数", required = true, dataType = "SysResourceQueryVO", paramType = "queryResource")
    public ResultHelper<Map<String, List<QueryResourceDTO>>> queryResourceAllSystem(@RequestBody SysResourceQueryVO sysResourceQueryVO) {
        return sysResourceService.queryResourceAllSystem(sysResourceQueryVO);
    }

    /**
     * 根据应用ID、用户ID和页面URL获取页面的按钮列表
     *
     * @author xzt
     * @date 2022-07-06
     */
    @PostMapping("/sysResource/queryButtonInPage")
    @ApiOperation(value = "根据应用ID、用户ID和页面URL获取页面的按钮列表")
    public ResultHelper<List<QueryResourceDTO>> queryButtonInPage(@RequestBody SysResourceQueryVO sysResourceQueryVO) {
        return sysResourceService.queryButtonInPage(sysResourceQueryVO);
    }

    /**
     * 新增菜单
     *
     * @author wzr
     * @date 2022-07-25
     */
    @PostMapping("/saveResource")
    @ApiOperation(value = "新增菜单")
    public ResultHelper<Object> saveResource(@RequestBody SysResourceDTO sysResourceDTO) {
        return sysResourceService.saveResource(sysResourceDTO);
    }

    /**
     * 分页带条件分页查询
     *
     * @author wzr
     * @date 2022-07-25
     */
    @PostMapping("/queryResourceByPage")
    @ApiOperation(value = "带条件分页查询菜单信息")
    public ResultHelper<Page<SysResourceDTO>> queryResourceByPage(@RequestBody SysResourceDTO sysResourceDTO) {
        Page<SysResourceDTO> sysResourceDTOPage = sysResourceService.queryResourceByPage(sysResourceDTO);
        return CommonResult.getSuccessResultData(sysResourceDTOPage);
    }

    /**
     * 根据id查询菜单信息
     *
     * @author wzr
     * @date 2022-07-25
     */
    @GetMapping("/queryResourceById")
    @ApiOperation(value = "根据id查询菜单信息")
    public SysResourceDTO queryResourceById(@RequestParam Long sysResourceId) {
        return sysResourceService.queryResourceById(sysResourceId);
    }

    /**
     * 修改菜单信息
     *
     * @author wzr
     * @date 2022-07-25
     */
    @PostMapping("/updateResource")
    @ApiOperation(value = "编辑菜单信息")
    public ResultHelper<Object> updateResource(@RequestBody SysResourceDTO sysResourceDTO) {
        return sysResourceService.updateResource(sysResourceDTO);
    }

    /**
     * 菜单信息是否禁用状态
     *
     * @author wzr
     * @date 2022-07-26
     */
    @ApiOperation("更改状态")
    @PostMapping("/updateResourceStatus")
    @ResponseBody
    public ResultHelper<Object> updateDictCodeStatus(@RequestBody SysResourceDTO sysResourceDTO) {
        return sysResourceService.updateResourceStatus(sysResourceDTO);
    }

    /**
     * 逻辑删除菜单信息
     *
     * @author wzr
     * @date 2022-07-26
     */
    @ApiOperation("逻辑删除菜单信息")
    @GetMapping("/deleteResourceById")
    public ResultHelper<Object> deleteResourceById(@RequestParam Long sysResourceId) {
        return sysResourceService.deleteResource(sysResourceId);
    }

    @ApiOperation("查询父级菜单信息")
    @PostMapping("/queryParentResource")
    public List<SysResourceDTO> queryParentResource() {
        return sysResourceService.queryParentResource();
    }
}
