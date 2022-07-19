package com.share.file.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Rookie
 */
public class StringUtil {

    private StringUtil() {
    }

    public static String str2Null(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        return str;
    }

    public static String concat(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        return "%" + str + "%";
    }
}
