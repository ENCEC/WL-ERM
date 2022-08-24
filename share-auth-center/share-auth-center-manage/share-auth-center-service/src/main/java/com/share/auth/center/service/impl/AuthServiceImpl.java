package com.share.auth.center.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.share.auth.center.constants.RedisMqConstant;
import com.share.auth.center.credential.CredentialProcessor;
import com.share.auth.center.enums.GlobalEnum;
import com.share.auth.center.model.dto.OauthTokenDto;
import com.share.auth.center.model.dto.OpenIdResponseDto;
import com.share.auth.center.model.dto.SsoUserInfoDto;
import com.share.auth.center.model.entity.*;
import com.share.auth.center.model.querymodels.*;
import com.share.auth.center.service.AuthService;
import com.share.auth.center.util.EntityUtils;
import com.share.auth.center.util.HttpsClientUtil;
import com.share.auth.center.util.OauthClientUtils;
import com.share.auth.center.util.RedisUtil;
import com.share.support.model.User;
import com.share.support.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author chenxf
 * @date 2021-01-21 17:06
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Value("${sso.login.clientId}")
    private String clientId;

    @Value("${sso.login.clientSecret}")
    private String clientSecret;

    @Value("${sso.login.authorize.requestUrl}")
    private String authorizeRequestUrl;

    @Value("${sso.login.authorize.redirectUri}")
    private String authorizeRedirectUri;

    @Value("${sso.login.token.requestUrl}")
    private String tokenRequestUrl;

    @Value("${sso.login.token.redirectUri}")
    private String tokenRedirectUri;

    @Value("${sso.login.openId.requestUrl}")
    private String openIdRequestUrl;

    @Value("${sso.login.userInfo.requestUrl}")
    private String userInfoRequestUrl;

    @Value("${sso.login.supply-chain-url}")
    private String supplyChainUrl;

    @Value("${sso.login.errorPage}")
    private String errorPage;

    @Value("${sso.login.permissionApplyPage}")
    private String permissionApplyPage;

    @Value("${sso.login.permissionApplyingPage}")
    private String permissionApplyingPage;

    @Autowired
    private CredentialProcessor credentialProcessor;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * @Author:chenxf
     * @Description: 统一认证初始化url
     * @Date: 9:36 2021/1/26
     * @Param: []
     * @Return:java.lang.String
     */
    @Override
    public String authorize(String applicationCode) {
        // 回调接口，返回应用编码
        String redirectUri = authorizeRedirectUri + "?applicationCode=" + (StringUtils.isBlank(applicationCode) ? "" : applicationCode);
        init();
        try {
            //配置请求参数，构建oauthd的请求。设置请求服务地址（authorizeUrl）、clientId、response_type、redirectUrl
            OAuthClientRequest accessTokenRequest = OAuthClientRequest
                    .authorizationLocation(authorizeRequestUrl)
                    .setResponseType("code")
                    .setClientId(clientId)
                    .setRedirectURI(redirectUri)
                    .setState(UUID.randomUUID().toString().replace("-", ""))
                    .buildQueryMessage();
            return accessTokenRequest.getLocationUri();
        } catch (OAuthSystemException e) {
            log.error("配置authorizeCode获取地址错误：{}", e.getMessage());
        }
        return "";
    }

    /**
     * @Author:chenxf
     * @Description: 统一认证登录
     * @Date: 9:36 2021/1/26
     * @Param: [code, state, applicationCode, request, response]
     * @Return:java.lang.String
     */
    @Override
    public String login(String code, String state, String applicationCode, HttpServletRequest request, HttpServletResponse response) {
        init();
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        try {
            OAuthClientRequest accessTokenRequest = OAuthClientRequest
                    .tokenLocation(tokenRequestUrl)
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setClientId(clientId)
                    .setClientSecret(clientSecret)
                    .setRedirectURI(tokenRedirectUri)
                    .setCode(code)
                    .buildBodyMessage();
            //去服务端请求access token，并返回响应
            OAuthAccessTokenResponse oAuthResponse = oAuthClient.accessToken(accessTokenRequest, OAuthJSONAccessTokenResponse.class);

            if (oAuthResponse == null) {
                log.error("单点登录 获取token返回空");
                return this.getErrorPage(GlobalEnum.AuthErrorEnum.UNKNOWN, applicationCode);
            }
            //token返回体获取
            String tokenBody = oAuthResponse.getBody();
            if (StringUtils.isEmpty(oAuthResponse.getBody())) {
                log.error("单点登录 获取token返回空");
                return this.getErrorPage(GlobalEnum.AuthErrorEnum.UNKNOWN, applicationCode);
            }
            String accessToken = "";
            try {
                OauthTokenDto tokenResponseDto = JSON.parseObject(tokenBody, OauthTokenDto.class);
                //成功情况下
                if (tokenResponseDto == null) {
                    log.error("单点登录 token信息转换后为空");
                    return this.getErrorPage(GlobalEnum.AuthErrorEnum.UNKNOWN, applicationCode);
                }
                String ret = tokenResponseDto.getRet();
                String retSuccessCode = "0";
                if (retSuccessCode.equals(ret)) {
                    accessToken = tokenResponseDto.getAccess_token();
                } else {
                    //7001 acceccToken创建失败
                    //7018 client_secret非法（资源id和title不匹配）
                    //7020 code过期
                    //7081 client_id非法（不是服务端已存在的资源系统）
                    //7900 请求参数格式错误，具体参见返回信息中的msg字段
                    //7905 redirect_uri无法解析出主域名
                    log.error("单点登录 获取token失败：" + tokenResponseDto.getMsg());
                    return this.getErrorPage(GlobalEnum.AuthErrorEnum.UNKNOWN, applicationCode);
                }
            } catch (Exception e) {
                log.error("单点登录 token信息转换失败：{}， e：", tokenBody, e);
            }
            if (StringUtils.isNotEmpty(accessToken)) {
                return getOpenId(accessToken, applicationCode, request, response);
            } else {
                return this.getErrorPage(GlobalEnum.AuthErrorEnum.UNKNOWN, applicationCode);
            }
        } catch (Exception e) {
            log.error("/login错误 " + e.getMessage());
            return this.getErrorPage(GlobalEnum.AuthErrorEnum.UNKNOWN, applicationCode);
        }
    }

    /**
     * 根据token获取openId信息
     *
     * @param accessToken
     * @param applicationCode
     * @param request
     * @param response
     * @return
     */
    private String getOpenId(String accessToken, String applicationCode, HttpServletRequest request, HttpServletResponse response) {
        init();
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        try {
            OAuthClientRequest userInfoRequest = new OAuthBearerClientRequest(openIdRequestUrl)
                    .setAccessToken(accessToken).buildQueryMessage();

            OAuthResourceResponse resourceResponse = oAuthClient.resource(userInfoRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
            if (resourceResponse == null) {
                log.error("单点登录 获取openId信息错误，返回为空");
                return this.getErrorPage(GlobalEnum.AuthErrorEnum.UNKNOWN, applicationCode);
            }
            String body = resourceResponse.getBody();
            OpenIdResponseDto openIdResponseDto = JSON.parseObject(body, OpenIdResponseDto.class);
            if (openIdResponseDto == null) {
                log.error("单点登录 获取openId信息错误，返回信息为空");
                return this.getErrorPage(GlobalEnum.AuthErrorEnum.UNKNOWN, applicationCode);
            }
            String retSuccessCode = "0";
            if (retSuccessCode.equals(openIdResponseDto.getRet())) {
                return getUserName(openIdResponseDto.getOpenid(), accessToken, applicationCode, request, response);
            } else {
                //根据code信息做判断
                log.error("单点登录 获取业务用户信息错误,错误信息：" + openIdResponseDto.getMsg());
                String code = openIdResponseDto.getCode();
                //access_token不存在 重新授权
                String retNotExistCode = "9094";
                if (retNotExistCode.equals(code)) {
                    return "/oauthSSo/render";
                } else {
                    //请求参数格式错误 等信息 返回到500页面
                    return this.getErrorPage(GlobalEnum.AuthErrorEnum.UNKNOWN, applicationCode);
                }
            }
        } catch (Exception e) {
            log.error("根据token获取openId信息报错：" + e.getMessage());
        }
        return this.getErrorPage(GlobalEnum.AuthErrorEnum.UNKNOWN, applicationCode);
    }

    /**
     * 根据openId获取用户信息
     *
     * @param openId
     * @param accessToken
     * @param applicationCode
     * @param request
     * @param response
     */
    private String getUserName(String openId, String accessToken, String applicationCode, HttpServletRequest request, HttpServletResponse response) {
        init();
        try {
            Map<String, String> headers = new HashMap<>(16);
            String userNameUrl = userInfoRequestUrl + "?" + "access_token=" + accessToken + "&oauth_consumer_key=" + clientId + "&openid=" + openId;
            JSONObject result = HttpsClientUtil.doGet(userNameUrl, headers);
            if (result == null) {
                log.error("单点登录 获取用户信息为空");
                return this.getErrorPage(GlobalEnum.AuthErrorEnum.UNKNOWN, applicationCode);
            }
            log.info("获取用户信息返回json：" + result.toString());
            //结果解析
            String ret = "0";
            String account = "";
            String code = "";
            String msg = "";
            try {
                ret = result.get("ret") == null ? "" : result.get("ret").toString();
                code = result.get("code") == null ? "" : result.get("code").toString();
                msg = result.get("msg") == null ? "" : result.get("msg").toString();
                String userInfo = result.get("userinfo").toString();
                SsoUserInfoDto ssoUserInfoDto = JSON.parseObject(userInfo, SsoUserInfoDto.class);
                if (ssoUserInfoDto != null) {
                    account = ssoUserInfoDto.getAccount();
                }
            } catch (Exception e) {
                log.error("单点登录 用户信息结果转换失败：{}", e.getMessage(), e);
                return this.getErrorPage(GlobalEnum.AuthErrorEnum.UNKNOWN, applicationCode);
            }
            //获取账号信息
            String retSuccessCode = "0";
            if (retSuccessCode.equals(ret)) {
                if (StringUtils.isEmpty(account)) {
                    return this.getErrorPage(GlobalEnum.AuthErrorEnum.UNKNOWN, applicationCode);
                }
                log.info("获取到登录用户账号:" + account);
                return this.validUser(account, applicationCode, request, response);
            } else {
                //根据code信息做判断
                log.error("单点登录 获取业务用户信息错误,错误信息：" + msg);
                //access_token不存在 重新授权
                String retNotExistCode = "9094";
                // 没访问权限代码值
                String retNotAuthorityCode = "-24";
                if (retNotExistCode.equals(code)) {
                    return "/oauthSSo/render";
                } else if (retNotAuthorityCode.equals(code)) {
                    //当前用户无权限访问此资源 返回403页面
                    return this.getErrorPage(GlobalEnum.AuthErrorEnum.UNKNOWN, applicationCode);
                } else {
                    //请求参数格式错误，等其他错误 返回500页面
                    return this.getErrorPage(GlobalEnum.AuthErrorEnum.UNKNOWN, applicationCode);
                }
            }
        } catch (Exception e) {
            log.error("根据用户信息登录调度系统报错：{}", e.getMessage(), e);
        }
        return this.getErrorPage(GlobalEnum.AuthErrorEnum.UNKNOWN, applicationCode);
    }

    private static void init() {
        try {
            SSLContext sc = SSLContext.getInstance("TLSv1.2");
            HostnameVerifier hv = (requestedHost, remoteServerSession) -> requestedHost.equalsIgnoreCase(remoteServerSession.getPeerHost());
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509ExtendedTrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
                            throw new UnsupportedOperationException();
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
                            throw new UnsupportedOperationException();
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] x509Certificates, String s, Socket socket) {
                            throw new UnsupportedOperationException();
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] x509Certificates, String s, Socket socket) {
                            throw new UnsupportedOperationException();
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) {
                            throw new UnsupportedOperationException();
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) {
                            throw new UnsupportedOperationException();
                        }
                    }
            };
            sc.init(null, trustAllCerts, new SecureRandom());
            SSLContext.setDefault(sc);
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 获取统一报错页面及页面错误代码
     * @param authErrorEnum 错误代码枚举
     * @param applicationCode 应用编码
     * @return 统一报错页面
     */
    private String getErrorPage(GlobalEnum.AuthErrorEnum authErrorEnum, String applicationCode) {
        String paramEncode = "";
        try {
            String param = "errorCode=" + authErrorEnum.getCode() + "&applicationCode=" + (StringUtils.isBlank(applicationCode) ? "" : applicationCode);
            paramEncode = URLEncoder.encode(param, "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("{}页面参数URLEncoder编码失败：{}", this.errorPage, e.getMessage(), e);
        }
        return this.errorPage + "?" + paramEncode;
    }

    /**
     * 校验用户
     * @param account 账号
     * @param applicationCode 应用编码
     * @param request 请求
     * @param response 响应
     * @return 跳转地址
     */
    private String validUser(String account, String applicationCode, HttpServletRequest request, HttpServletResponse response) {
        UemUser uemUser = QUemUser.uemUser.selectOne(QUemUser.uemUser.fieldContainer()).where(QUemUser.source.eq$(GlobalEnum.UserSource.NTIP.getCode()).and(QUemUser.account.eq$(account).or(QUemUser.account.eq$("GJIM_" + account)))).execute();
        // 1.账号不存在
        if (Objects.isNull(uemUser)){
            return this.getErrorPage(GlobalEnum.AuthErrorEnum.ACCOUNT_NOT_EXIST, applicationCode);
        }
        // 3.账号禁用
        if (!uemUser.getIsValid()) {
            return this.getErrorPage(GlobalEnum.AuthErrorEnum.ACCOUNT_INVALID, applicationCode);
        }
        // 4.账号没有绑定企业
        if (Objects.isNull(uemUser.getBlindCompanny())) {
            return this.getErrorPage(GlobalEnum.AuthErrorEnum.ACCOUNT_UNBIND_COMPANY, applicationCode);
        }
        UemCompany uemCompany = QUemCompany.uemCompany.selectOne(QUemCompany.uemCompany.fieldContainer()).byId(uemUser.getBlindCompanny());
        if (Objects.isNull(uemCompany)) {
            return this.getErrorPage(GlobalEnum.AuthErrorEnum.ACCOUNT_UNBIND_COMPANY, applicationCode);
        }
        // 2.账号所属组织被禁用
        if (!uemCompany.getIsValid()) {
            return this.getErrorPage(GlobalEnum.AuthErrorEnum.ACCOUNT_COMPANY_INVALID, applicationCode);
        }
        // 8.applicationCode不存在
        if (StringUtils.isBlank(applicationCode)) {
            return this.getErrorPage(GlobalEnum.AuthErrorEnum.APPLICATION_NULL, applicationCode);
        }
        // 9.applicationCode错误
        OauthClientDetails oauthClientDetails = QOauthClientDetails.oauthClientDetails.selectOne(QOauthClientDetails.oauthClientDetails.fieldContainer()).where(QOauthClientDetails.clientId.eq$(applicationCode)).execute();
        if (Objects.isNull(oauthClientDetails)) {
            return this.getErrorPage(GlobalEnum.AuthErrorEnum.APPLICATION_NOT_EXIST, applicationCode);
        }
        // 5.应用被禁用
        SysApplication sysApplication = QSysApplication.sysApplication.selectOne(QSysApplication.sysApplication.fieldContainer()).byId(oauthClientDetails.getSysApplicationId());
        if (Objects.isNull(sysApplication)){
            return this.getErrorPage(GlobalEnum.AuthErrorEnum.APPLICATION_INVALID, applicationCode);
        }
        if (!sysApplication.getIsValid()) {
            return this.getErrorPage(GlobalEnum.AuthErrorEnum.APPLICATION_INVALID, applicationCode);
        }
        // 允许所有用户登录的客户端id
        List<Long> allowAllClientIdList = OauthClientUtils.getAllowAllClientId();
        // 只允许管理员登录的客户端id
        List<Long> allowAdminClientIdList = OauthClientUtils.getAllowAdminClientId();
        // 允许没有角色的账号登录
        List<Long> allowNoRoleClientIdList = OauthClientUtils.getAllowNoRoleClientId();
        // 不允许访问账号、公共服务系统
        if (allowAllClientIdList.contains(sysApplication.getSysApplicationId()) || allowAdminClientIdList.contains(sysApplication.getSysApplicationId())) {
            return this.getErrorPage(GlobalEnum.AuthErrorEnum.APPLICATION_ERROR, applicationCode);
        }
        // 默认登录角色（登录应用只有一个角色）
        SysRole loginRole = null;
        // 用户角色
        List<UemUserRole> uemUserRoleList = QUemUserRole.uemUserRole.select(QUemUserRole.uemUserRole.fieldContainer()).where(QUemUserRole.uemUserId.eq$(uemUser.getUemUserId()).and(QUemUserRole.sysApplicationId.eq$(sysApplication.getSysApplicationId()))).execute();
        // 6.角色被禁用（登录需要角色的应用，存在当前应用角色，但是角色被禁用）
        if (!allowNoRoleClientIdList.contains(sysApplication.getSysApplicationId())) {
            if (CollectionUtils.isNotEmpty(uemUserRoleList)) {
                // 有效用户角色
                List<UemUserRole> validUserRoleList = uemUserRoleList.stream().filter(UemUserRole::getIsValid).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(validUserRoleList)) {
                    return this.getErrorPage(GlobalEnum.AuthErrorEnum.ROLE_INVALID, applicationCode);
                }
                // 应用角色
                List<Long> roleIds = validUserRoleList.stream().map(UemUserRole::getSysRoleId).collect(Collectors.toList());
                List<SysRole> sysRoleList = QSysRole.sysRole.select(QSysRole.sysRole.fieldContainer()).where(QSysRole.sysRoleId.in$(roleIds)).execute();
                if (CollectionUtils.isEmpty(sysRoleList)) {
                    return this.getErrorPage(GlobalEnum.AuthErrorEnum.ROLE_INVALID, applicationCode);
                }
                // 有效应用角色
                List<SysRole> validRoleList = sysRoleList.stream().filter(SysRole::getIsValid).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(validRoleList)) {
                    return this.getErrorPage(GlobalEnum.AuthErrorEnum.ROLE_INVALID, applicationCode);
                }
                // 默认登录角色
                if (validRoleList.size() == 1) {
                    loginRole = validRoleList.get(0);
                }
            }
        }
        log.info("获取到登录用户：{}", uemUser.getUemUserId());
        // 登录信息
        User userInfoModel = new User();
        BeanUtils.copyProperties(uemUser, userInfoModel);
//        userInfoModel.setCompanyName(uemCompany.getCompanyNameCn());
        userInfoModel.setAppCode(oauthClientDetails.getSysApplicationId().toString());
//        if (Objects.nonNull(loginRole)) {
//            userInfoModel.setSysRoleId(loginRole.getSysRoleId());
//            userInfoModel.setSysRoleName(loginRole.getRoleName());
//            userInfoModel.setRoleCode(loginRole.getRoleCode());
//        }
        // 内部登录
        this.login(userInfoModel, applicationCode, request, response);
        // 跳转首页
        return oauthClientDetails.getWebServerRedirectUri();
    }

    /**
     * 内部自登录
     * @param userInfoModel 用户
     * @param applicationCode 登录应用编码
     * @param request 请求
     * @param response 响应
     */
    private void login(User userInfoModel, String applicationCode, HttpServletRequest request, HttpServletResponse response) {
        // 用户信息redisKey
        String redisKey = RedisMqConstant.USER_INFO_KEY_PRE + userInfoModel.getUemUserId() + "_" + userInfoModel.getAppCode();
        // 用户jwt的redisKey
        String userJwt = "USER_INFO_" + userInfoModel.getUemUserId() + "_jwt";
        // 将用户信息保存到redis缓存中
        redisUtil.setForTimeDays(redisKey, EntityUtils.toJsonString(userInfoModel), 1);
        // 登录成功生成token +jwt
        String jwt = credentialProcessor.createCredential(userInfoModel);
        // 将jwt保存到redis中
        redisUtil.setForTimeDays(userJwt, jwt, 1);
        // 投递jwt给前端
        credentialProcessor.deliveryCredential(response, jwt, userInfoModel.getUemUserId().toString());
        // 保存用户信息到session中
        HttpSession session = request.getSession();
        session.setAttribute("user", userInfoModel);
        CookieUtil.addCookie(response, "client_id", applicationCode, "");
    }

}
