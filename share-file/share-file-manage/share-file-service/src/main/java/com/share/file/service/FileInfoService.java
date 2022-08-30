package com.share.file.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.share.file.model.vo.FileInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务操作接口
 * @author bll
 * @date 20201211
 */
public interface FileInfoService {
	
	/**
	 * 上传文件
	 * @param file
	 * @param fileType
	 * @param systemId
	 * @return
	 * @author bll
	 */
	Map<String, Object> uploadFile(MultipartFile[] file, String[] fileType, String systemId);

	/**
	 * 下载文件
	 * @param fileId
	 * @param response
	 * @author bll
	 */
	void downloadFile(String fileId, HttpServletResponse response);

	/**
	 * 批量下载文件
	 * @param fileIds
	 * @param response
	 * @author bll
	 */
	void batchDownloadFile(Long[] fileIds, HttpServletResponse response);

	/**
	 * 删除文件
	 * @param fileInfo
	 * @return
	 * @author nily
	 * @Date 2020/11/23
	 */
	Map<String, Object> deleteFile(FileInfo fileInfo);

	/**
	 * 批量删除文件
	 * @param fileInfo
	 * @return
	 * @author nily
	 * @Date 2020/11/23
	 */
	Map<String, Object> batchDeleteFile(FileInfo[] fileInfo);
	
	/**
	 * 保存当前系统
	 * @param systemCode
	 * @return
	 */
	Map<String, Object> saveCurrentSystem(String systemCode);

	/**
	 * 批量上传文件
	 * @param files
	 * @param fileType
	 * @param systemId
	 * @return
	 */
	Map<String, Object> batchUploadFile(MultipartFile[] files, String[] fileType, String systemId);

}
