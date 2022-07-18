package com.share.auth.center.constants;

import lombok.Data;

import java.util.List;

/**
 * @Author:chenxf
 * @Description: 用户登录/接口权限相关动态配置
 * @Date: 15:52 2021/1/18
 * @Param: 
 * @Return:
 *
 */
@Data
public class AuthenticationConfig {
    /**匿名用户Id*/
    private String anonymousRoleId = "ba98c88eb2f14632948d189cd41e4518";

    private boolean captchaEnabled = false;
    /** 是否所有资源都需要登录权限*/
    private boolean everyResourceRequireLogin = false;
    /**  当资源未配置时是否允许访问*/
    private boolean failedWhenResourceNotFound = false;
    /**  国家综合交通运输信息平台用户允许访问账号系统的url*/
    private List<String> imtpUserAllowPath;
    /**  白名单url集合*/
    private List<String> ignorePathPatterns;
}
