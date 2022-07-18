package com.share.auth.center.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.share.auth.center.constants.CodeFinal;
import com.share.auth.center.model.entity.UemLog;
import com.share.auth.center.model.querymodels.QUemLog;
import com.share.auth.center.service.UemUserService;
import com.share.auth.center.util.RequestUtil;
import com.share.auth.center.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @Author:chenxf
 * @Description: 登录失败处理类
 * @Date: 17:41 2020/11/28
 * @Param: 
 * @Return:
 *
 */
@Slf4j
@Component
public class UserLoginFailureHandler implements AuthenticationFailureHandler {
    @Autowired
    private ObjectMapper objectMapper;
    /**
     * 失败提示信息
     */
    private static final String FAIL_MESSAGE = "【登录失败】";

    @Autowired
    private UemUserService uemUserService;

    /**
     * 登录失败返回结果
     * @author zwq
     * @date 2020/4/4
     * @param request
     * @param response
     * @param exception
     * @return
     **/
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        UemLog uemLog = new UemLog();
        uemLog.setLogDate(new Date());
        uemLog.setIpAddress(request.getRemoteAddr());
        uemLog.setBrowser(RequestUtil.getBrowser(request));
        uemLog.setWay(CodeFinal.ZERO_STRING);
        uemLog.setEquipment(CodeFinal.ONE_STRING);
        uemLog.setLoginType(CodeFinal.ZERO_STRING);
        uemLog.setResult(CodeFinal.ONE_STRING);
        uemLog.setRowStatus(4);
        QUemLog.uemLog.save(uemLog);
        // 这些对于操作的处理类可以根据不同异常进行不同处理
        if (exception instanceof UsernameNotFoundException){
            log.info(FAIL_MESSAGE+exception.getMessage());
            ResponseUtil.responseFailed(objectMapper, response,"用户名不存在");
        }
        if (exception instanceof LockedException){
            log.info(FAIL_MESSAGE+exception.getMessage());
            ResponseUtil.responseFailed(objectMapper, response,"用户被冻结");
        }
        // 锁定异常处理
        if (exception.getCause() != null && exception.getCause() instanceof LockedException){
            log.info(FAIL_MESSAGE+exception.getMessage());
            ResponseUtil.responseFailed(objectMapper, response, exception.getCause().getMessage());
        }
        if (exception instanceof BadCredentialsException){
            log.info(FAIL_MESSAGE+exception.getMessage());
            this.lockedUser(request);
            ResponseUtil.responseFailed(objectMapper, response,"账号或密码输入错误");
        }
        ResponseUtil.responseFailed(objectMapper, response,"登录失败");
    }

    /**
     * 锁定用户
     * @param request 请求
     */
    private void lockedUser(HttpServletRequest request) {
        // 登录用户名
        String username = request.getParameter("username");
        // 判空
        if (StringUtils.isNotBlank(username)) {
            // 锁定用户
            uemUserService.lockedUser(username);
        }
    }

}