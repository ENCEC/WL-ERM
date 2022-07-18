package com.share.auth.center.handler;

import com.gillion.saas.redis.SassRedisInterface;
import com.share.auth.center.constants.RedisMqConstant;
import com.share.auth.center.credential.CredentialProcessor;
import com.share.auth.center.model.entity.OauthClientDetails;
import com.share.auth.center.model.querymodels.QOauthClientDetails;
import com.share.auth.center.service.UemUserService;
import com.share.support.model.User;
import com.share.support.util.CookieUtil;
import com.share.auth.center.util.EntityUtils;
import com.share.auth.center.util.RedisUtil;
import com.share.support.model.MyUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

/**
 * @Author:chenxf
 * @Description: oauth2提供的登录成功处理方法
 * @Date: 16:00 2021/1/18
 * @Param: 
 * @Return:
 *
 */
@Slf4j
@Component
public class UserLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String USER="user";

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private CredentialProcessor credentialProcessor;

    @Autowired
    private UemUserService uemUserService;
    @Autowired
    private SassRedisInterface redisInterface;

    /**
     * @Author:chenxf
     * @Description: oauth2的登录成功回调方法，在此处生成eds所需要的access_token，并保存token信息等操作
     * @Date: 16:01 2021/1/18
     * @Param: [request, response, authentication]
     * @Return:void
     *
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 获取登录用户信息
        MyUser user = (MyUser) authentication.getPrincipal();
        // 获取cookie中的clientId
        String clientId = CookieUtil.getCookieByName(request, "client_id");
        log.info("clientId" + clientId);
        user.setClientId(clientId);
        // 根据用户Id的和clientId查出用户信息
        User userInfoModel = uemUserService.getUserInfo(user.getUserId(),clientId);
        OauthClientDetails oauthClientDetails = QOauthClientDetails.oauthClientDetails.selectOne().byId(clientId);
        // 设置appCode
        userInfoModel.setAppCode(oauthClientDetails.getSysApplicationId().toString());
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
        HttpSession session=request.getSession();
        session.setAttribute(USER, userInfoModel);
        // 清除密码错误次数
        redisInterface.del(RedisMqConstant.ACCOUNT_LOCKED_KEY_PRE + userInfoModel.getAccount());
        // 调用父类的方法，继续执行oauth2接下来的流程（授权码模式为返回code和state到指定回调地址）
        super.onAuthenticationSuccess(request,response,authentication);
    }
}
