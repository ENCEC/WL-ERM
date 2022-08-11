package com.gillion.configuration;

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
    /**  白名单url集合*/
    private List<String> ignorePathPatterns;
}
