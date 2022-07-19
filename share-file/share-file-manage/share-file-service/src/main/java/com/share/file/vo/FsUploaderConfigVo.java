package com.share.file.vo;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 文件服务上传配置实体
 * @author bll
 * @date 20201211
 */
@Data
public class FsUploaderConfigVo {
	
	/**使用方业务系统ID，引用账号体系的ID*/
	private String businessSystemId;
	
    /**上传个数限制*/
    private Integer countLimit;
    
    /**上传大小限制*/
    private BigDecimal sizeLimit;
}
