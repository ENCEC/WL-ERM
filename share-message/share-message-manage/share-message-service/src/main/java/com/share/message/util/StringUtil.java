package com.share.message.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Rookie
 */
public class StringUtil {

    /**
     * 私有构造器
     *
     * @param
     * @return
     * @author huanghwh
     * @date 2021/5/13 上午10:54
     */
    private StringUtil() {
    }

    private static final Pattern MARCO_PATTERN = Pattern.compile("(\\{[^\\}]*\\})");

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

    /**
     * nily
     * 正则表达式提取 {} 中的内容
      * @param msg
     * @return
     */
    public static List<String> extractMessageByRegular(String msg){
        List<String> list = new ArrayList<>();
        Matcher m = MARCO_PATTERN.matcher(msg);
        while(m.find()){
            list.add(m.group().substring(1, m.group().length()-1));
        }
        return list;
    }
}
