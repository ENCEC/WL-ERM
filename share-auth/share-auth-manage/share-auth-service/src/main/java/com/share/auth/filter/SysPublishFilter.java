package com.share.auth.filter;

import com.share.auth.center.api.SysPublishInterface;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 系统发布过滤器，校验系统是否在发布中
 * @author wangzicheng
 * @description
 * @date 2021年04月07日 19:27
 */
//@WebFilter(filterName = "sysPublishFilter", urlPatterns = "/*")
@Slf4j
public class SysPublishFilter implements Filter {


    @Autowired
    private SysPublishInterface sysPublishInterface;

    /**
     * @author wangzicheng
     * @date 2021/4/13 20:38
     * 系统验证登录地址
     */
    private String validateUrl = "/system/security/validate";

    /**
     * 拦截所有请求，校验系统是否在发布中，若处于发布中返回发布时间
     * @param servletRequest 请求
     * @param servletResponse 响应
     * @param filterChain 过滤器调用链
     * @author wangzicheng
     * @date 2021/4/7 20:18
     * @throws IOException IO异常
     * @throws ServletException servlet异常
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();
        if (!StringUtils.isEmpty(uri) && validateUrl.equals(uri)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String times = sysPublishInterface.getTimes();
        if(StringUtils.isNotEmpty(times)){
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.write("{\"times\":\"" + times + "\"}");
            out.flush();
            out.close();
            return;
        }
        filterChain.doFilter(servletRequest, response);
    }

}
