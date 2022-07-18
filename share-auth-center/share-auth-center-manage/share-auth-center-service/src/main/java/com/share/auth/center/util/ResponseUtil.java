package com.share.auth.center.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.share.auth.center.model.dto.Result;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * @Author:chenxf
 * @Description: 通过流的方式返回接口工具类
 * @Date: 16:09 2021/1/18
 * @Param: 
 * @Return:
 *
 */
public class ResponseUtil {
    private ResponseUtil() {
        throw new IllegalStateException("Utility class");
    }
    /**
     * 通过流写到前端
     *
     * @param objectMapper 对象序列化
     * @param response
     * @param msg          返回信息
     * @param httpStatus   返回状态码
     * @throws IOException
     */
    public static void responseWriter(ObjectMapper objectMapper, HttpServletResponse response, String msg, int httpStatus) throws IOException {
        Result<Object> result = Result.of(null, httpStatus, msg);
        responseWrite(objectMapper, response, result);
    }

    /**
     * 通过流写到前端
     * @param objectMapper 对象序列化
     * @param response
     * @param obj
     */
    public static void responseSucceed(ObjectMapper objectMapper, HttpServletResponse response, Object obj) throws IOException {
        Result<Object> result = Result.succeed(obj);
        responseWrite(objectMapper, response, result);
    }

    /**
     * 通过流写到前端
     * @param objectMapper
     * @param response
     * @param msg
     * @throws IOException
     */
    public static void responseFailed(ObjectMapper objectMapper, HttpServletResponse response, String msg) throws IOException {
        Result<Object> result = Result.failed(msg);
        responseWrite(objectMapper, response, result);
    }

    private static void responseWrite(ObjectMapper objectMapper, HttpServletResponse response, Result<Object> result) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try (
                Writer writer = response.getWriter()
        ) {
            writer.write(objectMapper.writeValueAsString(result));
            writer.flush();
        }
    }
}
