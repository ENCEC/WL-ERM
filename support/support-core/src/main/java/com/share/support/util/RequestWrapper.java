package com.share.support.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;

/**
 * HttpServletRequest包装类
 * @author chenhy
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    private byte[] body;

    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        // 判断是否表单请求
        String contentType = request.getContentType();
        boolean isForm = StringUtils.isNotBlank(contentType) && (contentType.contains("multipart/form-data") || contentType.contains("x-www-form-urlencoded"));
        if (isForm) {
            // 表单请求，获取param参数
            StringBuilder bodyString = new StringBuilder();
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                bodyString.append("&").append(request.getParameter(parameterNames.nextElement()));
            }
            // 去除&
            if (bodyString.length() > 0) {
                body = bodyString.substring(1).getBytes();
            }
        } else {
            // 其他请求，读取流数据
            try (BufferedInputStream bis = new BufferedInputStream(request.getInputStream());
                 ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = bis.read(buffer)) > 0) {
                    baos.write(buffer, 0, len);
                }
                body = baos.toByteArray();
            }
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        ByteArrayInputStream bytes = new ByteArrayInputStream(body);
        return new InputStreamWrapper(bytes);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    public byte[] getRequestData() {
        return body;
    }

    /**
     * ServletInputStream包装类
     */
    class InputStreamWrapper extends ServletInputStream {

        private ByteArrayInputStream bytes;

        public InputStreamWrapper(ByteArrayInputStream bytes) {
            this.bytes = bytes;
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            // 不处理
        }

        @Override
        public int read() throws IOException {
            return bytes.read();
        }
    }

}
