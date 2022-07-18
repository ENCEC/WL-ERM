package com.share.support.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.share.support.constants.UserConstant;
import com.share.support.model.RequestLog;
import com.share.support.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 日志工具类
 * @author chenhy
 * @date 2021-7-5
 */
@Slf4j
public class LogUtil {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 保存请求日志
     * @param requestWrapper 请求包装类
     * @param responseWrapper 响应包装类
     * @param servletResponse 请求
     * @return 返回请求日志信息
     * @throws IOException IO异常
     */
    public static RequestLog getRequestLog(RequestWrapper requestWrapper, ResponseWrapper responseWrapper, ServletResponse servletResponse) throws IOException {
        // 请求日志
        RequestLog requestLog = new RequestLog();
        // 请求头参数
        Map<String, String> headMap = new HashMap<>(16);
        Enumeration<String> headerNames = requestWrapper.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String value = requestWrapper.getHeader(name);
            // 判断请求头是否存在请求日志id，若存在，获取请求日期
            if (StringUtils.equalsAnyIgnoreCase(name, "requestLogId") && StringUtils.isNotBlank(value)){
                requestLog.setRequestLogId(Long.valueOf(value));
                continue;
            }
            headMap.put(name, value);
        }

        // 请求url，添加/share-auth
        String requestUrl = requestWrapper.getRequestURL().toString();
        requestLog.setRequestUrl(requestUrl);
        // 请求方式
        requestLog.setMethod(requestWrapper.getMethod());
        // 请求头
        String headParam = OBJECT_MAPPER.writeValueAsString(headMap);
        requestLog.setHeaderParam(headParam);
        // 查询参数
        requestLog.setQueryString(requestWrapper.getQueryString());
        // 请求体
        byte[] requestData = requestWrapper.getRequestData();
        if (requestData != null) {
            requestLog.setRequestBody(new String(requestData));
        }
        // 请求来源ip
        requestLog.setRemoteAddress(requestWrapper.getRemoteAddr());
        // 请求类型：固定为1：接收外部请求
        requestLog.setType("1");
        // 状态码
        requestLog.setStatusCode(responseWrapper.getStatus() + "");
        // 响应数据
        byte[] responseData = responseWrapper.getResponseData();
        // 响应数据返回到响应流
        responseWrapper.setContentLength(-1);
        ServletOutputStream outputStream = servletResponse.getOutputStream();
        outputStream.write(responseData);
        outputStream.flush();
        // 解析返回数据
        String result = new String(responseData);
        JsonNode jsonNode = OBJECT_MAPPER.createObjectNode();
        try {
            jsonNode = OBJECT_MAPPER.readTree(result);
        } catch (Exception e) {
            log.error("objectMapper.readTree()异常：{}", e.getMessage(), e);
        }
        // 业务是否成功，根据success或status判断请求是否成功
        JsonNode successNode = null;
        JsonNode statusNode = null;
        // 判空
        if (jsonNode != null) {
            successNode = jsonNode.get("success");
            statusNode = jsonNode.get("status");
        }
        // 判空
        if (successNode != null) {
            boolean b = successNode.asBoolean(false);
            requestLog.setIsSuccess(b);
        }
        // 判空
        if (statusNode != null) {
            boolean status = statusNode.asBoolean(false);
            requestLog.setIsSuccess(status);
        }
        // 判空，且未根据statusNode设置请求是否成功
        if (statusNode != null && Objects.isNull(requestLog.getIsSuccess())) {
            String successCode = "1";
            String status = statusNode.asText();
            requestLog.setIsSuccess(Objects.equals(status, successCode));
        }
        // 请求结果
        requestLog.setResult(result);
        // 用户信息
        HttpSession session = requestWrapper.getSession();
        User user = (User) session.getAttribute(UserConstant.USER);
        // 判空
        if (user != null) {
            requestLog.setUemUserId(user.getUemUserId());
            requestLog.setAccount(user.getAccount());
        }
        // 返回日志信息
        return requestLog;
    }
}
