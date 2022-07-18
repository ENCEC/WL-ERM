package com.share.auth.controller.workflow;

import com.share.auth.domain.SysRoleDTO;
import com.share.auth.model.workflow.RoleVO;
import com.share.auth.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 提供给工作流的接口
 * @author wangcl
 * @date 2020/01/19
 */
@RequestMapping("/workflow")
@Api("提供给工作流的接口")
@RestController
public class WorkFlowController {
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 根据角色获取用户列表
     * @param sysRoleDTO 角色信息
     * @return 用户列表
     * @author wangcl
     */
    @PostMapping("/getRoleByRoleCode")
    @ApiOperation(value = "根据角色获取用户")
    public List<RoleVO> getRoleByRoleCode(@RequestBody SysRoleDTO sysRoleDTO){
        String roleCode = sysRoleDTO.getRoleCode();
        return sysRoleService.getRoleByRoleCode(roleCode);
    }
}
