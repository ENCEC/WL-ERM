package com.share.auth.controller;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.SysRoleDTO;
import com.share.auth.model.vo.SysRoleQueryVO;
import com.share.auth.service.SysResourceService;
import com.share.auth.service.SysRoleService;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门角色管理
 * @author xuzt <xuzt@gillion.com.cn>
 * @date 2022-07-22
 */
@Api("部门角色管理")
@RestController
@RequestMapping("/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysResourceService sysResourceService;

    /**
     * 根据部门ID获取角色列表
     *
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-22
     */
    @PostMapping("/getRoleListByDeptId")
    @ApiOperation("根据部门ID获取角色列表")
    @ApiImplicitParam(name = "deptId", value = "部门ID", required = true, dataType = "Long")
    public ResultHelper<Page<SysRoleDTO>> getRoleListByDeptId(@RequestParam(name = "deptId") Long deptId) {
        throw new UnsupportedOperationException();
    }

    /**
     * 根据部门ID获取角色列表
     *
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-22
     */
    @PostMapping("/queryRoleList")
    @ApiOperation("根据部门ID获取角色列表")
    @ApiImplicitParam(name = "sysRoleQueryVo", value = "角色管理查询接口入参", required = true, dataType = "SysRoleQueryVO")
    public ResultHelper<Page<SysRoleDTO>> queryRoleList(@RequestBody SysRoleQueryVO sysRoleQueryVo) {
        throw new UnsupportedOperationException();
    }

    /**
     * 添加角色
     *
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-22
     */
    @PostMapping("/saveSysRole")
    @ApiOperation("添加角色")
    @ApiImplicitParam(name = "sysRoleDTO", value = "添加角色接口入参", required = true, dataType = "SysRoleDTO")
    public ResultHelper<Object> saveSysRole(@RequestBody SysRoleDTO sysRoleDTO) {
        throw new UnsupportedOperationException();
    }

    /**
     * 根据角色ID获取角色信息
     *
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-22
     */
    @PostMapping("/getSysRoleById")
    @ApiOperation("根据角色ID获取角色信息")
    @ApiImplicitParam(name = "sysRoleId", value = "角色ID", required = true, dataType = "Long")
    public ResultHelper<SysRoleDTO> getSysRoleById(@RequestParam(name = "sysRoleId") Long sysRoleId) {
        throw new UnsupportedOperationException();
    }

    /**
     * 修改角色
     *
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-22
     */
    @PostMapping("/updateSysRole")
    @ApiOperation("修改角色")
    @ApiImplicitParam(name = "sysRoleDTO", value = "修改角色接口入参", required = true, dataType = "SysRoleDTO")
    public ResultHelper<Object> updateSysRole(@RequestBody SysRoleDTO sysRoleDTO) {
        throw new UnsupportedOperationException();
    }

    /**
     * 启用/禁用角色
     *
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-22
     */
    @PostMapping("/updateValid")
    @ApiOperation("启用/禁用角色")
    @ApiImplicitParam(name = "sysRoleDTO", value = "启用/禁用角色接口入参", required = true, dataType = "SysRoleDTO")
    public ResultHelper<Object> updateValid(@RequestBody SysRoleDTO sysRoleDTO) {
        throw new UnsupportedOperationException();
    }

    /**
     * 删除角色
     *
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-22
     */
    @PostMapping("/deleteSysRole")
    @ApiOperation("删除角色")
    @ApiImplicitParam(name = "sysRoleId", value = "删除角色接口入参", required = true, dataType = "Long")
    public ResultHelper<Object> deleteSysRole(@RequestParam(name = "sysRoleId") Long sysRoleId) {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取所有未禁用角色
     * @return com.share.support.result.ResultHelper<java.util.List<com.share.support.model.Role>>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-28
     */
    @GetMapping("/queryAllValidRole")
    @ApiOperation(value = "获取所有未禁用角色")
    public ResultHelper<List<SysRoleDTO>> queryAllValidRole() {
        return sysRoleService.queryAllValidRole();
    }
}
