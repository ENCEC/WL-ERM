package com.share.file.util.fastdfs;

import com.share.file.enums.GlobalEnum;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 上传返回结果
 * @author bll
 * @date 20201211
 */
@Data
@AllArgsConstructor
public class FastDfsUploadResult {
	private String resultCode;
	private String resultMsg;
	private String fileKey;
	
	
	
	public static FastDfsUploadResult getFailureResult(String resultCode,String resultMsg, String fileKey) {
        return new FastDfsUploadResult(resultCode, resultMsg, fileKey);
    }
	
	
	public static FastDfsUploadResult getSuccessResult(String fileKey) {
        return new FastDfsUploadResult(GlobalEnum.FileResultEnum.UPLOAD_SUCCESS.getCode(), GlobalEnum.FileResultEnum.UPLOAD_SUCCESS.getMessage(), fileKey);
    }
}	
