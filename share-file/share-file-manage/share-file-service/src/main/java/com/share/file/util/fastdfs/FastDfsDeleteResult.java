package com.share.file.util.fastdfs;

import com.share.file.enums.GlobalEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 删除文件结果封装
 * @author wangcl
 * @date 20201103
 */
@Data
@AllArgsConstructor
public class FastDfsDeleteResult {
	/**
	 * 返回码
	 */
	private String resultCode;
	/**
	 * 返回信息
	 */
	private String resultMsg;

	/**
	 * 失败结果封装
	 * @param resultCode
	 * @param resultMsg
	 * @return
	 */
	public static FastDfsDeleteResult getFailureResult(String resultCode, String resultMsg) {
        return new FastDfsDeleteResult(resultCode, resultMsg);
    }

	/**
	 * 成功结果封装
	 * @return
	 */
	public static FastDfsDeleteResult getSuccessResult() {
        return new FastDfsDeleteResult(GlobalEnum.FileResultEnum.DELETE_SUCCESS.getCode(), GlobalEnum.FileResultEnum.DELETE_SUCCESS.getMessage());
    }
}	
