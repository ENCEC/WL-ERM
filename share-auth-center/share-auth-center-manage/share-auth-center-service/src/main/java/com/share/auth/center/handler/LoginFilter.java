package com.share.auth.center.handler;

import com.share.support.util.AES128Util;
import com.share.support.util.MD5EnCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * @Author:chenxf
 * @Description: oauth2登录请求拦截器
 * @Date: 14:42 2021/1/11
 * @Param:
 * @Return:
 */
@Slf4j
public class LoginFilter implements Filter {

    /**
     * 密码字段
     */
    private static final String SECRET = "password";
    /**AES密钥*/
    private String aesSecretKey;

    public void setAesSecretKey(String aesSecretKey) {
        this.aesSecretKey = aesSecretKey;
    }

    /**
     * @Author:chenxf
     * @Description: 拦截oauth2的登录请求，对登录密码做二次加密，同时根据滑动解锁redisKey获取session中的值，并判断该值是否为滑动解锁成功（防止通过postman等方式直接调用登录接口）
     * @Date: 14:43 2021/1/11
     * @Param: [servletRequest, servletResponse, filterChain]
     * @Return:void
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        log.info("拦截到请求，请求url=" + request.getRequestURI());
        String password = request.getParameter(SECRET);
        log.info("拦截器替换密码，替换前密码为" + password);
        if (StringUtils.isNotEmpty(password)) {
            String checkMoveId = request.getParameter("checkMoveId");
            Object checkMoveId1 =  request.getAttribute("checkMoveId");
            HttpSession session = request.getSession();
            Object object = session.getAttribute(checkMoveId);
            if (Objects.isNull(object) || !(boolean) object) {
                this.outputMessage("滑动解锁失败，无法登录", response);
                return;
            }
            session.removeAttribute(checkMoveId);
            // AES解密登录密码
            ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper(request);
            String decPassword;
            try {
                decPassword = AES128Util.decrypt(password, aesSecretKey);
            } catch (Exception e) {
                log.error("LoginFilter调用AES128Util.decrypt(password, aesSecretKey)解密密码：{}，解密失败", password, e);
                this.outputMessage("密码解密失败", response);
                return;
            }
            // 登录密码加密
            String newPassword = MD5EnCodeUtils.encryptionPassword(decPassword);
            requestWrapper.addParameter(SECRET, newPassword);
            log.info("拦截器替换密码，替换后密码为" + requestWrapper.getParameter(SECRET));
            filterChain.doFilter(requestWrapper, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    /**
     * 输出信息
     * @param message 信息
     */
    private void outputMessage(String message, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write("{\"result\":" + message + "}");
        out.flush();
        out.close();
    }
}
