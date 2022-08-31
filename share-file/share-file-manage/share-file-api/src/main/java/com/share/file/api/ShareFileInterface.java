package com.share.file.api;

import com.share.file.domain.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author baill
 * @date 2020-10-30 16:52 文件服务外部接口
 */
@FeignClient(value = "${application.name.file}")
public interface ShareFileInterface {
	/**
	 * 上传文件
	 * @param systemId
	 * @param fileType
	 * @param fileName
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/externalFile/uploadExternalFile",consumes = "multipart/form-data")
	FastDfsUploadResult uploadExternalFile(@RequestParam("systemId") String systemId,
			@RequestParam("fileType") String fileType, @RequestParam("fileName") String fileName,
			@RequestPart("file") MultipartFile file);

	/**
	 * 下载文件
	 * @param fileInfoVO
	 * @return
	 */
	@RequestMapping(value = "/externalFile/downloadExternalFile")
	FastDfsDownloadResult downloadExternalFile(@RequestBody FileInfoVO fileInfoVO);

	/**
	 * @Description 查询文件列表
	 * @Author nily
	 * @Date 2020/11/27
	 * @Time 5:11 下午
	 */
	@PostMapping(value = "/externalFile/selectFilesList")
	FileInfoReturnVo<Object> selectFilesList(@RequestBody FileListParamsVo fileListParamsVo);

    /**
     * 删除文件
     * @param fileInfoVO
     * @return
     * @author wangcl
     */
    @RequestMapping(value = "/externalFile/deleteFile")
    FastDfsDeleteResult deleteFile(@RequestBody FileInfoVO fileInfoVO);

    /**
     * 获取文件显示的完整路径
     * @param fileInfoVO
     * @return
     * @author wangcl
     */
    @RequestMapping(value = "/externalFile/getFullUrl")
    FastDfsTokenResult getFullUrl(FileInfoVO fileInfoVO);

	/**
	 * 根据使用方系统编码查询文件上传配置
	 *
	 * @param businessSystemCode 使用方系统编码
	 * @return Map
	 * @throws
	 * @author tujx
	 */
    @PostMapping(value = "/externalFile/getUploaderConfigBySystemCode")
    Map<String,Object> getUploaderConfigBySystemCode(@RequestBody String businessSystemCode);

	/**
	 * 根据附件keyList获取多个附件
	 * @param fileInfoList
	 * @return
	 * @author wangzicheng
	 */
	@RequestMapping(value = "/externalFile/getFsFileInfoList")
	List<FileListParamsVo> getFsFileInfoList(List<String> fileKeyList);

	/**
	 * 获取多个文件显示的完整路径
	 * @param fileInfoList
	 * @return
	 * @author wangzicheng
	 */
	@RequestMapping(value = "/externalFile/getFullUrlMap")
	Map<String, FastDfsTokenResult> getFullUrlMap(List<FileInfoVO> fileInfoList);

	/**
	 * 批量上传文件
	 * @param file
	 * @param fileType
	 * @param systemId
	 * @return
	 */
	@RequestMapping(value = "/fileInfo/batchUploadFile",consumes = "multipart/form-data")
	Map<String, Object> batchUploadFile(@RequestPart("file") MultipartFile[] file,@RequestParam("fileType") String[] fileType,@RequestParam("systemId") String systemId);

}
