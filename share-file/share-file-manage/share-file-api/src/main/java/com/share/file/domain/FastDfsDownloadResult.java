package com.share.file.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FastDfsDownloadResult {
	private String resultCode;
	private String resultMsg;
	private String file;
	/**
	 * 文件名
	 */
	private String fileName;
}
