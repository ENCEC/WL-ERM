package com.share.file.util.fastdfs;

import com.share.file.enums.GlobalEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * token返回结果
 * @author wangcl
 * @date 2020/11/28 11:35
 */
@Data
@AllArgsConstructor
public class FastDfsTokenResult {
    /**
     * 返回码
     */
    private String resultCode;
    /**
     * 返回信息
     */
    private String resultMsg;
    /**
     * 完整路径
     */
    private String data;

    /**
     * 失败结果封装
     * @param resultCode
     * @param resultMsg
     * @return
     */
    public static FastDfsTokenResult getFailureResult(String resultCode, String resultMsg, String data) {
        return new FastDfsTokenResult(resultCode, resultMsg, data);
    }

    /**
     * 成功结果封装
     * @return
     */
    public static FastDfsTokenResult getSuccessResult(String data) {
        return new FastDfsTokenResult(GlobalEnum.FileResultEnum.TOKEN_GET_SUCCESS.getCode(), GlobalEnum.FileResultEnum.TOKEN_GET_SUCCESS.getMessage(), data);
    }
}
