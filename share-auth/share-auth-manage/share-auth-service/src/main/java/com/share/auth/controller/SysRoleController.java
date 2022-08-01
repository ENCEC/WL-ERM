package com.share.auth.controller;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.SysResourceDTO;
import com.share.auth.domain.SysRoleDTO;
import com.share.auth.model.entity.SysResource;
import com.share.auth.model.entity.SysRole;
import com.share.auth.model.vo.SysRoleQueryVO;
import com.share.auth.service.SysResourceService;
import com.share.auth.service.SysRoleService;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门角色管理
 *
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
     * @author wzr
     * @date 2022-07-29
     */
    @PostMapping("/saveSysRole")
    @ApiOperation("添加角色")
    @ApiImplicitParam(name = "sysRoleDTO", value = "添加角色接口入参", required = true, dataType = "SysRoleDTO")
    public ResultHelper<Object> saveSysRole(@RequestBody SysRoleDTO sysRoleDTO) {
        return sysRoleService.saveSysRole(sysRoleDTO);
    }


    /**
     * 分页带条件分页查询
     *
     * @author wzr
     * @date 2022-07-29
     */
    @PostMapping("/queryRoleByPage")
    @ApiOperation(value = "带条件分页查询角色信息")
    public ResultHelper<Page<SysRoleDTO>> queryRoleByPage(@RequestBody SysRoleDTO sysRoleDTO) {
        Page<SysRoleDTO> sysRoleDTOPage = sysRoleService.queryRoleByPage(sysRoleDTO);
        return CommonResult.getSuccessResultData(sysRoleDTOPage);
    }

    /**
     * 根据id查询角色
     *
     * @author wzr
     * @date 2022-07-29
     */
    @GetMapping("/queryRoleAndResourceById")
    @ApiOperation("根据id查询角色以及绑定的权限信息")
    public List<SysRoleDTO> queryRoleAndResource(@RequestParam Long sysRoleId) {
        return sysRoleService.queryRoleById(sysRoleId);
    }

    /**
     * 更新角色
     *
     * @author weizr
     * @date 2022-07-22
     */
    @PostMapping("/updateSysRole")
    @ApiOperation("更新角色")
    @ApiImplicitParam(name = "sysRoleDTO", value = "启用/禁用角色接口入参", required = true, dataType = "SysRoleDTO")
    public ResultHelper<Object> updateValid(@RequestBody SysRoleDTO sysRoleDTO) {
        return sysRoleService.updateSysRole(sysRoleDTO);
    }

    /**
     * 删除角色
     *
     * @author wzr
     * @date 2022-07-29
     */
    @GetMapping("/deleteRole")
    @ApiOperation("删除角色")
    @ApiImplicitParam(name = "sysRoleId", value = "删除角色接口入参", required = true, dataType = "Long")
    public ResultHelper<Object> deleteRole(@RequestParam(name = "sysRoleId") Long sysRoleId) {
        return sysRoleService.deleteRole(sysRoleId);
    }

    /**ne
     * 角色信息是否禁用状态
     *
     * @author wzr
     * @date 2022-07-29
     */
    @ApiOperation("更改状态")
    @PostMapping("/updateRoleStatus")
    @ResponseBody
    public ResultHelper<Object> updateRoleStatus(@RequestBody SysRoleDTO sysRoleDTO) {
        return sysRoleService.updateRoleStatus(sysRoleDTO);
    }

    /**
     * 获取所有未禁用角色
     *
     * @return com.share.support.result.ResultHelper<java.util.List < com.share.support.model.Role>>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-28
     */
    @GetMapping("/queryAllValidRole")
    @ApiOperation(value = "获取所有未禁用角色")
    public ResultHelper<List<SysRoleDTO>> queryAllValidRole() {
        return sysRoleService.queryAllValidRole();
    }
}
