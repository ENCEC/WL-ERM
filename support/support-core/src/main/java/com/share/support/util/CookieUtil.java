package com.share.support.util;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chenxf
 * @date 2020-10-22 13:55
 */
public class CookieUtil {
    /**
     * @Author:chenxf
     * @Description: 获取cookie信息
     * @Date: 17:49 2020/11/28
     * @Param: [request, cookieName]
     * @Return:java.lang.String
     *
     */
    public static String getCookieByName(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;
        if (cookies != null) {
            for (int i = 0; i < cookies.length; ++i) {
                if (cookies[i].getName().equals(cookieName)) {
                    cookie = cookies[i];
                    break;
                }
            }
        }
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }
    /**
     * 添加cookie(浏览器关闭失效)
     */
    public static void addCookie(HttpServletResponse response, String cookieName, String cookieValue, String webSiteDomain) {
        try {
            // String webSiteDomain = ConfigPropertiesUtil.getPara(ConfigConstants.WEBSITE_DOMAIN, true);
            Cookie cookie = new Cookie(cookieName, cookieValue);
            cookie.setPath("/");
            if (StringUtils.isNotBlank(webSiteDomain)) {
                cookie.setDomain(webSiteDomain);
            }
            response.addCookie(cookie);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
