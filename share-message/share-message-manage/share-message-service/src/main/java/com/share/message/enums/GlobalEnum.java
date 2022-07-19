package com.share.message.enums;


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
        EXPORT("E", "出口");

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

    /**
     * 短信返回类型
     */
    public enum MsgMarcoApiReturnType {
        /**
         * 成功
         */
        SUCCESS("0", "成功"),
        /**
         * 失败
         */
        FAIL("1", "失败"),
        /**
         * 网关系统内部错误
         */
        GATEWAYFAIL("109001", "网关系统内部错误"),
        /**
         * 签名验证失败
         */
        SIGNATRUEFAIL("109002", "签名验证失败"),
        /**
         * systemCode为空
         */
        SYSTEMCODENULL("109003", "systemCode为空"),
        /**
         * systemCode无效
         */
        SYSTEMCODEINVALID("109004", "systemCode无效"),
        /**
         * keyId为空
         */
        KEYIDNULL("109005", "keyId为空"),
        /**
         * keyId无效
         */
        KEYIDINVALID("109006", "keyId无效"),
        /**
         * 必填参数为空
         */
        PARMSNULL("140001", "必填参数为空"),
        /**
         * 参数不符合规格
         */
        PARMSERROR("140002", "参数不符合规格");

        private String code;
        private String value;

        MsgMarcoApiReturnType(String code, String value) {
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

    /**
     * 接口错误码
     */
    public enum ErrorCode {

        /**
         * 成功
         */
        SUCCESS("0", "成功"),
        /**
         * 失败
         */
        FAIL("1", "失败"),
        /**
         * appKey认证异常
         */
        APP_KEY_AUTH_ERROR("109002", "appKey认证异常"),
        /**
         * 未找到appKey标识
         */
        APP_KEY_NOT_FIND("109003", "未找到appKey标识"),
        /**
         * 签名认证出现异常，摘要不匹配
         */
        SIGN_ERROR("109004", "签名认证出现异常，摘要不匹配"),
        /**
         * 未携带摘要信息，无法完成认证
         */
        NOT_TAKE_ABSTRACT("109005", "未携带摘要信息，无法完成认证"),
        /**
         * 系统异常
         */
        SYSTEM_ERROR("120005", "系统异常"),
        /**
         * http请求网络IO发送异常
         */
        HTTP_IO_ERROR("120003", "http请求网络IO发送异常"),
        /**
         * 服务未进行授权
         */
        SERVICE_NOT_AUTH("111003", "服务未进行授权"),
        /**
         * 必填参数为空
         */
        REQUIRED_PARAMS_EMPTY("140001", "必填参数为空"),
        /**
         * 参数不符合规格
         */
        PARAMS_NOT_CONFORM("140002", "参数不符合规格"),
        /**
         * 数据源接口异常
         */
        DATASOURCE_API_ERROR("130003", "数据源接口异常");

        /**
         * 错误码
         */
        private String resultCode;

        /**
         * 错误信息
         */
        private String resultMsg;

        ErrorCode(String resultCode, String resultMsg) {
            this.resultCode = resultCode;
            this.resultMsg = resultMsg;
        }

        public String getResultCode() {
            return resultCode;
        }


        public String getResultMsg() {
            return resultMsg;
        }

    }

    public enum ServiceType {
        /**
         * 有服务商
         */
        ISP("SEC", "有服务商"),
        /**
         * 无服务商
         */
        NO_ISP("NO_SEC", "无服务商");

        private String code;
        private String value;

        ServiceType(String code, String value) {
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
    public enum DataOperRowStatus {
        /**
         * ① rowStatus=2 标明数据未更改，不做任何操作，未指定时默认为该状态。
         * <p>
         * ② rowStatus=4 标明数据为新增记录
         * <p>
         * ② rowStatus=8 标明数据为删除记录
         * <p>
         * ② rowStatus=16标明数据为修改记录
         */
        DEFAULT("默认", 2),
        ADDITION("添加", 4),
        DELETE("删除", 8),
        UPDATE("更新", 16);

        private String name;
        private Integer code;

        DataOperRowStatus(String name, Integer code) {
            this.name = name;
            this.code = code;
        }

        public String getName() {
            return name;
        }


        public Integer getCode() {
            return code;
        }


    }
    /**
     *消息模板状态
     */
    public enum IsValid {

        VALID("启用", Boolean.TRUE),
        INVALID("禁用", Boolean.FALSE);

        private String name;
        private Boolean code;

        IsValid(String name, Boolean code) {
            this.name = name;
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public Boolean getCode() {
            return code;
        }

    }
    /**
     *消息发送状态
     */
    public enum MessageStatus {

        SUCCESS("成功", true),
        FAILED("失败", false);

        private String name;
        private Boolean code;

        MessageStatus(String name, Boolean code) {
            this.name = name;
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public Boolean getCode() {
            return code;
        }

    }
}
