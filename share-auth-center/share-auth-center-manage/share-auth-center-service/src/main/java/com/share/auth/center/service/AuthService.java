package com.share.auth.center.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chenxf
 * @date 2021-01-21 17:06
 */
public interface AuthService {
    /**
     * 统一认证初始化url
     * @Author:chenxf
     * @Description: 统一认证初始化url
     * @Date: 9:35 2021/1/26
     * @param  applicationCode 应用编码
     * @return eturn:java.lang.String
     *
     */
    String authorize(String applicationCode);

    /**
     * 统一认证登录
     * @Author:chenxf
     * @Description: 统一认证登录
     * @Date: 9:36 2021/1/26
     * @param code 授权码
     * @param state 状态值
     * @param applicationCode 应用编码
     * @param request 请求
     * @param response 响应
     * @return java.lang.String
     *
     */
    String login(String code, String state, String applicationCode, HttpServletRequest request, HttpServletResponse response);
}
