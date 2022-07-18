package com.share.auth.center.enums;

/**
 * 枚举类
 * @author chenhy
 * @date 2021-05-17
 */
public class GlobalEnum {

    /**
     * 认证报错枚举
     */
    public enum AuthErrorEnum {
        /**
         * 账号不存在
         */
        UNKNOWN("0", "未知错误"),
        /**
         * 账号不存在
         */
        ACCOUNT_NOT_EXIST("1", "账号不存在"),
        /**
         * 账号所属组织被禁用
         */
        ACCOUNT_COMPANY_INVALID("2", "账号所属组织被禁用"),
        /**
         * 账号被禁用
         */
        ACCOUNT_INVALID("3", "账号被禁用"),
        /**
         * 账号没有绑定企业
         */
        ACCOUNT_UNBIND_COMPANY("4", "账号没有绑定企业"),
        /**
         * 应用被禁用
         */
        APPLICATION_INVALID("5", "应用被禁用"),
        /**
         * 角色被禁用
         */
        ROLE_INVALID("6", "角色被禁用"),
        /**
         * 账号数据错误
         */
        ACCOUNT_ERROR("7", "账号数据错误"),
        /**
         * 应用编码为空
         */
        APPLICATION_NULL("8", "应用编码为空"),
        /**
         * 应用不存在
         */
        APPLICATION_NOT_EXIST("9", "应用不存在"),
        /**
         * 应用编码错误
         */
        APPLICATION_ERROR("10", "应用编码错误"),

        /**
         * 访问受限
         */
        ACCESS_LIMIT("11", "访问受限");

        private String code;
        private String name;

        AuthErrorEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 用户来源
     */
    public enum UserSource {
        /**
         * 用户注册
         */
        REGISTER("0", "用户注册"),
        /**
         * 管理员新增
         */
        ADMIN_ADD("1", "管理员新增"),
        /**
         * 国家综合交通运输信息平台
         */
        NTIP("2", "国家综合交通运输信息平台"),
        /**
         * 一期数据
         */
        STAGE_ONE("3", "一期数据");
        private String code;
        private String name;

        UserSource(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }


    /**
     * 用户类型
     */
    public enum UserType {
        /**
         * 普通用户
         */
        GENERAL_USER("0", "普通用户"),
        /**
         * 管理员新增
         */
        COMPANY_USER("1", "企业用户"),
        /**
         * 国家综合交通运输信息平台
         */
        COMPANY_ADMIN("2", "企业管理员"),
        /**
         * 国家综合交通运输信息平台
         */
        IMPT_ADMIN("3", "国交管理员");

        private String code;
        private String name;

        UserType(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 审核状态枚举
     */
    public enum AuditStatusEnum {
        /**
         * 未审核
         */
        NO_AUDIT("0", "未审核"),
        /**
         * 审核通过
         */
        AUDIT_PASS("1", "审核通过"),
        /**
         * 审核拒绝
         */
        AUDIT_REJECT("2", "审核拒绝");

        private String code;

        private String name;

        AuditStatusEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    /**资源类型枚举*/
    public enum ResourceTypeEnum {
        /**页面*/
        PAGE(1),
        /**按钮*/
        BUTTON(2),
        /**接口*/
        INTERFACE(3);

        private Integer code;

        ResourceTypeEnum(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }

}
