package com.share.file.util.fastdfs;

import com.share.file.enums.GlobalEnum;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 下载返回结果
 * @author bll
 * @date 20201211
 */
@Data
@AllArgsConstructor
public class FastDfsDownloadResult {
	private String resultCode;
	private String resultMsg;
	/**
	 * base64加密文件
	 */
	private String file;
	/**
	 * 文件名
	 */
	private String fileName;
	public static FastDfsDownloadResult getFailureResult(String resultCode,String resultMsg, String file, String fileName) {
        return new FastDfsDownloadResult(resultCode, resultMsg, file, fileName);
    }
	
	
	public static FastDfsDownloadResult getSuccessResult(String file, String fileName) {
        return new FastDfsDownloadResult(GlobalEnum.FileResultEnum.UPLOAD_SUCCESS.getCode(), GlobalEnum.FileResultEnum.DOWNLOAD_SUCCESS.getMessage(), file, fileName);
    }
}
