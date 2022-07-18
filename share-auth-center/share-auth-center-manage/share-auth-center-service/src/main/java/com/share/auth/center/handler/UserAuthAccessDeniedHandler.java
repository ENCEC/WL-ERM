package com.share.auth.center.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.share.auth.center.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author:chenxf
 * @Description: 暂无权限返回拦截器
 * @Date: 17:41 2020/11/28
 * @Param: 
 * @Return:
 *
 */
@Component
public class UserAuthAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    private ObjectMapper objectMapper;
    /**
     * 暂无权限返回结果
     * @author zwq
     * @date 2020/4/4
     * @param request
     * @param response
     * @param exception
     * @return
     **/
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException {
        ResponseUtil.responseFailed(objectMapper, response,"未授权");
    }
}