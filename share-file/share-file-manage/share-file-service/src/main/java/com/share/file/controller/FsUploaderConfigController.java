package com.share.file.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.share.file.service.FsUploaderConfigService;
import com.share.file.vo.FsUploaderConfigVo;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author: baill
 * @description: 上传文件配置Controller
 * @data: 2020-12-03 15:10
 */
@RestController
@RequestMapping("fsUploaderConfig")
public class FsUploaderConfigController {
	
	@Autowired
	FsUploaderConfigService fsUploaderConfigService;
	
	/**
	 * 文件配置类保存接口
	 * @param fsUploaderConfigVo
	 * @return
	 * @author bll
	 */
	@PostMapping(value = "/saveUploaderConfig")
	@ApiOperation(value = "文件配置类保存接口",notes = "文件配置类保存接口")
	@ApiImplicitParam(name = "fsUploaderConfigVo", value = "文件配置类接收参数", required = true, dataType = "FsUploaderConfigVo", paramType = "saveUploaderConfig")
	public Map<String, Object> saveUploaderConfig(@RequestBody FsUploaderConfigVo fsUploaderConfigVo) {
        return fsUploaderConfigService.saveUploaderConfig(fsUploaderConfigVo);

    }
}
