package com.share.auth.constants;

/**
 * @author wangcl
 * @date 20201021
 * @description 通用常量类
 */
public class GlobalConstant {

    private GlobalConstant(){}

    /**
     * 前缀-国家综合交通运输信息平台
     */
    public static final String PREFIX_GJIM = "GJIM_";


    /**
     * 校验-StaffPhone值不唯一
     */
    public static final String VALID_STAFF_PHONE_DUPLICATE = "StaffPhone值不唯一";

    /**
     * 校验-StaffMail值不唯一
     */
    public static final String VALID_STAFF_MAIL_DUPLICATE = "StaffMail值不唯一";

    /**
     * 占位符-数据源
     */
    public static final String PLACEHOLDER_DATASOURCE = ":dataSource";

    /**
     * 占位符-用户来源
     */
    public static final String PLACEHOLDER_SOURCE = ":source";

    /**
     * 别名-mobile
     */
    public static final String ALIAS_MOBILE = "mobile";

    /**
     * 别名-applicationName
     */
    public static final String ALIAS_APPLICATION_NAME = "applicationName";



    /**
     * 调用公共服务接口返回值
     */
    public static class ApiConstant{
        /**
         * 成功
         */
        public static final String SUCCESS = "success";
        /**
         * 数据
         */
        public static final String DATA="data";
    }

    /**cookie中应用id字段名称*/
    public static final String COOKIE_CLIENT_ID = "client_id";

    /**随机字母*/
    public static final String RANDOM_LETTER = "ABCDEFGHIJKLMNPQRSTUVWXYZ";
    /**随机数字*/
    public static final String RANDOM_NUMBER = "123456789";
    /**绑定邮箱每日发送邮件最大次数前缀*/
    public static final String BIND_EMAIL_DAILY_SEND_EMAIL_MAX_TIMES_KEY_PRE = "BIND_EMAIL_DAILY_SEND_EMAIL_MAX_TIMES_";
    /**绑定邮箱每日发送邮件最大次数*/
    public static final int BIND_EMAIL_DAILY_SEND_EMAIL_MAX_TIMES = 10;

    /**
     * 调用公共服务接口返回值
     */
    public static class GeneralConstant {
        private GeneralConstant() {
        }

        /**
         * 编码
         */
        public static final String CODE = "code";
        public static final String COUNT = "count";
        public static final String SUCCESS = "200";
        public static final String UN_KNOWN = "unknown";
    }

}
