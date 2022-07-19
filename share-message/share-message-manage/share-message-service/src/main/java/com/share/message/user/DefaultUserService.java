package com.share.message.user;

import com.alibaba.fastjson.JSONObject;
import com.gillion.ec.core.security.IUser;
import com.gillion.ec.core.security.UserInfoCollector;
import com.gillion.ec.core.security.UserService;
import com.gillion.ec.core.security.data.ITable;
import com.gillion.ec.core.utils.UserUtils;
import com.share.auth.center.api.AuthCenterInterface;
import com.share.support.constants.UserConstant;
import com.share.support.model.User;
import com.share.support.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author liaowj
 * @version 1.0.0.0
 * @date 2018-09-04 17:11
 */
@Service
@Slf4j
public class DefaultUserService implements UserService, UserInfoCollector {

    private static final String USER = "user";

    private static final String UID = "uid";

    @Autowired
    private AuthCenterInterface authCenterInterface;

    @Override
    public IUser findByUsername(String s) {
        return UserUtils.anonymousUser;
    }

    @Override
    public Iterable<String> getAnonymousPermissionUrlPatterns() {
        return Lists.newArrayList();
    }

    @Override
    public Collection<ITable> getAnonymousDataPermissionTables() {
        return Lists.newArrayList();
    }

    @Override
    public Object getUserId() {
        final MsgUserInfoModel currentLoginUser = (MsgUserInfoModel) getCurrentLoginUser();
        return currentLoginUser==null?null:currentLoginUser.getUemUserId();
    }

    @Override
    public String getOfficeId() {
        //TODO 需要获取实际用户信息，由项目组自己实现
        return "";
    }

    @Override
    public IUser getCurrentLoginUser() {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(USER);
        /* 从session里获取用户，如果获取不到，就先调用认证权限获取用户信息，并将用户信息保存到session */
        if(user == null){
            user = authCenterInterface.getUserInfo();
            session.setAttribute(USER, user);
        }
        if(user == null){
            IUser anonymousUser = UserUtils.anonymousUser;
            return anonymousUser;
        }
        String jsonUser = JSONObject.toJSONString(user);
        MsgUserInfoModel msgUserInfoModel = JSONObject.parseObject(jsonUser, MsgUserInfoModel.class);
        return msgUserInfoModel;
    }

    /**
     * 拼接cookie信息
     * @param request
     * @return
     * @author wangcl
     */
    public List<String> getCookieList(HttpServletRequest request) {
        List<String> cookieList = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return cookieList;
        }
        for (Cookie cookie : cookies) {
            cookieList.add(cookie.getName() + "=" + cookie.getValue());
        }
        return cookieList;
    }

    /**
     * 登出接口
     * @param request
     * @author wangcl
     */
    public void logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        // 清除session
        session.removeAttribute(UserConstant.USER);
        authCenterInterface.logout();
    }
}
