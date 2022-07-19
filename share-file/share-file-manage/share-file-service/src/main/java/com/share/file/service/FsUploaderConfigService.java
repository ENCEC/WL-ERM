package com.share.file.service;

import java.util.Map;

import com.share.file.vo.FsUploaderConfigVo;

/**
 * 文件服务配置接口
 * @author bll
 * @date 20201211
 */
public interface FsUploaderConfigService {

	/**
	 * 文件配置类保存接口
	 * @param fsUploaderConfigVo
	 * @return
	 */
	Map<String, Object> saveUploaderConfig(FsUploaderConfigVo fsUploaderConfigVo);


	/**
	 * 根据使用方系统编码查询文件上传配置
	 *
	 * @param businessSystemCode 使用方系统编码
	 * @return Map
	 * @throws
	 * @author tujx
	 */
	Map<String,Object> getUploaderConfigBySystemCode(String businessSystemCode);


}
