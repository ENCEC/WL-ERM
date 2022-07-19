package com.share.file.controller;

import com.share.file.enums.GlobalEnum;
import com.share.file.model.vo.FileInfoApiVO;
import com.share.file.model.vo.FileInfoReturnVo;
import com.share.file.model.vo.FileInfoVO;
import com.share.file.model.vo.FileListParamsVo;
import com.share.file.service.FsUploaderConfigService;
import com.share.file.service.impl.Base64DecodedMultipartFile;
import com.share.file.util.fastdfs.FastDfsDeleteResult;
import com.share.file.util.fastdfs.FastDfsTokenResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.share.file.service.ExternalFileService;
import com.share.file.util.fastdfs.FastDfsDownloadResult;
import com.share.file.util.fastdfs.FastDfsUploadResult;

import java.util.List;
import java.util.Map;

/**
 * @author: baill
 * @description: 外部文件服务接口controller
 * @data: 2020-10-26 14:10
 */
@RestController
@RequestMapping("externalFile")
public class ExternalFileController {

	@Autowired
	ExternalFileService externalFileService;

	@Autowired
	FsUploaderConfigService fsUploaderConfigService;

	/**
	 * 文件上传
	 *
	 * @param systemId
	 * @param fileType
	 * @param fileName
	 * @param file
	 * @return
	 * @author bll
	 */
	@ApiOperation(value = "上传文件外部接口",notes = "上传文件外部接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "systemId", value = "系统id", required = true, dataType = "string", paramType = "uploadExternalFile"),
		@ApiImplicitParam(name = "fileType", value = "文件类型", required = true, dataType = "string", paramType = "uploadExternalFile"),
		@ApiImplicitParam(name = "fileName", value = "文件名称", required = true, dataType = "string", paramType = "uploadExternalFile"),
		@ApiImplicitParam(name = "file", value = "文件", required = true, dataType = "__file", paramType = "uploadExternalFile")
	}) 	
	@RequestMapping(value = "/uploadExternalFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, method = RequestMethod.POST)
	public FastDfsUploadResult uploadExternalFile(String systemId, String fileType, String fileName,
			MultipartFile file) {
		return externalFileService.uploadExternalFile(systemId, fileType, fileName, file);
	}

	/**
	 * 文件下载
	 *
	 * @param fileInfoVO
	 * @return
	 * @author bll
	 */
	@ApiOperation(value = "文件下载外部接口",notes = "文件下载外部接口")
	@ApiImplicitParam(name = "FileInfoVO", value = "文件接收参数", required = true, dataType = "FileInfoVO", paramType = "downloadExternalFile")
	@RequestMapping(value = "/downloadExternalFile", method = RequestMethod.POST)
	public FastDfsDownloadResult downloadExternalFile(@RequestBody FileInfoVO fileInfoVO) {
		String systemId = fileInfoVO.getSystemId();
		String fileKey = fileInfoVO.getFileKey();
		return externalFileService.downloadExternalFile(systemId, fileKey);
	}

	/**
	 * @Description 根据不同的查询条件进行查询获取文件列表；
	 * @Author nily
	 * @Date 2020/11/24
	 * @Time 9:04 上午
	 */
	@ApiOperation(value = "查询获取文件列表", notes = "查询获取文件列表")
	@ApiImplicitParam(name = "FileListParamsVo", value = "查询文件列表Vo", required = true, dataType = "FileListParamsVo")
	@PostMapping(value = "/selectFilesList")
	public FileInfoReturnVo<Object> selectFilesList(@RequestBody FileListParamsVo fileListParamsVo) {
		return externalFileService.selectFilesList(fileListParamsVo);
	}

	/**
	 * 对外提供base64加密文件的上传接口
	 * @param fileInfoApiVO
	 * @return FastDfsUploadResult
	 * @author wangcl
	 */
    @ApiOperation(value = "对外提供base64加密文件的上传接口",notes = "对外提供base64加密文件的上传接口")
    @ApiImplicitParam(name = "FileInfoApiVO", value = "文件接收参数", required = true, dataType = "FileInfoApiVO", paramType = "save")
	@RequestMapping(value = "/uploadBase64File", method = RequestMethod.POST)
	public FastDfsUploadResult uploadBase64File(@RequestBody FileInfoApiVO fileInfoApiVO) {
		String systemId = fileInfoApiVO.getSystemId();
		String fileType = fileInfoApiVO.getFileType();
		String fileName = fileInfoApiVO.getFileName();
		Base64DecodedMultipartFile base64DecodedMultipartFile = null;
		if (StringUtils.isEmpty(fileInfoApiVO.getFile())) {
			return FastDfsUploadResult.getFailureResult(GlobalEnum.FileResultEnum.PARAMETER_EMPTY_EXCEPTION.getCode(),
					GlobalEnum.FileResultEnum.PARAMETER_EMPTY_EXCEPTION.getMessage() + "【file字段为空】", null);
		}
		base64DecodedMultipartFile = (Base64DecodedMultipartFile) Base64DecodedMultipartFile.base64ToMultipart(fileInfoApiVO.getFile());
		return externalFileService.uploadExternalFile(systemId, fileType, fileName, base64DecodedMultipartFile);
	}

	/**
	 *  删除文件接口
	 * @param fileInfoVO
	 * @return
	 * @author wangcl
	 */
    @ApiOperation(value = "删除文件外部接口",notes = "删除文件外部接口")
    @ApiImplicitParam(name = "FileInfoVO", value = "文件接收参数", required = true, dataType = "FileInfoVO", paramType = "delete")
	@RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
	public FastDfsDeleteResult deleteFile(@RequestBody FileInfoVO fileInfoVO) {
		return externalFileService.deleteFile(fileInfoVO);
	}

	/**
	 * 获取完整路径接口
	 * @param fileInfoVO
	 * @return FastDfsTokenResult
	 * @author wangcl
	 */
	@ApiOperation(value = "获取完整路径",notes = "获取完整路径")
	@ApiImplicitParam(name = "FileInfoVO", value = "文件接收参数", required = true, dataType = "FileInfoVO", paramType = "getToken")
	@RequestMapping(value = "/getFullUrl", method = RequestMethod.POST)
	public FastDfsTokenResult getFullUrl(@RequestBody FileInfoVO fileInfoVO) {
		return externalFileService.getToken(fileInfoVO);
	}

	/**
	 * 获取完整路径接口
	 * @param fileInfoList
	 * @return FastDfsTokenResult
	 * @author wangzicheng
	 */
	@ApiOperation(value = "获取完整路径",notes = "获取完整路径")
	@ApiImplicitParam(name = "FileInfoVO", value = "文件接收参数", required = true, dataType = "FileInfoVO", paramType = "getToken")
	@RequestMapping(value = "/getFullUrlMap", method = RequestMethod.POST)
	public Map<String, FastDfsTokenResult> getFullUrlMap(@RequestBody List<FileInfoVO> fileInfoList){
		return externalFileService.getTokenMap(fileInfoList);
	}


	/**
	 * 根据使用方系统编码查询文件上传配置
	 *
	 * @param businessSystemCode 使用方系统编码
	 * @return Map
	 * @throws
	 * @author tujx
	 */
	@ApiOperation(value = "根据使用方系统编码查询文件上传配置",notes = "根据使用方系统编码查询文件上传配置")
	@ApiImplicitParam(name = "businessSystemCode", value = "使用方系统编码", required = true, dataType = "String", paramType = "query")
	@PostMapping(value = "/getUploaderConfigBySystemCode")
	public Map<String,Object> getUploaderConfigBySystemCode(@RequestBody String businessSystemCode){
		return fsUploaderConfigService.getUploaderConfigBySystemCode(businessSystemCode);
	}

	/**
	 * 获取完整路径接口
	 * @param fileInfoList
	 * @return FastDfsTokenResult
	 * @author wangzicheng
	 */
	@ApiOperation(value = "获取附件集合",notes = "获取附件集合")
	@ApiImplicitParam(name = "fileKeyList", value = "key集合", required = true, dataType = "fileKeyList", paramType = "fileKeyList")
	@RequestMapping(value = "/getFsFileInfoList", method = RequestMethod.POST)
	public List<FileListParamsVo> getFsFileInfoList(@RequestBody List<String> fileKeyList){
		return  externalFileService.getFsFileInfoList(fileKeyList);
	}
}
