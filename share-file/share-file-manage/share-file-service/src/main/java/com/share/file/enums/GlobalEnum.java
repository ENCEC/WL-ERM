package com.share.file.enums;

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
     *文件上传返回状态
     * nily modify
     */
	public enum FileResultEnum{
        /**
         * 成功【查询成功】
         */
        FILESLIST_SUCCESS("0","成功【查询成功】"),
        /**
         * 失败【查询失败】
         */
        FILESLIST_FAILURE("1","失败【查询失败】"),
        /**
         * 成功【获取成功】
         */
        TOKEN_GET_SUCCESS("0","成功【获取成功】"),
        /**
         * 失败【获取失败】
         */
        TOKEN_GET_FAILURE("1","失败【获取失败】"),
        /**
         * 成功【上传成功】
         */
		UPLOAD_SUCCESS("0","成功【上传成功】"),
        /**
         * 失败【上传失败】
         */
		UPLOAD_FAILURE("1","失败【上传失败】"),
        /**
         * 成功【下载成功】
         */
		DOWNLOAD_SUCCESS("0","成功【下载成功】"),
        /**
         * 失败【下载失败】
         */
		DOWNLOAD_FAILURE("1","失败【下载失败】"),
        /**
         * 成功【删除成功】
         */
        DELETE_SUCCESS("0","成功【删除成功】"),
        /**
         * 失败【删除失败】
         */
        DELETE_FAILURE("1","失败【删除失败】"),
        /**
         * 网关系统内部错误
         */
		GATEWAY_INTERNAL_EXCEPTION("109001","网关系统内部错误"),
        /**
         * 签名验证失败
         */
		SIGNATURE_VERIFICATION_EXCEPTION("109002","签名验证失败"),
        /**
         * systemCode为空
         */
		SYSTEMCODE_EMPTY_EXCEPTION("109003","systemCode为空"),
        /**
         * systemCode无效
         */
		SYSTEMCODE_INVAILD_EXCEPTION("109004","systemCode无效"),
        /**
         * keyId为空
         */
		KEYID_EMPTY_EXCEPTION("109005","keyId为空"),
        /**
         * keyId无效
         */
		KEYID_INVAILD_EXCEPTION("109006","keyId无效"),
        /**
         * 必填参数为空
         */
        PARAMETER_EMPTY_EXCEPTION("140001", "必填参数为空"),
        /**
         * 参数不符合规格
         */
        PARAMETER_INCONFORMITY_EXCEPTION("140002", "参数不符合规格"),
        /**
         * 查找不到相应文件
         */
        FILE_NOT_FOUND("140003", "查找不到相应文件");
        private String code;
        private String msg;

        FileResultEnum(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return this.code;
		}

		public String getMessage(){
			return this.msg;
		}
	}


    /**
     * 接口错误码
     */
    public enum InterfaceErrorCode {

        /**
         * 成功
         */
        SUCCESS("0", "成功"),
        /**
         * 失败
         */
        FAIL("1", "失败"),
        /**
         * 必填参数为空
         */
        REQUIRED_PARAMS_EMPTY("140001", "必填参数为空"),
        /**
         * 参数不符合规格
         */
        PARAMS_NOT_CONFORM("140002", "参数不符合规格");

        /**
         * 错误码
         */
        private String resultCode;

        /**
         * 错误信息
         */
        private String resultMsg;

        InterfaceErrorCode(String resultCode, String resultMsg) {
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
}
