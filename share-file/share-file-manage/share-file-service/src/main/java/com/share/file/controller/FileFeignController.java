package com.share.file.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.share.auth.api.ShareAuthInterface;
import com.share.auth.domain.QueryApplicationDTO;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;


/**
 *
 * @author: baill
 * @description: 文件服务Feign远程接口controller
 * @data: 2020-10-28 14:10
 */
@RestController
@RequestMapping("FileFeign")
public class FileFeignController {

	@Autowired
	private ShareAuthInterface shareAuthInterface;

	/**
	 * feign远程调用auth服务的系统列表接口
	 * @param opType
	 * @return
	 * @author bll
	 */
	@RequestMapping(value = "/getSystemList", method = RequestMethod.GET)
	@ApiOperation(value = "获取系统列表接口",notes = "获取系统列表接口")
	@ApiImplicitParam(name = "opType", value = "接收参数", required = true, dataType = "string", paramType = "queryApplicationForOpType")
	public List<QueryApplicationDTO> getSystemList(@RequestParam("opType") String opType) {
        return shareAuthInterface.queryApplicationForOpType(opType);
    }
}
