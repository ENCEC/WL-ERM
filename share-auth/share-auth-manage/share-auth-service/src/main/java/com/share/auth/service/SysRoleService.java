package com.share.auth.service;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.SysRoleDTO;
import com.share.auth.model.vo.SysRoleQueryVO;
import com.share.auth.model.workflow.RoleVO;
import com.share.support.result.ResultHelper;

import java.util.List;
import java.util.Map;

/**
 * @author chenxf
 * @date 2020-11-16 14:25
 */
public interface SysRoleService {
    /**
     * 分页查询应用的角色
     * @Author:chenxf
     * @Description: 分页查询应用的角色
     * @Date: 14:26 2020/11/16
     * @param sysRoleQueryVo 查询vo
     * @return :com.gillion.ds.client.api.queryobject.model.Page<com.share.auth.model.entity.SysRole>
     *
     */
    Page<SysRoleDTO> queryByPage(SysRoleQueryVO sysRoleQueryVo);

    /**
     * 新增更新保存角色接口
     *
     * @param sysRoleDTO 保存信息
     * @return :java.util.Map<java.lang.String,java.lang.Object>
     * @Author:chenxf
     * @Description: 新增更新保存角色接口
     * @Date: 10:29 2020/11/17
     */
    ResultHelper<Object> saveSysRole(SysRoleDTO sysRoleDTO);

    /**
     * 新增更新保存角色接口
     *
     * @param sysRoleDTO 保存信息
     * @return :java.util.Map<java.lang.String,java.lang.Object>
     * @Author:chenxf
     * @Description: 新增更新保存角色接口
     * @Date: 10:29 2020/11/17
     */
    ResultHelper<Object> updateSysRole(SysRoleDTO sysRoleDTO);

    /**
     * 权限分配角色联想控件查询接口
     * @Author:chenxf
     * @Description: 权限分配角色联想控件查询接口
     * @Date: 16:29 2021/1/29
     * @param sysRoleQueryVo : [sysRoleQueryVo]条件VO
     * @return :com.gillion.ds.client.api.queryobject.model.Page<com.share.auth.domain.SysRoleDTO>
     *
     */
    List<SysRoleDTO> querySysRoleByAppAndCompany(SysRoleQueryVO sysRoleQueryVo);

    /**
     * 权限分配角色联想控件查询接口-用户管理
     * @Author: cjh
     * @Description: 权限分配角色联想控件查询接口-用户管理
     * @Date: 19:29 2021/11/30
     * @param sysRoleQueryVo : [sysRoleQueryVo]条件VO
     * @return :com.gillion.ds.client.api.queryobject.model.Page<com.share.auth.domain.SysRoleDTO>
     *
     */
    List<SysRoleDTO> querySysRoleByAppAndCompanyByUser(SysRoleQueryVO sysRoleQueryVo);


    /**
     * 根据角色获取用户列表
     * @param roleCode 角色代码
     * @return 用户列表
     */
    List<RoleVO> getRoleByRoleCode(String roleCode);
}
