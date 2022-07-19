package com.share.file.util.fastdfs;

/**
 * FastDFS 上传下载时可能出现的一些异常信息
 * <p>
 *
 * @author wangcl
 * @name FastDfsException
 * @date 20201027
 */
public class FastDfsException extends Exception {

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误消息
     */
    private String message;

    public FastDfsException(){}

    public FastDfsException(String code, String message) {
        this.code = code;
        this.message = message;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
