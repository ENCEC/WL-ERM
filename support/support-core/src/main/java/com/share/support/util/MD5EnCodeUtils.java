package com.share.support.util;

import org.springframework.util.DigestUtils;

/**
 * @author chenxf
 * @date 2021-01-09 18:51
 */
public class MD5EnCodeUtils {

    /**
     * 加密密码
     * @param password 原始密码
     * @return 加密后的密码
     */
    public static String MD5EnCode(String password){
        return  DigestUtils.md5DigestAsHex(password.getBytes());
    }

    /**
     * 二次加密密码
     * @param password 原始密码
     * @return 加密后的密码
     */
    public static String encryptionPassword(String password) {
        password = MD5EnCodeUtils.MD5EnCode(password).substring(8, 24);
        // 密码二次加密
        return MD5EnCodeUtils.MD5EnCode(password);
    }
}
