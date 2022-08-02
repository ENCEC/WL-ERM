package com.share.auth.service;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.QueryResourceDTO;
import com.share.auth.domain.SysResourceDTO;
import com.share.auth.domain.SysRoleDTO;
import com.share.auth.model.entity.SysResource;
import com.share.auth.model.vo.SysRoleQueryVO;
import com.share.auth.model.workflow.RoleVO;
import com.share.support.result.ResultHelper;

import java.util.List;

/**
 * @author chenxf
 * @date 2020-11-16 14:25
 */
public interface SysRoleService {
    /**
     * 分页查询应用的角色
     *
     * @param sysRoleQueryVo 查询vo
     * @return :com.gillion.ds.client.api.queryobject.model.Page<com.share.auth.model.entity.SysRole>
     * @Author:chenxf
     * @Description: 分页查询应用的角色
     * @Date: 14:26 2020/11/16
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
    // ResultHelper<Object> saveSysRole(SysRoleDTO sysRoleDTO);

    /**
     * 新增更新保存角色接口
     *
     * @param sysRoleDTO 保存信息
     * @return :java.util.Map<java.lang.String,java.lang.Object>
     * @Author:chenxf
     * @Description: 新增更新保存角色接口
     * @Date: 10:29 2020/11/17
     */
    //  ResultHelper<Object> updateSysRole(SysRoleDTO sysRoleDTO);

    /**
     * 权限分配角色联想控件查询接口
     *
     * @param sysRoleQueryVo : [sysRoleQueryVo]条件VO
     * @return :com.gillion.ds.client.api.queryobject.model.Page<com.share.auth.domain.SysRoleDTO>
     * @Author:chenxf
     * @Description: 权限分配角色联想控件查询接口
     * @Date: 16:29 2021/1/29
     */
    List<SysRoleDTO> querySysRoleByAppAndCompany(SysRoleQueryVO sysRoleQueryVo);

    /**
     * 权限分配角色联想控件查询接口-用户管理
     *
     * @param sysRoleQueryVo : [sysRoleQueryVo]条件VO
     * @return :com.gillion.ds.client.api.queryobject.model.Page<com.share.auth.domain.SysRoleDTO>
     * @Author: cjh
     * @Description: 权限分配角色联想控件查询接口-用户管理
     * @Date: 19:29 2021/11/30
     */
    List<SysRoleDTO> querySysRoleByAppAndCompanyByUser(SysRoleQueryVO sysRoleQueryVo);


    /**
     * 根据角色获取用户列表
     *
     * @param roleCode 角色代码
     * @return 用户列表
     */
    List<RoleVO> getRoleByRoleCode(String roleCode);

    /**
     * 获取所有未禁用角色
     *
     * @return com.share.support.result.ResultHelper<java.util.List < com.share.support.model.Role>>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-28
     */
    ResultHelper<List<SysRoleDTO>> queryAllValidRole();

    /**
     * 添加角色
     *
     * @author wzr <wzr@gillion.com.cn>
     * @date 2022-07-29
     */
    ResultHelper<Object> saveSysRole(SysRoleDTO sysRoleDTO);


    /**
     * 逻辑删除角色信息
     *
     * @param sysRoleId
     * @author wzr <wzr@gillion.com.cn>
     */
    ResultHelper<Object> deleteRole(Long sysRoleId);

    /**
     * 分页查询角色信息
     *
     * @param sysRoleDTO
     * @author wzr <wzr@gillion.com.cn>
     */

    Page<SysRoleDTO> queryRoleByPage(SysRoleDTO sysRoleDTO);

    /**
     * 根据id查出数据
     *
     * @param sysRoleId
     * @author wzr <wzr@gillion.com.cn>
     */
    List<SysRoleDTO> queryRoleById(Long sysRoleId);

    /**
     * 更新数据
     *
     * @param sysRoleDTO
     * @author wzr <wzr@gillion.com.cn>
     */
    ResultHelper<Object> updateSysRole(SysRoleDTO sysRoleDTO);

    /**
     * 菜单是否禁用状态
     *
     * @param sysRoleDTO
     * @author wzr <wzr@gillion.com.cn>
     */
    ResultHelper<Object> updateRoleStatus(SysRoleDTO sysRoleDTO);
}
