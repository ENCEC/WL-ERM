package com.share.auth.service;

import com.share.auth.domain.QueryResourceDTO;
import com.share.auth.domain.SysResourceDTO;
import com.share.auth.domain.SysResourceQueryVO;
import com.share.auth.domain.SysRoleDTO;
import com.share.support.result.ResultHelper;

import java.util.List;
import java.util.Map;

/**
 * @author chenxf
 * @date 2020-10-26 16:45
 */
public interface SysResourceService {
    /**
     * 根据应用id，用户id获取资源集合
     *
     * @param sysResourceQueryVO 查询VO
     * @return :java.util.Map<java.lang.String,java.lang.Object>
     * @Author:chenxf
     * @Description: 根据应用id，用户id获取资源集合
     * @Date: 15:48 2020/11/16
     */
    ResultHelper<List<QueryResourceDTO>> queryResource(SysResourceQueryVO sysResourceQueryVO);

    /**
     * 根据应用id查询资源集合层级数据
     *
     * @param sysApplicationId: [sysApplicationId]应用id
     * @return :java.util.Map<java.lang.String,java.lang.Object>
     * @Author:chenxf
     * @Description: 根据应用id查询资源集合层级数据
     * @Date: 16:46 2020/11/16
     */
    ResultHelper<List<QueryResourceDTO>> queryApplicationResourceTree(Long sysApplicationId);

    /**
     * 根据应用id，用户id获取所有系统资源集合
     *
     * @param sysResourceQueryVO 查询VO
     * @return :java.util.Map<java.lang.String,java.lang.Object>
     * @Author:chenxf
     * @Description: 根据应用id，用户id获取资源集合
     * @Date: 15:48 2020/11/16
     */
    ResultHelper<Map<String , List<QueryResourceDTO>>> queryResourceAllSystem(SysResourceQueryVO sysResourceQueryVO);

    /**
     * 根据应用ID、用户ID和页面URL获取页面的按钮列表
     * @param sysResourceQueryVO -
     * @return 页面中允许的按钮列表
     */
    ResultHelper<List<QueryResourceDTO>> queryButtonInPage(SysResourceQueryVO sysResourceQueryVO);

    /**
     * 根据角色ID获取资源列表
     *
     * @param sysRoleDTO 角色ID
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-28
     */
    ResultHelper<List<SysResourceDTO>> queryResourceByRole(SysRoleDTO sysRoleDTO);

    /**
     * 获取所有未禁用角色
     * @return com.share.support.result.ResultHelper<java.util.List<com.share.auth.domain.SysResourceDTO>>
     * @author xuzt <xuzt@gillion.com.cn>
     * @date 2022-07-28
     */
    ResultHelper<List<SysResourceDTO>> queryAllValidResource();
}
