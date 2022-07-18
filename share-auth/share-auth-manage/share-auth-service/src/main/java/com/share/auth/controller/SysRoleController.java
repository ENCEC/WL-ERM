package com.share.auth.controller;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.gillion.exception.BusinessRuntimeException;
import com.share.auth.domain.QueryResourceDTO;
import com.share.auth.domain.SysRoleDTO;
import com.share.auth.model.vo.SysRoleQueryVO;
import com.share.auth.service.SysResourceService;
import com.share.auth.service.SysRoleService;
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
import java.util.Objects;

/**
 * @Author:chenxf
 * @Description: 角色管理控制器
 * @Date: 14:18 2020/11/16
 * @Param:
 * @Return:
 *
 */
@Api("角色管理控制器")
@Controller
@RequestMapping("/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysResourceService sysResourceService;

    /**
     * @Author:chenxf
     * @Description: 角色管理查询树形表格角色数据接口
     * @Date: 17:53 2020/11/28
     * @Param: [sysRoleQueryVo]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     *
     */
    @PostMapping("/queryRoleTreeTable")
    @ResponseBody
    @ApiOperation(value = "角色管理查询树形表格角色数据", notes = "角色管理查询树形表格角色数据")
    @ApiImplicitParam(name = "sysRoleQueryVo", value = "角色管理查询树形表格角色数据入参", required = true, dataType = "SysRoleQueryVO", paramType = "queryRoleTreeTable")
    public ResultHelper<Page<SysRoleDTO>> queryRoleTreeTable(@RequestBody SysRoleQueryVO sysRoleQueryVo) {
        if (sysRoleQueryVo.getPageSize() == 0 || sysRoleQueryVo.getCurrentPage() == 0) {
            return CommonResult.getFaildResultData("列表接口需传入当前页数、每页限制行数参数");
        }
        if (Objects.isNull(sysRoleQueryVo.getSysApplicationId())) {
            return CommonResult.getFaildResultData("应用id为空，请确认！");
        }
        Page<SysRoleDTO> sysRolePage = sysRoleService.queryByPage(sysRoleQueryVo);
        return CommonResult.getSuccessResultData(sysRolePage);
    }


    /**
     * @Author:chenxf
     * @Description: 角色管理查询树形表格资源数据接口
     * @Date: 17:53 2020/11/28
     * @Param: [sysApplicationId]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     *
     */
    @GetMapping("/queryApplicationResourceTree")
    @ResponseBody
    @ApiOperation(value = "角色管理查询树形表格资源数据", notes = "角色管理查询树形表格资源数据")
    @ApiImplicitParam(name = "sysApplicationId", value = "角色管理查询树形表格资源数据入参", required = true, dataType = "Long", paramType = "queryApplicationResourceTree")
    public ResultHelper<List<QueryResourceDTO>> queryApplicationResourceTree(@RequestParam(value = "sysApplicationId") Long sysApplicationId) {
        return sysResourceService.queryApplicationResourceTree(sysApplicationId);
    }

    /**
     * @Author:chenxf
     * @Description: 角色管理保存角色接口
     * @Date: 18:01 2020/11/28
     * @Param: [sysRoleDTO]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     *
     */
    @PostMapping("/saveSysRole")
    @ResponseBody
    @ApiOperation(value = "角色管理保存角色", notes = "角色管理保存角色")
    @ApiImplicitParam(name = "sysRoleDTO", value = "角色管理保存角色入参", required = true, dataType = "SysRoleDTO", paramType = "saveSysRole")
    public ResultHelper<Object> saveSysRole(@RequestBody SysRoleDTO sysRoleDTO) {
        return sysRoleService.saveSysRole(sysRoleDTO);
    }

    /**
     * @Author:chenxf
     * @Description: 角色管理修改角色接口
     * @Date: 18:01 2020/11/28
     * @Param: [sysRoleDTO]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     *
     */
    @PostMapping("/updateSysRole")
    @ResponseBody
    @ApiOperation(value = "角色管理修改角色", notes = "角色管理修改角色")
    @ApiImplicitParam(name = "sysRoleDTO", value = "角色管理修改角色入参", required = true, dataType = "SysRoleDTO", paramType = "saveSysRole")
    public ResultHelper<Object> updateSysRole(@RequestBody SysRoleDTO sysRoleDTO) {
        return sysRoleService.updateSysRole(sysRoleDTO);
    }

    /**
     * @Author:chenxf
     * @Description: 权限分配角色联想控件查询接口
     * @Date: 16:03 2021/1/29
     * @Param: [sysRoleQueryVo]
     * @Return:com.share.support.result.ResultHelper
     *
     */
    @PostMapping("/querySysRoleByAppAndCompany")
    @ResponseBody
    @ApiOperation(value = "权限分配角色下拉框查询接口", notes = "权限分配角色下拉框查询接口")
    @ApiImplicitParam(name = "sysRoleQueryVo", value = "权限分配角色下拉框查询接口入参", required = true, dataType = "SysRoleQueryVO", paramType = "querySysRoleByAppAndCompany")
    public ResultHelper<List<SysRoleDTO>> querySysRoleByAppAndCompany(@RequestBody SysRoleQueryVO sysRoleQueryVo) {
        List<SysRoleDTO> sysRoleDTOList = sysRoleService.querySysRoleByAppAndCompany(sysRoleQueryVo);
        return CommonResult.getSuccessResultData(sysRoleDTOList);
    }

    /**
     * @Author: cjh
     * @Description: 权限分配角色联想控件查询接口-用户管理
     * @Date: 19:03 2021/11/30
     * @Param: [sysRoleQueryVo]
     * @Return:com.share.support.result.ResultHelper
     *
     */
    @PostMapping("/querySysRoleByAppAndCompanyByUser")
    @ResponseBody
    @ApiOperation(value = "权限分配角色下拉框查询接口-用户管理", notes = "权限分配角色下拉框查询接口-用户管理")
    @ApiImplicitParam(name = "sysRoleQueryVo", value = "权限分配角色下拉框查询接口入参", required = true, dataType = "SysRoleQueryVO", paramType = "querySysRoleByAppAndCompanyByUser")
    public ResultHelper<List<SysRoleDTO>> querySysRoleByAppAndCompanyByUser(@RequestBody SysRoleQueryVO sysRoleQueryVo) {
        List<SysRoleDTO> sysRoleDTOList = sysRoleService.querySysRoleByAppAndCompanyByUser(sysRoleQueryVo);
        return CommonResult.getSuccessResultData(sysRoleDTOList);
    }
}
