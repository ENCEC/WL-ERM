package com.share.auth.center.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author:chenxf
 * @Description: 按校验分类获取客户端id
 * @Date: 14:56 2021/1/8
 * @Param:
 * @Return:
 *
 */
@Component
public class OauthClientUtils {

    private static List<Long> allowAllClientId;

    private static List<Long> allowAdminClientId;

    private static List<Long> allowNoRoleClientId;

    OauthClientUtils(){

    }

    public static List<Long> getAllowAllClientId(){
        return OauthClientUtils.allowAllClientId;
    }

    @Value("#{'${sso.allow.all}'.split(',')}")
    public void setAllowAllClientId(List<Long> allowAllClientId) {
        setClientId(allowAllClientId);
    }
    private static void setClientId(List<Long> allowAllClientId){
        OauthClientUtils.allowAllClientId = allowAllClientId;
    }

    public static List<Long> getAllowAdminClientId() {
        return allowAdminClientId;
    }

    @Value("#{'${sso.allow.admin}'.split(',')}")
    public void setAllowAdminClientId(List<Long> allowAdminClientId) {
        setAdminClientId(allowAdminClientId);
    }
    private static void setAdminClientId(List<Long> allowAdminClientId){
        OauthClientUtils.allowAdminClientId = allowAdminClientId;
    }

    public static List<Long> getAllowNoRoleClientId() {
        return allowNoRoleClientId;
    }

    @Value("#{'${sso.allow.noRole}'.split(',')}")
    public void setAllowNoRoleClientId(List<Long> allowNoRoleClientId) {
        setNoRoleClientId(allowNoRoleClientId);
    }
    private static void setNoRoleClientId(List<Long> allowNoRoleClientId){
        OauthClientUtils.allowNoRoleClientId = allowNoRoleClientId;
    }

}
