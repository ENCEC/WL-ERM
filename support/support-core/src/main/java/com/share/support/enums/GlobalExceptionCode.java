package com.share.support.enums;

/**
 * @author tujx
 * @description 异常编码枚举
 * @date 2020/12/24
 */
public enum GlobalExceptionCode {

    /**
     * 数据库操作异常
     */
    DATASOURCE_EXCEPTION("0100","数据库操作异常，请联系应用开发者处理！"),
    /**
     * IO异常
     */
    IO_EXCEPTION("0200","IO异常，请联系应用开发者处理！"),
    /**
     * 空指针异常
     */
    NULL_POINT_EXCEPTION("0300","请求的数据格式不符，请联系应用开发者处理！"),
    /**
     * 业务异常
     */
    RUNTIME_EXCEPTION("0400","运行异常，请联系应用开发者处理！"),
    /**
     * 乐观锁不一致异常
     */
    VERSION_EXCEPTION("0500","数据已更新，请刷新后再操作！"),
    /**
     * 未知异常
     */
    UNKNOWN_EXCEPTION("0000","服务器内部错误!");

    private String code;

    private String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    GlobalExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
