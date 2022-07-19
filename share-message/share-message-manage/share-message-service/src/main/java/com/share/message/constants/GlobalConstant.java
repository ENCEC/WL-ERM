package com.share.message.constants;

import java.util.regex.Pattern;

/**
 * @author wangcl
 * @date 20201021
 * @description 通用常量类
 */
public class GlobalConstant {

    /**
     * 私有构造器
     * @param
     * @return
     * @author huanghwh
     * @date 2021/5/12 下午2:59
     */
    private GlobalConstant() {}

    /**
     * rowStatus 状态码
     */
    public static final int SAVE = 4;
    public static final int DELETE = 8;
    public static final int UPDATE = 16;
    public static final int RESET = 2;

    /**
     * 邮件附件类型-xlsx
     */
    public static final String EMAIL_ATTACHMENT_TYPE_XLSX = "xlsx";
    /**
     * 邮件附件类型-word
     */
    public static final String EMAIL_ATTACHMENT_TYPE_WORD = "word";
    /**
     * 邮件附件类型-pdf
     */
    public static final String EMAIL_ATTACHMENT_TYPE_PDF = "pdf";
    /**
     * 邮件附件类型-png
     */
    public static final String EMAIL_ATTACHMENT_TYPE_PNG = "png";

    /**
     * 当前系统编码在session中的key
     */
    public static final String SESSION_KEY_APP_CODE = "msg_application_code";

    /**
     * 邮件格式正则
     */
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?");

}
