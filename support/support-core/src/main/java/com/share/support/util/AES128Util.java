package com.share.support.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * @author tujx
 * @description AES-128-ECB加解密
 * @date 2020/11/05
 */
public class AES128Util {

    /**
     * 加密
     *
     * @param sSrc 需要解密的字符串
     * @param sKey 秘钥
     * @return
     * @throws Exception
     * @author tujx
     */
    public static String encrypt(String sSrc, String sKey) throws Exception {
        if (StringUtils.isBlank(sSrc)) {
            throw new RuntimeException("加密字符串不能为空");
        }
        if (StringUtils.isBlank(sKey)) {
            throw new RuntimeException("秘钥不能为空");
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            throw new RuntimeException("秘钥长度需要为16");
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        //"算法/模式/补码方式"
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        //此处使用BASE64做转码功能，同时能起到2次加密的作用。
        return new Base64().encodeToString(encrypted);
    }

    /**
     * 解密
     *
     * @param sSrc 需要解密的字符串
     * @param sKey 秘钥
     * @return
     * @author tujx
     */
    public static String decrypt(String sSrc, String sKey) throws Exception {
        if (StringUtils.isBlank(sSrc)) {
            throw new RuntimeException("解密字符串不能为空");
        }
        if (StringUtils.isBlank(sKey)) {
            throw new RuntimeException("秘钥不能为空");
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            throw new RuntimeException("秘钥长度需要为16");
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        //先用base64解密
        byte[] encrypted1 = new Base64().decode(sSrc);
        byte[] original = cipher.doFinal(encrypted1);
        String originalString = new String(original, "utf-8");
        return originalString;
    }

    /**
     * 生成秘钥
     *
     * @return
     * @author tujx
     */
    public static String getCKey() {
        String val = "";
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 16; i++) {
            String str = random.nextInt(2) % 2 == 0 ? "num" : "char";
            if ("char".equalsIgnoreCase(str)) {
                // 产生字母
                int nextInt = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (nextInt + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(str)) {
                // 产生数字
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
}
