package com.share.file.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 删除文件结果封装
 * @author wangcl
 * @date 20201103
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FastDfsDeleteResult {
	/**
     * 返回码
	 */
	private String resultCode;
	/**
     * 返回信息
	 */
	private String resultMsg;
}	
