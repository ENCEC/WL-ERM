package com.share.message.filter;

import com.share.auth.center.api.SysPublishInterface;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * @author wangzicheng
 * @description
 * @date 2021年04月07日 19:27
 */
@WebFilter(filterName = "sysPublishFilter", urlPatterns = "/*")
@Slf4j
public class SysPublishFilter implements Filter {

    @Autowired
    private SysPublishInterface sysPublishInterface;

    /**
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @author wangzicheng
     * @date 2021/4/7 20:18
     * 拦截所有请求
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
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
