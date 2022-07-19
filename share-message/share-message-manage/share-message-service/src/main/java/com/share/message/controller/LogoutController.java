package com.share.message.controller;

import com.share.message.user.DefaultUserService;
import com.share.support.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author wangcl
 * @date 20201026
 * @description 退出接口
 */
@Controller
public class LogoutController {
    @Autowired
    private DefaultUserService loginUserInfoService;

    @Value("${sso.redirectUrl}")
    private String redirectUrls;


    @Autowired
    private RestTemplate restTemplate;

    /**
     * 登出接口
     * @param request
     * @author wangcl
     */
    @RequestMapping(value = "/ssoLogout",method = GET)
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        loginUserInfoService.logout(request);
        String state = CookieUtil.getCookieByName(request,"state");
        if(StringUtils.isNotEmpty(state) && !request.getRemoteAddr().contains(state)){
            HttpHeaders requestHeaders = new HttpHeaders();
            // 调用认证权限接口，需携带cookie
            requestHeaders.put("Cookie", loginUserInfoService.getCookieList(request));
            HttpEntity<String> requestEntity = new HttpEntity<>(null, requestHeaders);
            // 调用退出接口
            restTemplate.exchange(state + "/logout", HttpMethod.GET, requestEntity, Object.class);
        }
        String redirectUrl = redirectUrls + "/logout";
        response.setStatus(302);
        response.sendRedirect(redirectUrl);
    }
}
