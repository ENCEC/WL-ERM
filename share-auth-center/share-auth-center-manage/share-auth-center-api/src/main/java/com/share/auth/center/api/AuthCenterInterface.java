package com.share.auth.center.api;

import com.gillion.utils.CommonResult;
import com.share.support.model.User;
import com.share.support.result.ResultHelper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 认证中心feign接口
 * @author wangcl
 * @date 2020/01/13
 */
@FeignClient(value = "${application.name.center}")
public interface AuthCenterInterface {

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping(value = "/getUserInfo")
    User getUserInfo();

    /**
     * 框架自带的登出接口
     */
    @GetMapping(value = "/logout")
    void logout();

    /**
     * 创建jwt
     * @param userInfo 用户信息
     * @return jwt
     * @author wangcl
     * @date 2021/04/01
     */
    @RequestMapping(value = "/createCredential")
    ResultHelper<String> createCredential(@RequestBody User userInfo);

    /**
     * @Author cec
     * @Description 解析凭证
     * @Date  2021/11/30 10:47
     * @Param parseCredential 入参请求
     * @return
     **/
    @RequestMapping(value = "/parseCredential")
    User parseCredential(String parseCredential);


    /**
     * 生成jwt的Cookie
     * @param userInfo 用户信息
     * @return jwt的Cookie列表JSON格式字符串
     */
    @RequestMapping(value = "/createCredentialCookie")
    String createCredentialCookie(@RequestBody User userInfo);

    /**
     * 资源权限校验
     * @param params
     * @return
     */
    @RequestMapping(value = "/validate")
    CommonResult validateUrl(@RequestBody String[] params);

}
