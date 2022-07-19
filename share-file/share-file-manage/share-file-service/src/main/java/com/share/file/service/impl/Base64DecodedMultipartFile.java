package com.share.file.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * base64加密文件转成MultipartFile
 * @author wangcl
 * @date 2020/11/25
 */
@Slf4j
public class Base64DecodedMultipartFile implements MultipartFile {
    private final byte[] imgContent;
    private final String header;

    public Base64DecodedMultipartFile(byte[] imgContent, String header) {
        this.imgContent = imgContent;
        this.header = header.split(";")[0];
    }

    @Override
    public String getName() {
        // 使用SecureRandom比Math.random()线程更安全
        return header.split("/")[1];
    }

    @Override
    public String getOriginalFilename() {
        // 使用SecureRandom比Math.random()线程更安全
        return header.split("/")[1];
    }

    @Override
    public String getContentType() {
        return header.split(":")[1];
    }

    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return imgContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(File dest) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(dest)) {
            fileOutputStream.write(imgContent);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * base64转成MultipartFile
     * @param base64
     * @return
     */
    public static MultipartFile base64ToMultipart(String base64) {
        try {
            String[] baseStrs = base64.split(",");
            byte[] b;
            b = Base64.decodeBase64(baseStrs[1]);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            return new Base64DecodedMultipartFile(b, baseStrs[0]);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
