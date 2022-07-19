package com.share.file.service;

import com.share.file.model.entity.FsFileInfo;
import com.share.file.model.vo.FileInfoReturnVo;
import com.share.file.model.vo.FileInfoVO;
import com.share.file.model.vo.FileListParamsVo;
import com.share.file.util.fastdfs.FastDfsDeleteResult;
import com.share.file.util.fastdfs.FastDfsTokenResult;
import org.springframework.web.multipart.MultipartFile;

import com.share.file.util.fastdfs.FastDfsDownloadResult;
import com.share.file.util.fastdfs.FastDfsUploadResult;

import java.util.List;
import java.util.Map;


/**
 *
 * @author: baill
 * @description: 外部文件服务接口Service
 * @data: 2020-10-26 14:10
 */
public interface ExternalFileService {

	/**
	 * 上传文件
	 * @param systemId
	 * @param fileType
	 * @param fileName
	 * @param file
	 * @return
	 */
	FastDfsUploadResult uploadExternalFile(String systemId, String fileType, String fileName, MultipartFile file);

	/**
	 * 下载文件
	 * @param systemId
	 * @param fileKey
	 * @return
	 */
	FastDfsDownloadResult downloadExternalFile(String systemId, String fileKey);

	/**
	 * 删除文件
	 * @param fileInfoVO 文件信息参数
	 * @return
	 * @author wangcl
	 */
	FastDfsDeleteResult deleteFile(FileInfoVO fileInfoVO);

	/**
	 * 为用户返回文件列表功能
	 *
	 * @param fileListParamsVo
	 * @return
	 * @Author nily
	 * @Date 2020/11/24
	 * @Time 9:24 上午
	 */
	FileInfoReturnVo<Object> selectFilesList(FileListParamsVo fileListParamsVo);

	/**
	 * 根据filekey获取token
	 * @param fileInfoVO
	 * @return FastDfsTokenResult
	 * @author wangcl
	 */
   FastDfsTokenResult getToken(FileInfoVO fileInfoVO);
	/**
	 * 根据多个filekey获取多个token
	 * @param fileInfoList
	 * @return FastDfsTokenResult
	 * @author wangzicheng
	 */
	Map<String, FastDfsTokenResult> getTokenMap(List<FileInfoVO> fileInfoList);

	/**
	 * 根据多个filekey获取多个file
	 * @param fileKeyList
	 * @return FastDfsTokenResult
	 * @author kzb
	 */
	List<FileListParamsVo> getFsFileInfoList(List<String> fileKeyList);
}
