package com.share.auth.util;

import java.util.regex.Pattern;

/**
 * @Author:chenxf
 * @Description: 密码相关工具类
 * @Date: 10:46 2021/1/22
 * @Param:
 * @Return:
 */
public class PasswordUtils {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(.{8,12})$");

    /**
     * 私有构造器
     *
     * @param
     * @return
     * @author huanghwh
     * @date 2021/4/30 上午11:22
     */
    private PasswordUtils() {
    }

    /**
     * @Author:chenxf
     * @Description: 密码强度校验，必须包含数字，大小写英文字母，特殊字符4种，长度8到12位
     * @Date: 10:48 2021/1/22
     * @Param: [password]
     * @Return:boolean
     */
    public static boolean matchersPassword(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }
}
