package com.share.auth.controller;

import com.share.auth.constants.GlobalConstant;
import com.share.auth.user.DefaultUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author wangcl
 * @date 20201026
 * @description 退出接口
 */
@Api("退出登录控制器")
@Controller
public class LogoutController {

    @Autowired
    private DefaultUserService loginUserInfoService;

    @Value("${sso.redirectUrl}")
    private String redirectUrls;

    /**
     * 登出接口
     * @param request
     */
    @ApiOperation(value = "登出接口", notes = "登出接口")
    @PostMapping("/ssoLogout")
    public String logout(HttpServletRequest request){
        loginUserInfoService.logout(request);
//        String redirectUrl = redirectUrls + "/logout";
        //解决多前端访问同一后台，返回不同路径的问题
        String redirectUrl = "/share-auth-center/logout";
        return "redirect:" + redirectUrl;
    }
}
