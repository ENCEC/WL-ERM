package com.share.auth.center.controller;

import com.alibaba.nacos.common.util.UuidUtils;
import com.gillion.ec.core.utils.CookieUtils;
import com.gillion.saas.redis.SassRedisInterface;
import com.share.auth.center.constants.CodeFinal;
import com.share.auth.center.credential.CredentialProcessor;
import com.share.auth.center.model.dto.ValidateUser;
import com.share.auth.center.service.UemUserService;
import com.share.support.model.User;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;


/**
 * @Author:chenxf
 * @Description: 登录控制器
 * @Date: 17:41 2020/11/28
 * @Param:
 * @Return:
 */
@Api("登录登出功能相关控制器")
@Slf4j
@Controller
public class LoginController {

    @Autowired
    private UemUserService uemUserService;
    @Autowired
    private CredentialProcessor credentialProcessor;

    @Autowired
    private SassRedisInterface redisInterface;

    /**忘记密码键**/
    public static final String FORGET_KEY = "forget_random";

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * @Author:chenxf
     * @Description: 校验用户是否可以登录
     * @Date: 16:42 2020/11/17
     * @Param: [account, password, clientId, request]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     */
    @ApiOperation("校验用户能否登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "登录账号", required = true, dataType = "String", paramType = "validateUser"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "validateUser"),
            @ApiImplicitParam(name = "clientId", value = "客户端clientId", required = true, dataType = "String", paramType = "validateUser"),
            @ApiImplicitParam(name = "checkMoveId", value = "图片滑动解锁移动redis的key", required = true, dataType = "String", paramType = "validateUser"),
            @ApiImplicitParam(name = "xWidth", value = "图片滑动长度", required = true, dataType = "Double", paramType = "validateUser")
    })
    @PostMapping("/validateUser")
    @ResponseBody
    public ResultHelper<Object> validateUser(@RequestBody ValidateUser validateUser,
                                             HttpServletRequest request, HttpServletResponse response) {
        if (Objects.isNull(validateUser)) {
            return CommonResult.getFaildResultData("参数错误！");
        }
        return uemUserService.validateUser(validateUser.getAccount(), validateUser.getPassword(),
                validateUser.getClientId(), validateUser.getCheckMoveId(), Double.valueOf(validateUser.getxWidth()), request, response);
    }


    /**
     * @Author:chenxf
     * @Description: 校验用户是否可以登录（数字验证码）
     * @Date: 16:42 2020/11/17
     * @Param: [account, password, clientId, request]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     */
    @ApiOperation("校验用户能否登录接口（验证码）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "登录账号", required = true, dataType = "String", paramType = "validateUser"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "validateUser"),
            @ApiImplicitParam(name = "clientId", value = "客户端clientId", required = true, dataType = "String", paramType = "validateUser"),
            @ApiImplicitParam(name = "checkDigitalId", value = "数字验证码redis的key", required = true, dataType = "String", paramType = "validateUser"),
            @ApiImplicitParam(name = "verifyCode", value = "数字验证码", required = true, dataType = "String", paramType = "validateUser")
    })
    @GetMapping("/validateUserByDigitalCode")
    @ResponseBody
    public ResultHelper<Object> validateUserByDigitalCode(@RequestParam(name = "account") String account,
                                                          @RequestParam(name = "password") String password,
                                                          @RequestParam(name = "clientId") String clientId,
                                                          @RequestParam(name = "checkDigitalId") String checkDigitalId,
                                                          @RequestParam(name = "verifyCode") String verifyCode,
                                                          HttpServletRequest request, HttpServletResponse response) {
        return uemUserService.validateUserByDigitalCode(account, password, clientId, checkDigitalId, verifyCode, request, response);
    }


    /**
     * @param code         oauth2授权码模式授权码code
     * @param env          环境地址
     * @param clientId     应用clientId
     * @param clientSecret 应用密钥，为空时使用默认密钥
     * @param request      请求
     * @param response     响应
     * @return 请求结果
     * @auth:chenxf
     * @description: 获取其它环境token接口
     * @date: 15:37 2021/1/18
     * @Param: [code, env, request, response]
     * @Return:com.share.support.result.ResultHelper
     */
    @ApiOperation("获取其它环境token接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "oauth2授权码模式授权码code", required = true, dataType = "String", paramType = "getToken"),
            @ApiImplicitParam(name = "env", value = "环境地址", required = true, dataType = "String", paramType = "getToken"),
            @ApiImplicitParam(name = "clientId", value = "应用clientId", required = true, dataType = "String", paramType = "getToken"),
            @ApiImplicitParam(name = "clientSecret", value = "应用密钥", required = true, dataType = "String", paramType = "getToken")
    })
    @GetMapping("/getToken")
    @ResponseBody
    public ResultHelper<Object> getToken(@RequestParam(name = "code") String code, @RequestParam(name = "env") String env, String clientId, String clientSecret, HttpServletRequest request, HttpServletResponse response) {
        return uemUserService.getToken(code, env, clientId, clientSecret, request, response);
    }

    /**
     * @Author:chenxf
     * @Description: 单点登录请求授权转发接口（多这一步转发是为了保存clientId）
     * @Date: 15:38 2021/1/18
     * @Param: [client_id, redirect_uri, response_type, state, response]
     * @Return:java.lang.String
     */
    @ApiOperation("单点登录请求授权转发接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "client_id", value = "客户端clientId", required = true, dataType = "String", paramType = "authentication"),
            @ApiImplicitParam(name = "redirect_uri", value = "客户端redirectUri", required = true, dataType = "String", paramType = "authentication"),
            @ApiImplicitParam(name = "response_type", value = "请求授权方式，固定为code", required = true, dataType = "authentication", paramType = "authentication"),
            @ApiImplicitParam(name = "state", value = "state参数", required = true, dataType = "String", paramType = "authentication")
    })
    @GetMapping(value = "/oauth2/auth/authentication")
    public String authentication(@RequestParam(value = "client_id") String clientId,
                                 @RequestParam(value = "redirect_uri") String redirectUri,
                                 @RequestParam(value = "response_type") String responseType,
                                 @RequestParam(value = "state") String state,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        try {
            return uemUserService.authentication(clientId, redirectUri, responseType, state, request, response);
        } catch (Exception e) {
            throw new ApplicationContextException(e.getMessage());
        }
    }

    /**
     * 获取用户信息
     *
     * @return
     * @author wangcl
     */
    @ApiOperation("获取用户信息接口")
    @ResponseBody
    @GetMapping(value = "/getUserInfo")
    public User getUserInfo(HttpServletResponse response) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(servletRequestAttributes)) {
            return null;
        }
        HttpServletRequest request = servletRequestAttributes.getRequest();
        User user = credentialProcessor.parseCredential(request);
//        if (Objects.nonNull(user) && Objects.nonNull(user.getExpireTime())) {
//            long expireTime = user.getExpireTime();
//            long currentTime = System.currentTimeMillis();
//            if (expireTime - currentTime <= CodeFinal.MILLIS_MINUTE_TEN) {
//                Cookie jwt = CookieUtils.getCookie(CodeFinal.ACCESS_TOKEN_NAME);
//                credentialProcessor.refreshCredential(response, jwt.getValue());
//            }
//        }
        if (Objects.nonNull(user)) {
            user = uemUserService.getUserInfo(user.getUemUserId(), user.getClientId());
            if (Objects.nonNull(user.getExpireTime())) {
                long expireTime = user.getExpireTime();
                long currentTime = System.currentTimeMillis();
                if (expireTime - currentTime <= CodeFinal.MILLIS_MINUTE_TEN) {
                    Cookie jwt = CookieUtils.getCookie(CodeFinal.ACCESS_TOKEN_NAME);
                    credentialProcessor.refreshCredential(response, jwt.getValue());
                }
            }
        }
        return user;
    }

    /**
     * 生成jwt
     *
     * @return
     * @author wangcl
     */
    @ApiOperation("生成jwt")
    @ResponseBody
    @RequestMapping(value = "/createCredential", method = RequestMethod.POST)
    public ResultHelper<String> createCredential(@RequestBody User userInfo) {
        String credential = credentialProcessor.createCredential(userInfo);
        if (Objects.nonNull(credential)) {
            return CommonResult.getSuccessResultData(credential);
        } else {
            return CommonResult.getFaildResultData("生成jwt失败！");
        }
    }

    /**
     * 生成jwt的Cookie
     *
     * @param userInfo 用户信息
     * @return jwt的Cookie列表JSON格式字符串
     */
    @ApiOperation("生成jwt的Cookie")
    @ResponseBody
    @RequestMapping(value = "/createCredentialCookie", method = RequestMethod.POST)
    public String createCredentialCookie(@RequestBody User userInfo) {
        return uemUserService.createCredentialCookie(userInfo);
    }

    /**
     * 解析jwt
     *
     * @return
     * @author wangcl
     */
    @ApiOperation("解析jwt")
    @ResponseBody
    @RequestMapping(value = "/parseCredential", method = RequestMethod.POST)
    public User parseCredential(@RequestBody String parseCredential) {
        return credentialProcessor.parseCredential(parseCredential);
    }


    /**
     * @Author:chenxf
     * @Description: 校验滑动结算是否成功接口
     * @Date: 15:02 2021/1/14
     * @Param: [checkMoveId, xWidth]
     * @Return:java.lang.Boolean
     */
    @ApiOperation("校验滑动结算是否成功接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkMoveId", value = "滑动解锁图片redis的Key", required = true, dataType = "String", paramType = "validateUnlock"),
            @ApiImplicitParam(name = "xWidth", value = "滑动距离", required = true, dataType = "Double", paramType = "validateUnlock")
    })
    @GetMapping("/validateUnlock")
    @ResponseBody
    public ResultHelper validateUnlock(@RequestParam(name = "checkMoveId") String checkMoveId, @RequestParam(name = "xWidth") Double xWidth) {
        boolean b = uemUserService.validateUnlock(checkMoveId, xWidth);
        if(b){
            String uid = UuidUtils.generateUuid();
            redisInterface.set(FORGET_KEY, uid,  60);
            return CommonResult.getSuccessResultData(uid);
        }else {
            return CommonResult.getFaildResultData();
        }

    }

    /**
     * @Author:chenxf
     * @Description: 校验验证码验证是否成功接口
     * @Date: 15:02 2021/1/14
     * @Param: [checkDigitalId, verifyCode]
     * @Return:java.lang.Boolean
     */
    @ApiOperation("校验滑动结算是否成功接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checkDigitalId", value = "验证码在redis中的Key", required = true, dataType = "String", paramType = "validateDigitalCode"),
            @ApiImplicitParam(name = "verifyCode", value = "验证码", required = true, dataType = "String", paramType = "validateDigitalCode")
    })
    @GetMapping("/validateDigitalCode")
    @ResponseBody
    public Boolean validateDigitalCode(@RequestParam(name = "checkDigitalId") String checkDigitalId, @RequestParam(name = "verifyCode") String verifyCode) {
        return uemUserService.validateDigitalCode(checkDigitalId, verifyCode);
    }


}
