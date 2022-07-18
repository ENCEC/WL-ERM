package com.share.auth.center.service;



import com.gillion.utils.CommonResult;

import java.util.List;

/**
 * Created by huangwb on 2018/7/10
 * @author huangwb
 */
public interface ResourcePermissionService {

    /**
     * 资源权限校验
     * @param params params
     * @return CommonResult
     */
    CommonResult validate(String[] params);

    /**
     * 根据roleIds刷新资源权限缓存
     * @param roleIds roleIds
     */
    void refreshResourceUrlPermissionByRoleIds(List<String> roleIds);

    /**
     * 根据appCode/roleIds刷新资源权限缓存
     * @param appCode appCode
     * @param roleIds roleIds
     */
    void refreshResourceUrlPermissionByAppCodeAndRoleIds(String appCode, List<String> roleIds);

    /**
     * 判断urlPath 是不是在ignore urlPattern list中
     * @param path path
     * @return boolean
     */
    boolean isInIgnorePathPattern(String path);


    /**
     * 判断urlPath 是不是允许 国家综合交通运输信息平台用户 访问
     * @param path path
     * @return boolean
     */
    boolean isImtpUserAllowPath(String path);
}
