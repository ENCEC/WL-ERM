package com.share.auth.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
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

//    @Value("#{'${sso.allow.all}'.split(',')}")
//    private List<Long> allowAllClient;
//
//    @Value("#{'${sso.allow.admin}'.split(',')}")
//    private List<Long> allowAdminClient;
//
//    @Value("#{'${sso.allow.noRole}'.split(',')}")
//    private List<Long> allowNoRoleClient;
//
//    public static List<Long> ALLOW_ALL_CLIENT_ID = new ArrayList<>();
//
//    public static List<Long> ALLOW_ADMIN_CLIENT_ID = new ArrayList<>();
//
//    public static List<Long> ALLOW_NO_ROLE_CLIENT_ID = new ArrayList<>();
//
//    @PostConstruct
//    public void getAllowClient(){
//        ALLOW_ALL_CLIENT_ID = this.allowAllClient;
//        ALLOW_ADMIN_CLIENT_ID = this.allowAdminClient;
//        ALLOW_NO_ROLE_CLIENT_ID = this.allowNoRoleClient;
//    }
}
