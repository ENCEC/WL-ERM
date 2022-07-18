package com.share.auth.center.service;

import com.share.auth.center.model.entity.OauthClientDetails;
import com.share.support.model.User;

import java.util.Collection;

/**
 * @author chenxf
 * @date 2020-12-05 14:31
 */
public interface StaticResourceService {

    /**
     * 获取用户页面下无权限url
     * @Author:chenxf
     * @Description: 获取用户页面下无权限url
     * @Date: 15:55 2020/12/5
     * @param user : [user, pageUrl]
     * @param pageUrl pageUrl
     * @param oauthClientDetails    oauthClientDetails
     * @return :java.util.Collection<java.lang.String>
     *
     */
    Collection<String> getNoPermissionUrl(User user, String pageUrl, OauthClientDetails oauthClientDetails);
}
