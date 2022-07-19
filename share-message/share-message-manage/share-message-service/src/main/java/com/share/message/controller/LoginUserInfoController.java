package com.share.message.controller;

import com.share.message.service.LoginUserInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author tujx
 * @description 当前登录信息
 * @date 2020/11/26
 */
@RestController
@RequestMapping("/message")
public class LoginUserInfoController {

    @Autowired
    private LoginUserInfoService loginUserInfoService;


    /**
     * 获取当前登录用户信息
     *
     * @param
     * @return Map
     * @throws
     * @author tujx
     */
    @GetMapping("/getCurrentLoginUser")
    @ApiOperation(value = "获取当前登录用户信息", notes = "获取当前登录用户信息")
    public Map<String,Object> getCurrentLoginUser(){
        return loginUserInfoService.getCurrentLoginUser();
    }
}
