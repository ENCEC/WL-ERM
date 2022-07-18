package com.share.support.util;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * HttpServletRequest包装类
 * @author chenhy
 */
public class ResponseWrapper extends HttpServletResponseWrapper {

    private ByteArrayOutputStream bytes;
    private ServletOutputStream outputStream;
    private PrintWriter printWriter;

    public ResponseWrapper(HttpServletResponse response) throws UnsupportedEncodingException {
        super(response);
        this.bytes = new ByteArrayOutputStream();
        this.outputStream = new OutputStreamWrapper(bytes);
        this.printWriter = new PrintWriter(new OutputStreamWriter(bytes, this.getCharacterEncoding()));
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return this.outputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return this.printWriter;
    }

    @Override
    public void flushBuffer() throws IOException {
        if (outputStream != null) {
            outputStream.flush();
        }
        if (printWriter != null) {
            printWriter.flush();
        }
    }

    /**
     * 获取响应数据
     * @return 响应数据
     */
    public byte[] getResponseData() throws IOException {
        this.flushBuffer();
        return bytes.toByteArray();
    }

    /**
     * ServletOutputStream包装类
     */
    class OutputStreamWrapper extends ServletOutputStream {

        private ByteArrayOutputStream bytes;

        public OutputStreamWrapper(ByteArrayOutputStream bytes) {
            this.bytes = bytes;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
            // 不处理
        }

        @Override
        public void write(int b) throws IOException {
            bytes.write(b);
        }
    }
}
