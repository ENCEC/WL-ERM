package com.share.auth.service;

import com.gillion.ds.client.api.queryobject.model.Page;
import com.share.auth.domain.SysDictCodeVO;
import com.share.auth.domain.SysResourceDTO;
import com.share.auth.model.vo.SysResourceVO;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @description应用接口
 * @Author: zhuyp
 * @Date: 2021/12/7 17:09
 */
public interface SysResourceNewService {

    /**
     * @Author zhuyp
     * @Description 查询分页信息
     * @Date  2021/12/7 17:35
     * @Param SysResourceDTO sysResource
     * @return Page<SysResourceVO>
     **/
    Page<SysResourceVO> selectSysResourcePage(SysResourceDTO sysResource);

    /**
     * 通过id查询
     * @param sysResourceId
     * @return
     */
    SysResourceVO selectSysResourceById(Long sysResourceId);

    /**
     * 通过resourcePic搜索子页面/菜单/按钮
     * @param resourcePid 父页面/菜单id
     * @return 子页面/菜单/按钮列表
     */
    List<SysResourceVO> selectSysResourceByResourcePid(Long resourcePid);

    /**
     * 保存
     * @param sysResource
     * @return int
     */
    Integer saveOrUpdate(SysResourceDTO sysResource);
    /**
     * 修改
     * @param sysResource
     * @return int
     */
    Integer updateResource(SysResourceDTO sysResource);
    /** 
     * @Author zhuyp
     * @Description 查询父级菜单作为下拉框
     * @Date  2021/12/7 17:12
     * @Param
     * @return   Map<String, List<SysDictCodeVO>>
     **/
     Map<String, List<SysDictCodeVO>> selectSysParentsResourceList();
    /**
     * @Author zhuyp
     * @Description 查询关联应用作为下拉框
     * @Date  2021/12/7 17:12
     * @Param
     * @return  Map<String, List<SysDictCodeVO>>
     **/
     Map<String, List<SysDictCodeVO>> selectSysApplicationList();
     /**
     * @Author zhuyp
     * @Description 查询菜单作为下拉框
     * @Date  2021/12/7 17:12
     * @Param   sysResourceId
     * @return Map<String, List<SysDictCodeVO>>
     **/
     Map<String, SysDictCodeVO> selectSysResourceList(Long sysResourceId);

    /**
     * 菜单和应用唯一性校验
     * @return Boolean
     */
     Boolean uniqueValidate(SysResourceDTO sysResource);

    /**
     * 启用状态下的父级菜单如果其下子菜单存在启用状态的子菜单，不能禁用
     * @param resourceId
     * @return Boolean
     */
     Boolean forbiddenValidate(Long resourceId);
    /**
     * 禁用状态下的子菜单如果其父菜单为禁用状态，不能启用
     * @param resourceId
     * @return Boolean
     */
     Boolean openValidate(Long resourceId);

    /**
     * 父级菜单下是否存在子菜单，有-》TRUE
     * @param pid
     * @return Boolean
     */
     Boolean existSonResource(Long pid);
}
