package com.share.file.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FastDfsUploadResult {
	private String resultCode;
	private String resultMsg;
	private String fileKey;
	
	
}	
