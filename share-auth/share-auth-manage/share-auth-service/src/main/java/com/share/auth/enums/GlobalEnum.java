package com.share.auth.enums;

import com.share.auth.constants.CodeFinal;

import java.util.Objects;

/**
 * @author wangcl
 * @date 20201021
 * @description 通用枚举
 */
public class GlobalEnum {
    /**
     * 贸易类型
     */
    public enum TradeType {
        /**
         * 进口
         */
        IMPORT("I", "进口"),
        /**
         * 出口
         */
        EXPORT("E", "出口");;

        private String code;
        private String value;

        TradeType(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

    }
    public enum RoleEnum {
        /**
         * 公路院
         */
        GLY("GLY", "公路院组"),
        /**
         * 疫苗专家
         */
        YMZJ("YMZJ", "专家组");

        private String code;
        private String value;

        RoleEnum(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

//        public void setCode(String code) {
//            this.code = code;
//        }

        public String getValue() {
            return value;
        }

//        public void setValue(String value) {
//            this.value = value;
//        }
    }

    /**
     * 物流提供方类型和角色互相转换
     */
    public enum SupplyTypeConvertRoleEnum{
        /**
         * S1-S6  - 承运商角色
         */
        OTHER(null, CodeFinal.CYS),
        /**
         * 港口-港口角色
         */
        S7("S7", "GKA"),
        /**
         * 仓库-仓库角色
         */
        S8("S8", "CKA"),
        /**
         * 其他物流服务商-无
         */
        S9("S9", null);


        private String type;

        private String role;

        SupplyTypeConvertRoleEnum(String type, String role) {
            this.type = type;
            this.role = role;
        }

        public String getType() {
            return type;
        }

        public String getRole() {
            return role;
        }

        public static String getRoleByType(String type) {
            for (SupplyTypeConvertRoleEnum convertRoleEnum : SupplyTypeConvertRoleEnum.values()) {
                if (Objects.equals(convertRoleEnum.getType(), type)) {
                    return convertRoleEnum.getRole();
                }
            }
            // 默认返回承运商
            return SupplyTypeConvertRoleEnum.OTHER.getType();
        }

    }

    /**
     * 操作类型枚举
     */
    public enum OptionTypeEnum {
        /**
         * 新增
         */
        INSERT("1", "新增"),
        /**
         * 修改
         */
        UPDATE("2", "修改"),
        /**
         * 删除
         */
        DELETE("3", "删除");

        private String code;

        private String name;

        OptionTypeEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        /**
         * 根据code查询name
         * @param code code值
         * @return name值
         */
        public static String getNameByCode(String code) {
            for (OptionTypeEnum optionTypeEnum : OptionTypeEnum.values()) {
                if (Objects.equals(optionTypeEnum.getCode(), code)) {
                    return optionTypeEnum.getName();
                }
            }
            return null;
        }

    }

    /**
     * 组织状态枚举
     */
    public enum OrgStatusEnum {
        /**
         * 正常-启用
         */
        NORMAL("0", "正常", true),
        /**
         * 无效-禁用
         */
        INVALID("1", "无效", false);

        private String code;
        private String name;
        private Boolean invalid;

        OrgStatusEnum(String code, String name, Boolean invalid) {
            this.code = code;
            this.name = name;
            this.invalid = invalid;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public Boolean getInvalid() {
            return invalid;
        }

        /**
         * 根据code查询name
         * @param code code值
         * @return name
         */
        public static String getNameByCode(String code) {
            for (OrgStatusEnum orgStatusEnum : OrgStatusEnum.values()) {
                if (Objects.equals(orgStatusEnum.getCode(), code)) {
                    return orgStatusEnum.getName();
                }
            }
            return null;
        }

        /**
         * 根据code查询禁用/启用状态
         * @param code code值
         * @return 禁用/启用状态
         */
        public static Boolean getInvalidByCode(String code) {
            for (OrgStatusEnum orgStatusEnum : OrgStatusEnum.values()) {
                if (Objects.equals(orgStatusEnum.getCode(), code)) {
                    return orgStatusEnum.getInvalid();
                }
            }
            return false;
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
        STAGE_ONE("3", "一期数据"),
        /**
         * 客服新增
         */
        CUSTOMER_SERVICE_ADD("4", "客服新增");

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
         * 企业用户
         */
        COMPANY_USER("1", "企业用户"),
        /**
         * 企业管理员
         */
        COMPANY_ADMIN("2", "企业管理员"),
        /**
         * 国交管理员
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
     * 员工状态枚举
     */
    public enum StaffStatusEnum {
        /**
         * 正常-启用
         */
        NORMAL("0", "在职", true),
        /**
         * 无效-禁用
         */
        INVALID("1", "离职", false),

        /**
         * 无效-禁用
         */
        LOCK("2", "锁定", null);

        private String code;
        private String name;
        private Boolean invalid;

        StaffStatusEnum(String code, String name, Boolean invalid) {
            this.code = code;
            this.name = name;
            this.invalid = invalid;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public Boolean getInvalid() {
            return invalid;
        }

        /**
         * 根据code查询name
         * @param code code值
         * @return name
         */
        public static String getNameByCode(String code) {
            for (StaffStatusEnum staffStatusEnum : StaffStatusEnum.values()) {
                if (Objects.equals(staffStatusEnum.getCode(), code)) {
                    return staffStatusEnum.getName();
                }
            }
            return null;
        }

        /**
         * 根据code查询禁用/启用状态
         * @param code code值
         * @return 禁用/启用状态
         */
        public static Boolean getInvalidByCode(String code) {
            for (StaffStatusEnum staffStatusEnum : StaffStatusEnum.values()) {
                if (Objects.equals(staffStatusEnum.getCode(), code)) {
                    return staffStatusEnum.getInvalid();
                }
            }
            return false;
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

    /**承运商企业的类型*/
    public enum CarrierType {
        /**注册企业*/
        REGISTER("0"),
        /**非注册企业*/
        NO_REGISTER("1");

        private String code;

        CarrierType(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    /**用户身份类型*/
    public enum UserIdentity {
        /**普通用户*/
        ORDINARY("1"),
        /**企业管理员*/
        COMPANY_ADMIN("2"),
        /**平台客服*/
        ADMIN("3"),
        /**国交管理员*/
        IMPT_ADMIN("4");

        private String code;

        UserIdentity(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    /**管理员按钮显示枚举*/
    public enum ShowManagerButton {
        /**企业审核中*/
        COMPANY_NO_AUDIT(0),
        /**未申请企业管理员*/
        COMPANY_ADMIN_NO_APPLY(1),
        /**企业管理员申请审核中*/
        COMPANY_ADMIN_NO_AUDIT(2),
        /**企业管理员申请通过*/
        COMPANY_ADMIN_AUDIT_PASS(3);

        private Integer code;

        ShowManagerButton(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }


    /**
     * 人员岗位代码枚举
     */
    public enum StaffDutyCodeEnum {
        /**
         * 业务负责人
         */
        BUSINESS_LEADER("1", "业务负责人"),
        /**
         * 业务负责人
         */
        COMPANY_LEADER("2", "企业负责人"),
        /**
         * 业务负责人
         */
        OTHER("3", "业务负责人");

        private String code;

        private String name;

        StaffDutyCodeEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public static String getNameByCode(String code) {
            for (StaffDutyCodeEnum codeEnum : StaffDutyCodeEnum.values()) {
                if (Objects.equals(codeEnum.getCode(),code)) {
                    return codeEnum.getName();
                }
            }
            return null;
        }

    }

}
