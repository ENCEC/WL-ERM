package com.share.file.constants;

/**
 * @author wangcl
 * @date 20201021
 * @description 通用常量类
 */
public class GlobalConstant {
    /**
     * rowStatus 状态码
     * nily
     */
    public static final int SAVE = 4;
    public static final int DELETE = 8;
    public static final int UPDATE = 16;
    public static final int RESET = 2;

    public static final String SESSION_KEY_APP_CODE = "msg_application_code";

    /**
     * httpSecretKey,用于生成token
     */
    public static final String HTTP_SECRET_KEY = "fastdfs.http_secret_key";
    /**
     * 完整路径
     */
    public static final String HTTP_NGINX_URL = "fastdfs.nginx.http_url";

    private GlobalConstant() {
    }
}
