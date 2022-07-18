package com.share.auth.center.constants;

/**
 * @Author:chenxf
 * @Description: 常量配置类
 * @Date: 17:40 2020/11/28
 * @Param:
 * @Return:
 *
 */
public class CodeFinal {

    private CodeFinal() {
    }

    // 登录类型MILLIS_MINUTE_TEN
    /**用户名*/
    public static final String LOGIN_WAY_USERNAME = "0";
    /** 手机*/
    public static final String LOGIN_WAY_PHONE = "1";
    /** 邮箱*/
    public static final String LOGIN_WAY_EMAIL = "2";
    /** 微信*/
    public static final String LOGIN_WAY_WECHAT = "3";
    /** QQ*/
    public static final String LOGIN_WAY_QQ = "4";
    /** 国家政务平台*/
    public static final String LOGIN_WAY_ENV = "5";


    /**字符串0*/
    public static final String ZERO_STRING = "0";
    /**字符串1*/
    public static final String ONE_STRING = "1";


    /** 客户端id */
    public static final String CLIENT_ID = "client_id";
    /** 返回路径 */
    public static final String RETURN_URL = "return_url";
    /** uid cookie的name */
    public static final String UID = "uid";
    /** session的用户信息 */
    public static final String USER = "user";
    /**JWT名称*/
    public static final String ACCESS_TOKEN_NAME = "access_token";
    /**refresh_token名称*/
    public static final String REFRESH_TOKEN_NAME = "refresh_token";
    /**error名称*/
    public static final String ERROR_NAME = "error";
    /**msg名称*/
    public static final String MSG_NAME = "msg";
    /**JWT名称*/
    public static final String USER_ID_NAME = "userId";
    /**JWT名称*/
    public static final String EXPIRE_IN_NAME = "expires_in";
    /**JWT名称*/
    public static final String JWT_NAME = "jwt";
    /**clientId名称*/
    public static final String CLIENT_ID_NAME = "clientId";
    /**role名称*/
    public static final String ROLE_NAME = "role";
    /** spring 安全上下文 */
    public static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";
    /**checkMoveId名称*/
    public static final String CHECK_MOVE_ID_NAME = "checkMoveId";


    /** 编码UTF-8 */
    public static final String ENCODING_UTF_8 = "UTF-8";



    /**授权请求成功代码码值*/
    public static final String AUTHORIZE_SUCCESS_CODE = "0";


    /**重定向代码*/
    public static final String REDIRECT_CODE = "302";


    /**
     * 默认角色
     */
    public static class DefaultRole {
        private DefaultRole(){}

        /**平台客服默认角色ID*/
        public static final Long ADMIN_ROLE_ID = 0L;
        /**平台客服默认角色代码*/
        public static final String ADMIN_ROLE_CODE = "SYSADMIN";
        /**平台客服默认角色代码*/
        public static final String ADMIN_ROLE_NAME = "PlateFormUser";


        /**其他用户默认角色ID*/
        public static final Long USER_ROLE_ID = 1L;
    }


    /**
     * 综合平台常量
     */
    public static class NtipConstant {
        private NtipConstant(){}

        /**授权码返回类型*/
        public static final String AUTHORIZE_RESPONSE_TYPE = "code";
        /**请求结果*/
        public static final String AUTHORIZE_RESULT_RET = "ret";
        /**请求结果错误代码*/
        public static final String AUTHORIZE_RESULT_CODE = "code";
        /**请求结果错误信息*/
        public static final String AUTHORIZE_RESULT_MSG = "msg";
        /**用户信息*/
        public static final String AUTHORIZE_RESULT_USER_INFO = "userinfo";
    }

    /**账号锁定提示信息*/
    public static final String ACCOUNT_LOCKED_MESSAGE = "账号已锁定，请稍后重试";
    /**账号锁定密码错误次数*/
    public static final Long ACCOUNT_LOCKED_PASSWORD_ERROR_TIMES = 5L;

    /**空判断*/
    public static final String NULL = "null";

    /**凭证有效期低于20分钟，刷新凭证 */
    public static final Long MILLIS_MINUTE_TEN = 3 * 60 * 1000L;
}
