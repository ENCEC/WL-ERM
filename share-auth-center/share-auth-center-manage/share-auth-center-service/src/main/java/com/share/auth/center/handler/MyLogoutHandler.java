package com.share.auth.center.handler;

import com.share.auth.center.credential.CredentialProcessor;
import com.share.support.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * @Author:chenxf
 * @Description: 自定义实现退出登录
 * @Date: 9:55 2020/10/19
 * @Param:
 * @Return:
 *
 */
@Component
@Slf4j
public class MyLogoutHandler implements LogoutSuccessHandler {


    @Autowired
    private CredentialProcessor credentialProcessor;

    /**
     * session的用户信息
     */
    private static final String USER = "user";
    /**
     * @Author:chenxf
     * @Description: oauth2提供的退出登录成功扩展扩展方式，在此处销毁eds的access_token
     * @Date: 15:58 2021/1/18
     * @Param: [httpServletRequest, httpServletResponse, authentication]
     * @Return:void
     *
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException{
        String accessToken = CookieUtil.getCookieByName(httpServletRequest, "access_token");
        credentialProcessor.destroy(httpServletResponse, accessToken);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter out = httpServletResponse.getWriter();
        out.write("{\"isLogout\":true}");
        out.flush();
        out.close();
    }
}
