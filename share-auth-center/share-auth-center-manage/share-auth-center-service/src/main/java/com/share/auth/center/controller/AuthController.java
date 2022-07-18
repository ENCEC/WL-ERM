package com.share.auth.center.controller;

import com.share.auth.center.service.AuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chenxf
 * @date 2021-01-21 17:05
 */
@RestController
@RequestMapping("/oauthSSO")
public class AuthController {

    @Autowired
    private AuthService authService;


    /**
     * redirect 到code，登录，获取用户信息
     * @param applicationCode 跳转应用的应用编码
     * @param response 跳转
     * @throws IOException 异常
     */
    @RequestMapping(value = "/render", method = RequestMethod.GET)
    public void renderAuth(String applicationCode, HttpServletResponse response) throws IOException {
        response.sendRedirect(authService.authorize(applicationCode));
    }

    /**
     * 授权码回调接口
     * @param code 授权码
     * @param state 状态值
     * @param applicationCode 应用编码
     * @param request 请求
     * @param response 响应
     * @throws IOException 异常
     */
    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public void login(String code, String state, String applicationCode, HttpServletRequest request, HttpServletResponse response) throws IOException  {
        String url = authService.login(code,state,applicationCode,request,response);
        if(StringUtils.isNotEmpty(url)){
            response.sendRedirect(url);
        }else {
            response.sendRedirect("/404");
        }
    }
}
