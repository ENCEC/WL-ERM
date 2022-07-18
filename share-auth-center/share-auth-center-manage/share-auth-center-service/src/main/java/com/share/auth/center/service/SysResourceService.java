package com.share.auth.center.service;

import java.util.List;
import java.util.Set;

/**
 * @author chenxf
 * @date 2020-12-04 15:26
 */
public interface SysResourceService {
    /**
     * 获取所有接口权限url
     * @Author:chenxf
     * @Description: 获取所有接口权限url
     * @Date: 14:30 2020/12/18
     * @Param: []
     * @return :java.util.Set<java.lang.String>
     *
     */
    Set<String> findAllByIsValid();
    /**
     *  获取角色的所有按钮权限url
     * @Author:chenxf
     * @Description: 获取角色的所有按钮权限url
     * @Date: 14:16 2020/12/18
     * @param roleId 角色id
     * @param resourceType 资源类型
     * @return :java.util.Set<java.lang.String>
     *
     */
    Set<String> getResourcesByRoleId(String roleId, int resourceType);

    /**
     * 根据应用Id和角色Id获取权限列表
     * @Author:chenxf
     * @Description: 根据应用Id和角色Id获取权限列表
     * @Date: 14:47 2020/12/18
     * @param key  key
     * @param value  value
     * @return :java.util.Set<java.lang.String>
     *
     */
    Set<String> getResourcesByClientIdAndRoleId(String key, String value);


    /**
     * 获取页面下的所有按钮资源集合
     * @Author:chenxf
     * @Description: 获取页面下的所有按钮资源集合
     * @Date: 13:56 2020/12/18
     * @param key : [key]
     * @return :java.util.Set<java.lang.String>
     *
     */
    Set<String> loadAllResourcesByPageUrl(String key);

    /**
     * 根据应用Id和页面url获取该页面下所有资源
     * @Author:chenxf
     * @Description: 根据应用Id和页面url获取该页面下所有资源
     * @Date: 13:59 2020/12/18
     * @param sysApplicationId 应用id
     * @param pageUrl 页面地址
     * @return :java.util.Set<java.lang.String>
     *
     */
    Set<String> loadAllResourcesByAppCodeAndPageUrl(String sysApplicationId, String pageUrl);

    /**
     * 根据接口url获取资源所属应用Id
     * @Author:chenxf
     * @Description: 根据接口url获取资源所属应用Id
     * @Date: 14:39 2020/12/18
     * @param path 接口url
     * @return :java.lang.Long
     *
     */
    List<Long> findByPath(String path);
}
