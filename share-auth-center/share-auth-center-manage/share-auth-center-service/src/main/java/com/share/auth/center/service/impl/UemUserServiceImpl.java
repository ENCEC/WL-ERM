package com.share.auth.center.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.gillion.ds.entity.base.RowStatusConstants;
import com.gillion.ec.core.utils.CookieUtils;
import com.gillion.ec.snowflakeid.SnowFlakeGenerator;
import com.gillion.exception.BusinessRuntimeException;
import com.gillion.saas.redis.SassRedisInterface;
import com.google.common.collect.Lists;
import com.share.auth.center.constants.CodeFinal;
import com.share.auth.center.constants.EsConstant;
import com.share.auth.center.constants.RedisMqConstant;
import com.share.auth.center.credential.CredentialProcessor;
import com.share.auth.center.model.dto.TokenModel;
import com.share.auth.center.model.dto.UserDTO;
import com.share.auth.center.model.entity.*;
import com.share.auth.center.model.querymodels.*;
import com.share.auth.center.queue.dto.DelayTaskDTO;
import com.share.auth.center.queue.listener.UemUserUnlockListener;
import com.share.auth.center.queue.service.RedisDelayedQueue;
import com.share.auth.center.service.UemUserService;
import com.share.auth.center.util.EntityUtils;
import com.share.auth.center.util.HttpsClientUtil;
import com.share.auth.center.util.RequestUtil;
import com.share.support.model.User;
import com.share.support.result.CommonResult;
import com.share.support.result.ResultHelper;
import com.share.support.util.AES128Util;
import com.share.support.util.CookieUtil;
import com.share.support.util.MD5EnCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author chenxf
 * @date 2020-10-10 10:16
 */
@Service
@Slf4j
public class UemUserServiceImpl implements UemUserService {

    /**
     * 公共服务相关配置
     */
    @Value("${sso.portal.client_id}")
    private String ssoPortalClientId;
    @Value("${sso.portal.client_secret}")
    private String ssoPortalClientSecret;
    @Value("${sso.portal.redirect_uri}")
    private String ssoPortalRedirectUri;
    @Value("${authentication.cookieExpireSeconds:28800}")
    private int cookieExpireSeconds;
    @Value("${authentication.jwt.jwtName}")
    private String jwtName;
    @Value("${aes_secret_key}")
    private String aesSecretKey;
    @Autowired
    private SnowFlakeGenerator snowFlakeGenerator;

    @Value("${web_domain}")
    private String webDomain;

    @Autowired
    private CredentialProcessor credentialProcessor;

    @Autowired
    private SassRedisInterface redisInterface;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisDelayedQueue redisDelayedQueue;

    /**
     * 账号被禁用提示
     */
    private static final String ENABLED_MESSAGE = "该账号已禁用，不能登录";
    /**
     * 授权失败提示
     */
    private static final String GRANT_FAIL_MESSAGE = "获取授权失败，请重试";

    /**
     * 查询用户
     * @param username 用户名、邮箱、手机号
     * @return com.share.auth.center.model.entity.UemUser
     * @date 2022-08-02
     */
    private UemUser getUemUser(String username) {
        return QUemUser.uemUser
                .selectOne()
                .where(QUemUser.account.eq$(username))
                .execute();
    }

    /**
     * @Author:chenxf
     * @Description: 根据用户名查询登录用户信息，用户可以是普通用户，也可以是管理员
     * @Date: 11:48 2020/10/21
     * @Param: [username]
     * @Return:com.share.auth.domain.UserDTO
     */
    @Override
    public UserDTO getByUsername(String username) {
        UserDTO userDTO = new UserDTO();
        // 查询用户是否存在
        UemUser uemUser = getUemUser(username);
        // 既不是普通用户，也不是管理员
        if (Objects.isNull(uemUser)) {
            log.error("用户{}不存在", username);
            throw new UsernameNotFoundException("用户名不存在 username => " + username);
        }
        // 校验用户锁定状态
        if (uemUser.getIsLocked()) {
            log.info("登录username：{}，账号已锁定", username);
            throw new LockedException(CodeFinal.ACCOUNT_LOCKED_MESSAGE);
        }
        List<GrantedAuthority> authorityList = Lists.newArrayList();
        // 普通用户
        if (Objects.nonNull(uemUser.getIsValid()) && Boolean.FALSE.equals(uemUser.getIsValid())) {
            throw new LockedException("该用户已被禁用");
        }
        authorityList.add(new SimpleGrantedAuthority("USER"));
        userDTO.setUsername(uemUser.getAccount());
        userDTO.setPassword(uemUser.getPassword());
        userDTO.setId(uemUser.getUemUserId());
        userDTO.setStatus(uemUser.getIsValid());
        userDTO.setRoles(authorityList);
        return userDTO;
    }

    /**
     * @Author:chenxf
     * @Description: 根据用户id和应用id返回用户信息
     * @Date: 9:48 2020/11/16
     * @Param: [uid, clientId]
     * @Return:com.share.auth.center.model.UserInfoModel
     */
    @Override
    public User getUserInfo(Long uid, String clientId) {
        log.info("------------------------------uid:" + uid + ",clientId: " + clientId);
        User userInfoModel = new User();
        // 查询用户
        UemUser uemUser = QUemUser.uemUser.selectOne().byId(uid);
        if (Objects.nonNull(uemUser)) {
            BeanUtils.copyProperties(uemUser, userInfoModel);
        } else {
            return null;
        }
        // 根据clientId查询应用id
        OauthClientDetails oauthClientDetail = QOauthClientDetails.oauthClientDetails.selectOne().byId(clientId);
        if (Objects.nonNull(oauthClientDetail)) {
            // 根据应用id和用户id查询角色
            List<UemUserRole> uemUserRoleList = QUemUserRole.uemUserRole.select(
                    QUemUserRole.uemUserRole.fieldContainer()
            ).where(
                    QUemUserRole.uemUserId.eq$(userInfoModel.getUemUserId())
                            .and(QUemUserRole.sysApplicationId.eq$(oauthClientDetail.getSysApplicationId()))
                            .and(QUemUserRole.isValid.eq$(true))
            ).execute();
            if (CollectionUtils.isNotEmpty(uemUserRoleList)) {
                List<UemUserRole> validUserRole = uemUserRoleList.stream().filter(UemUserRole::getIsValid).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(validUserRole) && validUserRole.size() == 1) {
                    // 查询角色名称
                    SysRole sysRole = QSysRole.sysRole.selectOne().byId(validUserRole.get(0).getSysRoleId());
                    if (Objects.nonNull(sysRole)) {
                        userInfoModel.setSysRoleId(sysRole.getSysRoleId());
                        userInfoModel.setSysRoleName(sysRole.getRoleName());
                        userInfoModel.setRoleCode(sysRole.getRoleCode());
                    }
                }
            }
        }
        userInfoModel.setClientId(clientId);
        return userInfoModel;
    }

    /**
     * @Author:chenxf
     * @Description: 校验用户是否可以登录
     * @Date: 10:14 2020/11/16
     * @Param: [account, password, clientId, request]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public ResultHelper<Object> validateUser(String account, String password, String clientId, String checkMoveId, Double xWidth, HttpServletRequest request, HttpServletResponse response) {
//        String resultStr = validateCode(checkMoveId, xWidth, true);
//        if (StringUtils.isNotEmpty(resultStr)) {
//            return CommonResult.getFaildResultData(resultStr);
//        }
        // 将滑动结算成功标志设置到session中，/login接口的拦截器中直接判断session中标志即可
        HttpSession session = request.getSession();
        session.setAttribute(checkMoveId, true);
        String errMsg = validateAccount(clientId, account, password, request, response);
        if (StringUtils.isNotEmpty(errMsg)) {
            return CommonResult.getFaildResultData(errMsg);
        } else {
            return CommonResult.getSuccessResultData();
        }
    }

    /**
     * @Author:chenxf
     * @Description: 校验账号
     * @Date: 10:14 2021/12/22
     * @Param: [clientId, account, password, request, response]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     */
    private String validateAccount(String clientId, String account, String password, HttpServletRequest request, HttpServletResponse response) {
        // 根据clientId查询应用id
        OauthClientDetails oauthClientDetails = QOauthClientDetails.oauthClientDetails.selectOne().byId(clientId);
        String cookieClientId = CookieUtil.getCookieByName(request, CodeFinal.CLIENT_ID);
        if (null == oauthClientDetails) {
            return "客户端ClientId不存在!";
        }
        //若校验的clientId和cookie中存放的不一致，则以user中的为标准
        if (StringUtils.isEmpty(cookieClientId) || !clientId.equals(cookieClientId)) {
            CookieUtil.addCookie(response, CodeFinal.CLIENT_ID, clientId, "");
        }
        log.info("查询到登录应用,应用id：" + oauthClientDetails.getSysApplicationId());
        SysApplication sysApplication = QSysApplication.sysApplication.selectOne().byId(oauthClientDetails.getSysApplicationId());
        if (Boolean.FALSE.equals(sysApplication.getIsValid())) {
            return "该应用被禁用，无法登录";
        }
        String decPassword;
        try {
            decPassword = AES128Util.decrypt(password, aesSecretKey);
        } catch (Exception e) {
            log.error("validateUser调用AES128Util.decrypt(password, aesSecretKey)解密密码：{}，解密失败", password, e);
            return "密码解密失败";
        }
        // 密码加密
        password = MD5EnCodeUtils.encryptionPassword(decPassword);
        // 查询用户信息
        UemUser user = QUemUser.uemUser
                .selectOne()
                .where(QUemUser.account.eq$(account))
                .execute();
        // 校验账号是否锁定
        if (user.getIsLocked()) {
            return CodeFinal.ACCOUNT_LOCKED_MESSAGE;
        }
        // 用户登录
        if (Objects.equals(user.getPassword(), password)) {
            log.info("查询到登录用户,用户id：" + user.getUemUserId());
            // 校验账号启/禁用
            if (user.getIsValid() == null || !user.getIsValid()) {
                return ENABLED_MESSAGE;
            }
            User userInfoModel = new User();
            BeanUtils.copyProperties(user, userInfoModel);
            String credential = credentialProcessor.createCredential(userInfoModel);
            credentialProcessor.deliveryCredential(response, credential, user.getUemUserId().toString());
            // 保存登录日志
            this.saveLoginLog(user, oauthClientDetails, account, request);
            return null;
        }
        // 设置用户锁定信息
        this.setUserLockedInfo(user);
        // 校验账号是否锁定
        if (user.getIsLocked()) {
            return CodeFinal.ACCOUNT_LOCKED_MESSAGE;
        }
        Object obj = redisInterface.get(RedisMqConstant.ACCOUNT_LOCKED_KEY_PRE + account);
        if (Objects.nonNull(obj)) {
            long errorSize = Long.parseLong(obj.toString());
            long reTrySize = CodeFinal.ACCOUNT_LOCKED_PASSWORD_ERROR_TIMES - errorSize;
            return "密码错误，重试" + reTrySize + "次后将被锁定！";
        }
        return "用户名或密码错误，请确认！";
    }

    private String validateOauthAccount(String clientId, String account, String password, HttpServletRequest request, HttpServletResponse response) {
        OauthClientDetails oauthClientDetails = QOauthClientDetails.oauthClientDetails.selectOne().byId(clientId);
        String cookieClientId = CookieUtil.getCookieByName(request, CodeFinal.CLIENT_ID);
        if (null == oauthClientDetails) {
            return "客户端ClientId不存在!";
        }
        //若校验的clientId和cookie中存放的不一致，则以user中的为标准
        if (StringUtils.isEmpty(cookieClientId) || !clientId.equals(cookieClientId)) {
            CookieUtil.addCookie(response, CodeFinal.CLIENT_ID, clientId, "");
        }
        log.info("查询到登录应用,应用id：" + oauthClientDetails.getSysApplicationId());
        SysApplication sysApplication = QSysApplication.sysApplication.selectOne().byId(oauthClientDetails.getSysApplicationId());
        if (Boolean.FALSE.equals(sysApplication.getIsValid())) {
            return "该应用被禁用，无法登录";
        }
        // 查询是否是其他用户登录
        UemUser user = this.getUemUser(account);
        if (Objects.isNull(user)) {
            return "用户名不存在！";
        }
        return null;
    }

    /**
     * @Author:chenxf
     * @Description: 校验用户是否可以登录
     * @Date: 10:14 2020/11/16
     * @Param: [account, password, clientId, request]
     * @Return:java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    @Deprecated
    public ResultHelper<Object> validateUserByDigitalCode(String account, String password, String clientId, String checkDigitalId, String verifyCode, HttpServletRequest request, HttpServletResponse response) {
//        String resultStr = validateDigitalCode(checkDigitalId, verifyCode, true);
//        if (StringUtils.isNotEmpty(resultStr)) {
//            return CommonResult.getFaildResultData(resultStr);
//        }
        // 验证码验证通过设置到session中，/login接口的拦截器中直接判断session中标志即可
        HttpSession session = request.getSession();
        session.setAttribute(checkDigitalId, true);
        // 根据clientId查询应用id
        OauthClientDetails oauthClientDetails = QOauthClientDetails.oauthClientDetails.selectOne().byId(clientId);
        String cookieClientId = CookieUtil.getCookieByName(request, CodeFinal.CLIENT_ID);
        if (StringUtils.isEmpty(cookieClientId)) {
            // 没有clientId默认登录账号权限系统
            CookieUtil.addCookie(response, CodeFinal.CLIENT_ID, clientId, "");
        }
        log.info("查询到登录应用,应用id：" + oauthClientDetails.getSysApplicationId());
        SysApplication sysApplication = QSysApplication.sysApplication.selectOne().byId(oauthClientDetails.getSysApplicationId());
        if (Boolean.FALSE.equals(sysApplication.getIsValid())) {
            return CommonResult.getFaildResultData("该应用被禁用，无法登录");
        }
        String decPassword;
        try {
            decPassword = AES128Util.decrypt(password, aesSecretKey);
        } catch (Exception e) {
            log.error("validateUser调用AES128Util.decrypt(password, aesSecretKey)解密密码：{}，解密失败", password, e);
            return CommonResult.getFaildResultData("密码解密失败");
        }
        // 密码加密
        password = MD5EnCodeUtils.encryptionPassword(decPassword);
        // 查询用户
        UemUser user = this.getUemUser(account);
        // 校验账号是否锁定
        if (user.getIsLocked()) {
            return CommonResult.getFaildResultData(CodeFinal.ACCOUNT_LOCKED_MESSAGE);
        }
        // 用户登录
        if (Objects.equals(user.getPassword(), password)) {
            log.info("查询到登录用户,用户id：" + user.getUemUserId());
            // 校验账号启/禁用
            if (user.getIsValid() == null || !user.getIsValid()) {
                return CommonResult.getFaildResultData(ENABLED_MESSAGE);
            }
            // 保存登录日志
            this.saveLoginLog(user,oauthClientDetails, account, request);
            return CommonResult.getSuccessResultData();
        }
        // 设置用户锁定信息
        this.setUserLockedInfo(user);
        // 校验账号是否锁定
        if (user.getIsLocked()) {
            return CommonResult.getFaildResultData(CodeFinal.ACCOUNT_LOCKED_MESSAGE);
        }
        return CommonResult.getFaildResultData("用户名或密码错误，请确认！");
    }

    /**
     * @Author:chenxf
     * @Description: 校验验证码
     * @Date: 11:20 2021/1/13
     * @Param: [result, checkMoveId, xWidth, deleteRedisSign]
     * @Return:java.lang.String
     */
    private String validateCode(String checkMoveId, Double xWidth, boolean deleteRedisSign) {
        String result = "";
        if (StringUtils.isEmpty(checkMoveId)) {
            result = "入参错误checkMoveId为空";
            return result;
        }
        if (Objects.isNull(xWidth)) {
            result = "滑动距离为空，请重试";
            return result;
        }
        String object = redisInterface.get(checkMoveId);
        if (Objects.isNull(object)) {
            log.info("滑动解锁获取redis缓存数据失败");
            result = "滑动解锁失败，请稍后重试";
            return result;
        }
        Double width = Double.valueOf(object);
        log.info("入参：" + xWidth + "，redis参数：" + width);
        int xWidthMax = 4;
        if (Math.abs(xWidth - width) > xWidthMax) {
            result = "验证失败，请重新滑动拼图解码";
            //验证不通过
            return result;
        } else {
            if (deleteRedisSign) {
                redisInterface.del(checkMoveId);
            }
        }
        return result;
    }

    /**
     * @Author:chenxf
     * @Description: 校验验证码(数字验证码)
     * @Date: 11:20 2021/1/13
     * @Param: [result, checkDigitalId, verifyCode, deleteRedisSign]
     * @Return:java.lang.String
     */
    private String validateDigitalCode(String checkDigitalId, String verifyCode, boolean deleteRedisSign) {
        String result = "";
        if (StringUtils.isEmpty(checkDigitalId)) {
            result = "入参错误checkDigitalId为空";
            return result;
        }
        if (Objects.isNull(verifyCode)) {
            result = "验证码为空，请重试";
            return result;
        }
        String object = redisInterface.get(checkDigitalId);
        if (Objects.isNull(object)) {
            log.info("获取数字验证码redis缓存数据失败");
            result = "验证码校验失败，请稍后重试";
            return result;
        }
        String checkCode = String.valueOf(object);
        log.info("入参：" + verifyCode + "，redis参数：" + checkCode);
        if (!checkCode.equals(verifyCode)) {
            result = "验证失败，请重新校验";
            //验证不通过
            return result;
        } else {
            if (deleteRedisSign) {
                redisInterface.del(checkDigitalId);
            }
        }
        return result;
    }

    /**
     * 获取其它环境token接口
     *
     * @param code         oauth2授权码模式授权码code
     * @param env          环境地址
     * @param clientId     应用clientId
     * @param clientSecret 应用密钥，为空时使用默认密钥
     * @param request      请求
     * @param response     响应
     * @return 请求结果
     */
    @Override
    public ResultHelper<Object> getToken(String code, String env, String clientId, String clientSecret, HttpServletRequest request, HttpServletResponse response) {
        log.info("public ResultHelper<Object> getToken()请求参数：code：{}， env：{}， clientId：{}，clientSecret：{}", code, env, clientId, clientSecret);
        TokenModel tokenModel;
        // 认证地址为空
        if (StringUtils.isEmpty(env)) {
            log.info("认证地址为空");
            return CommonResult.getFaildResultData("认证地址为空");
        }
        try {
//            String innerAdd = AuthAddressEnum.getInnerByOuter(env);
//            log.info("env：{}地址映射为：{}", env, innerAdd);
//            //认证地址映射失败
//            if (StringUtils.isEmpty(innerAdd)) {
//                log.info("认证地址映射失败");
//                return CommonResult.getFaildResultData("认证地址映射失败");
//            }
            tokenModel = this.getToken(code, env, clientId, clientSecret);
        } catch (Exception e) {
            log.info("获取token失败：{}", e.getMessage(), e);
            return CommonResult.getFaildResultData("获取token失败");
        }
        // 根据用户Id的和clientId查出用户信息
        User userInfoModel = credentialProcessor.parseCredential(tokenModel.getJwt());
        credentialProcessor.deliveryCredential(response, tokenModel.getJwt(), userInfoModel.getUemUserId().toString());
        HttpSession session = request.getSession();
        session.setAttribute(CodeFinal.USER, userInfoModel);
        CookieUtil.addCookie(response, "state", env, "");
        return CommonResult.getSuccessResultData("获取token成功");
    }

    /**
     * @Author:chenxf
     * @Description: 校验滑动结算是否成功接口
     * @Date: 15:02 2021/1/14
     * @Param: [checkMoveId, xWidth]
     * @Return:java.lang.Boolean
     */
    @Override
    public Boolean validateUnlock(String checkMoveId, Double xWidth) {
        String result = validateCode(checkMoveId, xWidth, false);
        return StringUtils.isEmpty(result);
    }

    /**
     * @Author:ecchen
     * @Description: 校验验证码校验是否成功接口
     * @Date: 15:02 2021/1/14
     * @Param: [checkDigitalId, verifyCode]
     * @Return:java.lang.Boolean
     */
    @Override
    public Boolean validateDigitalCode(String checkDigitalId, String verifyCode) {
        String result = validateDigitalCode(checkDigitalId, verifyCode, false);
        return StringUtils.isEmpty(result);
    }

    /**
     * @param code              授权码
     * @param httpAuthCenterUrl 认证中心地址
     * @param clientId          应用id
     * @param clientSecret      密钥
     * @return token
     * @auth:chenxf
     * @description: 调用oauth2接口获取token
     * @date: 16:17 2021/1/18
     * @Param: [code, httpAuthCenterUrl]
     * @Return:com.share.auth.center.model.dto.TokenModel
     */
    private TokenModel getToken(String code, String httpAuthCenterUrl, String clientId, String clientSecret) {
        // 请求参数clientId为空，从cookie中获取
        if (StringUtils.isBlank(clientId)) {
            Cookie cookie = CookieUtils.getCookie(CodeFinal.CLIENT_ID);
            // cookie中存在clientId
            if (Objects.nonNull(cookie)) {
                clientId = cookie.getValue();
            }
        }
        // clientId为空
        if (StringUtils.isBlank(clientId)) {
            log.error("请求参数不存在clientId，cookie不存在{}", CodeFinal.CLIENT_ID);
            throw new BusinessRuntimeException("clientId不能为空");
        }
        // 默认公共服务clientSecret
        if (StringUtils.isBlank(clientSecret)) {
            clientSecret = ssoPortalClientSecret;
        }
        Map<String, String> header = new HashMap<>(2);
        OauthClientDetails oauthClientDetails = QOauthClientDetails.oauthClientDetails.selectOne().byId(clientId);
        // 不存在clientId对应的应用
        if (Objects.isNull(oauthClientDetails)) {
            log.error("不存在clientId：{}对应的应用信息", clientId);
            throw new BusinessRuntimeException("clientId错误");
        }
        try {
            StringBuilder url = new StringBuilder(httpAuthCenterUrl)
                    .append("/share-auth-center/oauth/token")
                    .append("?client_id=")
                    .append(clientId)
                    .append("&client_secret=")
                    .append(clientSecret)
                    .append("&code=")
                    .append(code)
                    .append("&grant_type=authorization_code")
                    .append("&redirect_uri=")
                    .append(oauthClientDetails.getWebServerRedirectUri());
            log.info("getToken请求获取token，请求信息：{}", url);
            log.info("getToken请求获取token，请求信息：{}", url);
            JSONObject result = HttpsClientUtil.doPost(url.toString(), header);
            log.info("获取授权接口返回值：" + result);
            // 获取授权接口调用失败
            if (result == null) {
                log.error("获取授权接口调用失败，请重试");
                throw new BusinessRuntimeException("获取授权接口调用失败，请重试");
            }
            // 获取授权失败
            if (result.get(CodeFinal.ERROR_NAME) != null) {
                log.error("获取授权失败，请重试：{}", result.get(CodeFinal.MSG_NAME).toString());
                throw new BusinessRuntimeException(result.get(CodeFinal.MSG_NAME) == null ? GRANT_FAIL_MESSAGE : result.get(CodeFinal.MSG_NAME).toString());
            }
            // 获取授权失败
            if (result.get(CodeFinal.ACCESS_TOKEN_NAME) == null || result.get(CodeFinal.REFRESH_TOKEN_NAME) == null) {
                log.error(GRANT_FAIL_MESSAGE);
                throw new BusinessRuntimeException(GRANT_FAIL_MESSAGE);
            }
            TokenModel tokenModel = new TokenModel();
            tokenModel.setAccessToken(result.get(CodeFinal.ACCESS_TOKEN_NAME).toString());
            tokenModel.setRefreshToken(result.get(CodeFinal.REFRESH_TOKEN_NAME).toString());
            tokenModel.setUid(result.get(CodeFinal.USER_ID_NAME) == null ? null : result.get(CodeFinal.USER_ID_NAME).toString());
            tokenModel.setExpiresIn(result.get(CodeFinal.EXPIRE_IN_NAME) == null ? null : result.get(CodeFinal.EXPIRE_IN_NAME).toString());
            tokenModel.setJwt(result.get(CodeFinal.JWT_NAME) == null ? null : result.get(CodeFinal.JWT_NAME).toString());
            return tokenModel;
        } catch (Exception e) {
            log.error("获取token失败，：{}", e.getMessage(), e);
            throw new BusinessRuntimeException(e.getMessage());
        }
    }


    @Override
    public void lockedUser(String userName) {
        // 查询用户
        UemUser user = this.getUemUser(userName);
        // 设置用户锁定信息
        this.setUserLockedInfo(user);
    }

    /**
     * 设置用户锁定信息
     *
     * @param uemUser         用户
     */
    private void setUserLockedInfo(UemUser uemUser) {
        // 判断用户是否存在，用户不存在不锁定账号
        if (Objects.isNull(uemUser)) {
            return;
        }
        // 获取账号
        String account = uemUser.getAccount();
//        redisInterface.del(RedisMqConstant.ACCOUNT_LOCKED_KEY_PRE + account);
        // 密码错误次数+1
        Long incr = redisInterface.incr(RedisMqConstant.ACCOUNT_LOCKED_KEY_PRE + account);
        // 当前时间毫秒数
        long current = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        // 明天0点毫秒数
        long tomorrowZero = calendar.getTimeInMillis();
        // 今日剩余时间
        long remainSecond = (tomorrowZero - current) / 1000;
        // 过期时间
        redisInterface.expire(RedisMqConstant.ACCOUNT_LOCKED_KEY_PRE + account, Integer.parseInt(String.valueOf(remainSecond)));
        log.info("账号：{}，密码输入错误{}次", account, incr);

        // 密码错误次数5次，锁定用户
        if (incr >= CodeFinal.ACCOUNT_LOCKED_PASSWORD_ERROR_TIMES) {
            log.info("账号：{}，密码输入错误次数已达5次，进行锁定，10分钟后解锁", account);
            // 延时任务业务数据
            DelayTaskDTO data = new DelayTaskDTO();
            data.setAccount(account);
            // 延时十分钟解锁
            redisDelayedQueue.addQueue(data, RedisMqConstant.ACCOUNT_LOCKED_SECONDS, TimeUnit.SECONDS, UemUserUnlockListener.class.getSimpleName());
            // 锁定用户
            uemUser.setIsLocked(true);
            uemUser.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
            QUemUser.uemUser.save(uemUser);
        }
    }

    @Override
    public void unlockedUser(String account) {
        // 清除密码错误次数
        redisInterface.del(RedisMqConstant.ACCOUNT_LOCKED_KEY_PRE + account);
        // 查询用户
        UemUser user = QUemUser.uemUser.selectOne().where(QUemUser.account.eq$(account)).execute();
        // 解锁用户
        if (Objects.nonNull(user)) {
            user.setRowStatus(RowStatusConstants.ROW_STATUS_MODIFIED);
            user.setIsLocked(Boolean.FALSE);
            QUemUser.uemUser.save(user);
        }
    }

    /**
     * 保存登录日志
     *
     * @param uemUser            用户
     * @param oauthClientDetails 登录应用
     * @param username           登录账号
     * @param request            登录请求信息
     */
    private void saveLoginLog(UemUser uemUser, OauthClientDetails oauthClientDetails,
                              String username, HttpServletRequest request) {
        // 登录日志
        UemLog uemLog = new UemLog();
        uemLog.setLogDate(new Date());
        uemLog.setIpAddress(request.getRemoteAddr());
        uemLog.setBrowser(RequestUtil.getBrowser(request));
        // 测试环境无法访问外网
        uemLog.setWay(CodeFinal.ZERO_STRING);
        uemLog.setEquipment(CodeFinal.ONE_STRING);
        uemLog.setResult(CodeFinal.ZERO_STRING);
        uemLog.setLoginType(CodeFinal.ZERO_STRING);
        uemLog.setRowStatus(RowStatusConstants.ROW_STATUS_ADDED);
        // 设置登录应用
        if (Objects.nonNull(oauthClientDetails)) {
            uemLog.setApplicationId(oauthClientDetails.getSysApplicationId());
        }
        // 设置登录方式和登录类型
        if (username.equals(uemUser.getAccount())) {
            uemLog.setWay(CodeFinal.LOGIN_WAY_USERNAME);
            uemLog.setLoginType(CodeFinal.LOGIN_WAY_USERNAME);
        } else if (username.equals(uemUser.getMobile())) {
            uemLog.setWay(CodeFinal.LOGIN_WAY_PHONE);
            uemLog.setLoginType(CodeFinal.LOGIN_WAY_PHONE);
        } else if (username.equals(uemUser.getEmail())) {
            uemLog.setWay(CodeFinal.LOGIN_WAY_EMAIL);
            uemLog.setLoginType(CodeFinal.LOGIN_WAY_EMAIL);
        }
        // 设置用户id
        uemLog.setUemUserId(uemUser.getUemUserId());
        // 保存
        QUemLog.uemLog.save(uemLog);
    }

    @Override
    public int saveToEs(UemLog uemLog, UemUser uemUser) {
        long snowId = snowFlakeGenerator.next();
        uemLog.setUemLogId(snowId);
        uemLog.setCity("");
        Map<String, Object> newMap = new HashMap<>();
        Map<String, Object> map = BeanUtil.beanToMap(uemLog);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            newMap.put(StrUtil.toUnderlineCase(entry.getKey()), entry.getValue());
        }
        if (uemUser != null) {
            newMap.put("account", uemUser.getAccount());
            newMap.put("name", uemUser.getName());
            newMap.put("user_type", uemUser.getUserType());
        }
        return saveDataToEs(EsConstant.UEM_LOG, EsConstant.TYPE, snowId + "", newMap);

    }

    private int saveDataToEs(String index, String type, String snowId, Map<String, Object> jsonObject) {
        /*IndexRequest request = null;
        if (org.springframework.util.StringUtils.isEmpty(type)) {
            request = new IndexRequest(index, snowId);
        } else {
            request = new IndexRequest(index, type, snowId);
        }
        request.source(jsonObject);
        ActionFuture<IndexResponse> response = esClient.index(request);
        IndexResponse result = response.actionGet();
        RestStatus status = result.status();
        if (Objects.equals(result.getShardInfo().getSuccessful(), 1)) {
            return 1;
        } else {
            return 0;
        }*/
        return 0;
    }

    @Override
    public String authentication(String clientId, String redirectUri, String responseType, String state, HttpServletRequest request, HttpServletResponse response) {
        // 根据jwt获取用户信息
        User userInfoModel = credentialProcessor.parseCredential(request);
        if (Objects.nonNull(userInfoModel)) {
            String errMsg = validateOauthAccount(userInfoModel.getClientId(), userInfoModel.getAccount(), userInfoModel.getPassword(), request, response);
            if (StringUtils.isNotEmpty(errMsg)) {
                request.getSession().invalidate();
                Cookie cookie = new Cookie(CodeFinal.ACCESS_TOKEN_NAME, (String) null);
                cookie.setMaxAge(0);
                cookie.setPath("/");
                Cookie uidCookie = new Cookie("uid", (String) null);
                cookie.setMaxAge(0);
                uidCookie.setPath("/");
                if (Objects.nonNull(webDomain) && !CodeFinal.NULL.equals(webDomain)) {
                    cookie.setDomain(webDomain);
                    uidCookie.setDomain(webDomain);
                }
                response.addCookie(cookie);
                response.addCookie(uidCookie);
            } else {
                // 认证信息
                SecurityContext securityContext = (SecurityContext) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
                if (Objects.isNull(securityContext)) {
                    securityContext = SecurityContextHolder.getContext();
                }
                Authentication authentication = securityContext.getAuthentication();
                // 判断auth认证是否过期, 若过期，从jwt获取用户信息进行认证
                if (Objects.isNull(authentication)) {
                    // token
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userInfoModel.getAccount(), userInfoModel.getPassword());
                    token.setDetails(new WebAuthenticationDetails(request));
                    // 认证
                    authentication = authenticationManager.authenticate(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    HttpSession session = request.getSession(true);
                    // 在session中存放security context,方便同一个session中控制用户的其他操作
                    session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
                }
                // 获取uid
                String uidName = "uid";
                String uidValue = CookieUtil.getCookieByName(request, uidName);
                // uid是否过期，过期则刷新uid
                if (StringUtils.isBlank(uidValue)) {
                    Cookie uidCookie = new Cookie("uid", userInfoModel.getUemUserId().toString());
                    uidCookie.setPath("/");
                    uidCookie.setMaxAge(cookieExpireSeconds - 10);
                    uidCookie.setSecure(false);
                    response.addCookie(uidCookie);
                }
            }
        } else {
            request.getSession().invalidate();
        }
        // 跳转认证
        CookieUtil.addCookie(response, CodeFinal.CLIENT_ID, clientId, "");
        CookieUtil.addCookie(response, CodeFinal.RETURN_URL, state, "");
        String redirectUrl = "/oauth/authorize" + "?client_id=" + clientId + "&redirect_uri=" + redirectUri + "&response_type=" + responseType + "&state=" + state;
        response.setStatus(HttpServletResponse.SC_FOUND);
        return "redirect:" + redirectUrl;
    }

    @Override
    public String createCredentialCookie(User userInfo) {
        List<Cookie> cookieList = new ArrayList<>();
        // jwt
        String jwt = credentialProcessor.createCredential(userInfo);
        Cookie cookie = new Cookie(jwtName, jwt);
        cookie.setPath("/");
        if (Objects.nonNull(webDomain) && !CodeFinal.NULL.equals(webDomain)) {
            cookie.setDomain(webDomain);
        }
        cookie.setHttpOnly(true);
        cookie.setMaxAge(cookieExpireSeconds - 10);
        cookie.setSecure(false);
        cookieList.add(cookie);
        // uid
        Cookie uidCookie = new Cookie("uid", userInfo.getUemUserId().toString());
        uidCookie.setPath("/");
        if (Objects.nonNull(webDomain) && !CodeFinal.NULL.equals(webDomain)) {
            uidCookie.setDomain(webDomain);
        }
        uidCookie.setMaxAge(cookieExpireSeconds - 10);
        uidCookie.setSecure(false);
        cookieList.add(uidCookie);
        return EntityUtils.toJsonString(cookieList);
    }

}
