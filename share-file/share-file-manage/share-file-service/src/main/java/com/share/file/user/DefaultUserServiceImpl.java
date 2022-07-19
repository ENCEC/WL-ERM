package com.share.file.user;

import com.alibaba.fastjson.JSON;
import com.gillion.ec.core.security.IUser;
import com.gillion.ec.core.security.UserInfoCollector;
import com.gillion.ec.core.security.UserService;
import com.gillion.ec.core.security.data.ITable;
import com.share.auth.center.api.AuthCenterInterface;
import com.share.support.constants.UserConstant;
import com.share.support.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @version:1.0
 * @Description: 当前登录用户信息 工具类
 * @author: SYF
 * @date: 2020年11月5日上午11:28:16
 */
@Service
public class DefaultUserServiceImpl implements UserService, UserInfoCollector {

    private static final String USER = "user";

    @Autowired
    private AuthCenterInterface authCenterInterface;
    /**
     * @return
     * @Description:获取当前登录用户的userid
     * @author: SYF
     * @date: 2020年11月5日上午11:24:54
     */
    @Override
    public Object getUserId() {
        final SupplyUserInfoModel currentLoginUser = (SupplyUserInfoModel) getCurrentLoginUser();
        return currentLoginUser == null ? null : currentLoginUser.getUemUserId();
    }

    /**
     * @return
     * @Description:
     * @author: SYF
     * @date: 2020年11月5日上午11:27:56
     */
    @Override
    public String getOfficeId() {
        final SupplyUserInfoModel currentLoginUser = (SupplyUserInfoModel) getCurrentLoginUser();
        return currentLoginUser == null ? "" : currentLoginUser.getUemUserId().toString();
    }

    /**
     * @return
     * @Description:获取当然登录用户
     * @author: SYF
     * @date: 2020年11月5日上午11:28:00
     */
    @Override
    public IUser getCurrentLoginUser() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(requestAttributes)) {
            throw new NullPointerException("无法获取ServletRequestAttributes对象");
        }
        HttpServletRequest request = requestAttributes.getRequest();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        /* 从session里获取用户，如果获取不到，就先调用认证权限获取用户信息，并将用户信息保存到session */
        if (user == null) {
            user = authCenterInterface.getUserInfo();
            session.setAttribute(USER, user);
        }
        String jsonUser = JSON.toJSONString(user);
        return JSON.parseObject(jsonUser, SupplyUserInfoModel.class);
    }

    /**
     * @param arg0
     * @return
     * @Description:
     * @author: SYF
     * @date: 2020年11月5日下午2:59:55
     */
    @Override
    public IUser findByUsername(String arg0) {

        return null;
    }

    /**
     * @return
     * @Description:
     * @author: SYF
     * @date: 2020年11月5日下午2:59:55
     */
    @Override
    public Collection<ITable> getAnonymousDataPermissionTables() {

        return Collections.emptyList();
    }

    /**
     * @return
     * @Description:
     * @author: SYF
     * @date: 2020年11月5日下午2:59:55
     */
    @Override
    public Iterable<String> getAnonymousPermissionUrlPatterns() {

        return null;
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
