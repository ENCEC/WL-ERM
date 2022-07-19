package com.share.message.enums;

/**
 * @author tujx
 * @description 数据长度校验枚举
 * @date 2021/03/23
 */
public class DataLengthEnum {

    /**
     * 邮件发送配置
     */
    public enum EmailConfig {
        /**
         * 邮箱配置名称
         */
        EMAIL_CONFIG_NAME(100, "邮箱配置名称"),
        /**
         * 服务器地址
         */
        HOST(64, "服务器地址"),
        /**
         * 服务器端口
         */
        PORT(9, "服务器端口"),
        /**
         * 发送方邮箱
         */
        EMAIL_ADDRESS(64, "发送方邮箱"),
        /**
         * 发送方邮箱密码
         */
        EMAIL_PASSWORD(25, "发送方邮箱密码"),
        /**
         * 邮箱用户名
         */
        EMAIL_USER(64, "邮箱用户名");

        private Integer length;
        private String dataName;

        EmailConfig(Integer length, String dataName) {
            this.length = length;
            this.dataName = dataName;
        }

        public Integer getLength() {
            return length;
        }


        public String getDataName() {
            return dataName;
        }


        public String getTipsStr(){
            return dataName + "长度不能超过" + length;
        }
    }

    /**
     * 邮件发送配置
     */
    public enum EmailTemplate {
        /**
         * 模板名称
         */
        EMAIL_TEMPLATE_NAME(100, "模板名称"),
        /**
         * 模板描述
         */
        DESCRIPTION(500, "模板描述"),
        /**
         * 模板主题
         */
        SUBJECT(200, "模板主题"),
        /**
         * 模板内容
         */
        CONTENT(4000, "模板内容"),
        /**
         * 报表路径
         */
        ATTACHMENT_URL(100, "报表路径");

        private Integer length;
        private String dataName;

        EmailTemplate(Integer length, String dataName) {
            this.length = length;
            this.dataName = dataName;
        }

        public Integer getLength() {
            return length;
        }


        public String getDataName() {
            return dataName;
        }


        public String getTipsStr(){
            return dataName + "长度不能超过" + length;
        }
    }
}
