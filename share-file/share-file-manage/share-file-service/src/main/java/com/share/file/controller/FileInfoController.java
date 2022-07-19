package com.share.file.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.share.file.model.vo.FileInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.share.file.service.FileInfoService;

/**
 * 
 * @author: baill
 * @description: 文件服务接口controller
 * @data: 2020-10-26 14:10
 */
@RestController
@RequestMapping("fileInfo")
public class FileInfoController {
	
	@Autowired
	FileInfoService fileInfoService;
	
	/**
	 * 上传文件
	 * @param file
	 * @param response
	 * @return
	 * @author bll
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.GET)
	@ApiOperation(value = "文件上传接口",notes = "文件上传接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "file", value = "文件数组", required = true, dataType = "__file", allowMultiple = true, paramType = "uploadFile"),
		@ApiImplicitParam(name = "fileType", value = "文件类型数组", required = true, dataType = "string", allowMultiple = true, paramType = "uploadFile"),
		@ApiImplicitParam(name = "systemId", value = "系统id", required = true, dataType = "string", paramType = "uploadFile")
	})
	public Map<String, Object> uploadFile(@RequestParam MultipartFile[] file,@RequestParam String[] fileType,@RequestParam String systemId, HttpServletResponse response) {
        return fileInfoService.uploadFile(file, fileType, systemId);

    }

	/**
	 * 下载文件
	 * @param fileId
	 * @param response
	 * @return
	 * @author bll
	 */
	@RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
	@ApiOperation(value = "文件下载接口",notes = "文件下载接口")
	@ApiImplicitParam(name = "fileId", value = "文件id接收参数", required = true, dataType = "string", paramType = "downloadFile")
	public void downloadFile(String fileId, HttpServletResponse response) {
		fileInfoService.downloadFile(fileId,response);
	}

	/**
	 * 批量下载文件
	 * @param fileIds
	 * @param response
	 * @author bll
	 */
	@RequestMapping(value = "/batchDownloadFile", method = RequestMethod.GET)
	@ApiOperation(value = "文件批量下载接口",notes = "文件批量下载接口")
	@ApiImplicitParam(name = "fileIds", value = "文件ID数组接收参数", required = true, dataType = "Long", allowMultiple = true, paramType = "batchDownloadFile")
	public void batchDownloadFile(Long[] fileIds, HttpServletResponse response) {
		fileInfoService.batchDownloadFile(fileIds,response);
	}


	/**
	 * @Description 删除文件
	 * @Author nily
	 * @Date 2020/11/23
	 * @Time 3:50 下午
	 */
	@ApiOperation(value = "删除文件",notes = "删除文件")
	@ApiImplicitParam(name = "FileInfo",value = "删除文件Vo",required = true,dataType ="FileInfo")
	@RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
	public Map<String,Object> deleteFile(FileInfo fileinfo) {
        return fileInfoService.deleteFile(fileinfo);
    }

	/**
	 * 批量删除文件
	 * @param fileinfos
	 * @return
	 */
	@ApiOperation(value = "批量删除文件",notes = "批量删除文件")
	@ApiImplicitParam(name = "fileinfos",value = "删除文件Vo集合",required = true,dataType ="FileInfo",allowMultiple = true)
	@PostMapping(value = "/batchDeleteFile")
	public Map<String,Object> batchDeleteFile(@RequestBody FileInfo[] fileinfos) {
        return fileInfoService.batchDeleteFile(fileinfos);
    }

	/**
	 * 保存当前系统code
	 * @param systemCode
	 * @return
	 * @author bll
	 */
	@RequestMapping(value = "/saveCurrentSystem", method = RequestMethod.GET)
	@ApiOperation(value = "保存当前系统code",notes = "保存当前系统code")
	@ApiImplicitParam(name = "systemCode", value = "系统code参数", required = true, dataType = "string", paramType = "saveCurrentSystem")
	public Map<String,Object> saveCurrentSystem(@RequestParam("systemCode") String systemCode) {
        return fileInfoService.saveCurrentSystem(systemCode);
    }
}
