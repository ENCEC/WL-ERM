package com.share.auth.filter;

import com.gillion.ec.core.utils.CookieUtils;
import com.share.auth.center.api.AuthCenterInterface;
import com.share.auth.config.AuthenticationConfig;
import com.share.auth.config.PatternPathMatcher;
import com.share.auth.constants.CodeFinal;
import com.share.auth.model.entity.SysRequestLog;
import com.share.auth.model.querymodels.QSysRequestLog;
import com.share.auth.user.DefaultUserService;
import com.share.support.model.RequestLog;
import com.share.support.model.User;
import com.share.support.result.ResultHelper;
import com.share.support.util.CookieUtil;
import com.share.support.util.LogUtil;
import com.share.support.util.RequestWrapper;
import com.share.support.util.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jooq.lambda.Seq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

/**
 * api接口过滤器，记录请求信息
 *
 * @author chenhy
 */
@WebFilter(filterName = "apiFilter", urlPatterns = "/*")
@Slf4j
public class ApiFilter implements Filter {

    @Value("${web_domain}")
    private String webDomain;

    @Value("${authentication.cookieExpireSeconds:3600}")
    private int cookieExpireSeconds;

    @Value("${authentication.millisMinuteTen:1200}")
    private Long millisMinuteTen;
    /**
     * 白名单集合
     */
    private Set<PatternPathMatcher> ignorePathPatternMatchers;

    @Autowired
    private AuthenticationConfig authenticationConfig;

    @Autowired
    private AuthCenterInterface authCenterInterface;

    @Autowired
    private DefaultUserService defaultUserService;

    /**
     * 拦截请求，记录请求信息
     *
     * @param servletRequest  请求
     * @param servletResponse 响应
     * @param filterChain     过滤器调用链
     * @throws IOException      IO异常
     * @throws ServletException servlet异常
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest) servletRequest);
        ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) servletResponse);
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            this.ignorePathPatternMatchers = Seq.seq(authenticationConfig.getIgnorePathPatterns())
                    .map(PatternPathMatcher::new)
                    .toSet();
            if (!isInIgnorePathPattern(requestWrapper.getRequestURI())) {
                String credential = CookieUtil.getCookieByName(requestWrapper, CodeFinal.ACCESS_TOKEN);
                if (Objects.nonNull(credential)) {
                    User user = authCenterInterface.parseCredential(credential);
                    if (Objects.nonNull(user) && Objects.nonNull(user.getExpireTime())) {
                        refreshCredential(request, response, user);
                    }
                }
            }
            filterChain.doFilter(requestWrapper, responseWrapper);
            log.info("保存请求日志");
            this.saveSysRequestLog(requestWrapper, responseWrapper, servletResponse);
        } catch (Exception e) {
            log.error("保存请求日志失败：{}", e.getMessage(), e);
        }
    }

    /**
     * 保存请求日志
     *
     * @param requestWrapper  请求包装类
     * @param responseWrapper 响应包装类
     * @param servletResponse 请求
     */
    private void saveSysRequestLog(RequestWrapper requestWrapper,
                                   ResponseWrapper responseWrapper,
                                   ServletResponse servletResponse) throws IOException {
        // 获取请求日志信息
        RequestLog requestLog = LogUtil.getRequestLog(requestWrapper, responseWrapper, servletResponse);
        log.info("接口请求信息：{}", requestLog);
        // 保存信息
        SysRequestLog sysRequestLog;
        // 判断是否新增
        if (Objects.isNull(requestLog.getRequestLogId())) {
            // 新增请求日志
            sysRequestLog = new SysRequestLog();
            sysRequestLog.setRowStatus(4);
            // 重试次数：默认0
            sysRequestLog.setRectryCount(0);
        } else {
            // 修改请求日志
            sysRequestLog = QSysRequestLog.sysRequestLog.selectOne(QSysRequestLog.sysRequestLog.fieldContainer()).where(QSysRequestLog.sysRequestLogId.eq$(requestLog.getRequestLogId())).execute();
            sysRequestLog.setRowStatus(16);
            // 重试次数：+1
            sysRequestLog.setRectryCount(sysRequestLog.getRectryCount() + 1);
        }
        // 设置属性值
        // 请求url
        sysRequestLog.setRequestUrl(requestLog.getRequestUrl());
        // 请求方式
        sysRequestLog.setMethod(requestLog.getMethod());
        // 参数过长，保存1000个字符
        int maxLength = 1000;
        // 请求头
        sysRequestLog.setHeaderParam(requestLog.getHeaderParam());
        // 避免参数过长
        if (StringUtils.isNotBlank(sysRequestLog.getHeaderParam()) && sysRequestLog.getHeaderParam().length() > maxLength) {
            sysRequestLog.setHeaderParam(sysRequestLog.getHeaderParam().substring(0, maxLength));
        }
        // 查询参数
        sysRequestLog.setQueryString(requestLog.getQueryString());
        // 请求体
        sysRequestLog.setRequestBody(requestLog.getRequestBody());
        // 请求来源ip
        sysRequestLog.setRemoteAddress(requestLog.getRemoteAddress());
        // 请求类型
        sysRequestLog.setType(requestLog.getType());
        // 状态码
        sysRequestLog.setStatusCode(requestLog.getStatusCode());
        // 业务是否成功
        sysRequestLog.setIsSuccess(requestLog.getIsSuccess());
        // 请求结果
        sysRequestLog.setResult(requestLog.getResult());
        // 避免参数过长
        if (StringUtils.isNotBlank(sysRequestLog.getResult()) && sysRequestLog.getResult().length() > maxLength) {
            sysRequestLog.setResult(sysRequestLog.getResult().substring(0, maxLength));
        }
        // 用户id，表结构待修改
        // 用户名，表结构待修改
        // 保存
        if (null == requestWrapper.getRequest().getAttribute("user")) {
            return;
        }
        QSysRequestLog.sysRequestLog.save(sysRequestLog);
    }

    /**
     * @return
     * @Author cec
     * @Description 刷新凭证
     * @Date 2021/11/30 14:45
     * @Param
     **/
    private void refreshCredential(HttpServletRequest request, HttpServletResponse response, User user) {
        long expireTime = user.getExpireTime();
        long currentTime = System.currentTimeMillis();
        long diffTime = expireTime - currentTime;
        if (diffTime > 0L && diffTime <= millisMinuteTen * CodeFinal.MILLIS_TO_SECOND) {
            ResultHelper<String> resultData = authCenterInterface.createCredential(user);
            if (resultData.getSuccess()) {
                String newCredential = resultData.getData();
                deliveryCredential(response, newCredential, String.valueOf(user.getUserId()));
            }
        }
    }

    /**
     * @return
     * @Author cec
     * @Description 投递凭证
     * @Date 2021/11/30 14:46
     * @Param
     **/
    private void deliveryCredential(HttpServletResponse response, String credential, String uid) {
        Cookie cookie = new Cookie(CodeFinal.ACCESS_TOKEN, credential);
        cookie.setPath("/");
        if (Objects.nonNull(webDomain) && !CodeFinal.NULL.equals(webDomain)) {
            cookie.setDomain(webDomain);
        }
        cookie.setHttpOnly(true);
        cookie.setMaxAge(cookieExpireSeconds - 10);
        cookie.setSecure(false);
        response.addCookie(cookie);

        Cookie uidCookie = new Cookie("uid", uid);
        //uidCookie.setHttpOnly(true);
        uidCookie.setPath("/");
        if (Objects.nonNull(webDomain) && !CodeFinal.NULL.equals(webDomain)) {
            uidCookie.setDomain(webDomain);
        }
        uidCookie.setMaxAge(cookieExpireSeconds - 10);
        uidCookie.setSecure(false);
        response.addCookie(uidCookie);
    }

    /**
     * @return
     * @Author cec
     * @Description 白名单比对
     * @Date 2021/11/30 14:46
     * @Param
     **/
    private boolean isInIgnorePathPattern(String path) {
        return Seq.seq(ignorePathPatternMatchers)
                .anyMatch(matcher -> matcher.match(path));
    }

}
