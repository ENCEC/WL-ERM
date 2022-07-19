package com.share.file.util.fastdfs;

import com.share.file.constants.GlobalConstant;
import com.share.file.enums.GlobalEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ProtoCommon;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerServer;

import com.share.file.model.entity.FsFileInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author baill
 * @date 20201110
 * @description fastdfs封装工具类
 */
@Slf4j
@Component
public class FastDfsClient {
	/**
	 * 路径分隔符
	 */
	public static final String SEPARATOR = "/";
	/**
	 * Point
	 */
	public static final String POINT = ".";
	/**
	 * 文件名称Key
	 */
	private static final String FILENAME = "filename";

	/**
	 * TrackerServer 配置文件路径
	 */
	@Value("${fastdfs.config.name}")
	private String fastdfsConfigPath;

	/**
	 * 上传文件
	 *
	 * @param is
	 * @param filename
	 * @param descriptions
	 * @return
	 * @throws FastDfsException
	 * @throws IOException
	 */
	public String upload(InputStream is, String filename, Map<String, String> descriptions, StorageServer storageServer)
			throws FastDfsException, IOException {
		filename = toLocal(filename);
		// 返回路径
		String path = null;
		// 文件描述
		NameValuePair[] nvps = null;
		List<NameValuePair> nvpsList = new ArrayList<>();
		// 文件名后缀
		String suffix = getFilenameSuffix(filename);
		// 文件名
		if (StringUtils.isNotBlank(filename)) {
			nvpsList.add(new NameValuePair(FILENAME, filename));
		}
		// 描述信息
		if (descriptions != null && descriptions.size() > 0) {
			descriptions.forEach((key, value) -> nvpsList.add(new NameValuePair(key, value)));
		}
		if (CollectionUtils.isNotEmpty(nvpsList)) {
			nvps = new NameValuePair[nvpsList.size()];
			nvpsList.toArray(nvps);
		}
		TrackerServer trackerServer = TrackerServerPool.borrowObject(fastdfsConfigPath);
		try {
			StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
			// 读取流
			byte[] fileBuff = new byte[is.available()];
			is.read(fileBuff, 0, fileBuff.length);

			// 上传
			path = storageClient.upload_file1(fileBuff, suffix, nvps);

			if (StringUtils.isBlank(path)) {
				TrackerServerPool.returnObject(trackerServer, fastdfsConfigPath);
				throw new FastDfsException(GlobalEnum.FileResultEnum.UPLOAD_FAILURE.getCode(), GlobalEnum.FileResultEnum.UPLOAD_FAILURE.getMessage());
			}

			if (log.isDebugEnabled()) {
				log.debug("upload file success, return path is {}", path);
			}
		} catch (MyException e) {
			log.error("上传文件失败："+e);
			TrackerServerPool.returnObject(trackerServer, fastdfsConfigPath);
			throw new FastDfsException(GlobalEnum.FileResultEnum.UPLOAD_FAILURE.getCode(), GlobalEnum.FileResultEnum.UPLOAD_FAILURE.getMessage());
		} finally {
			// 关闭流
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					log.error("上传文件失败："+e);
					TrackerServerPool.returnObject(trackerServer, fastdfsConfigPath);
				}
			}
		}
		// 返还对象
		TrackerServerPool.returnObject(trackerServer, fastdfsConfigPath);
		return path;
	}

	/**
	 * 转换路径中的 '\' 为 '/' <br>
	 * 并把文件后缀转为小写
	 *
	 * @param path 路径
	 * @return
	 */
	public static String toLocal(String path) {
		if (StringUtils.isNotBlank(path)) {
			path = path.replace("\\\\", SEPARATOR);
			if (path.contains(POINT)) {
				String pre = path.substring(0, path.lastIndexOf(POINT) + 1);
				String suffix = path.substring(path.lastIndexOf(POINT) + 1).toLowerCase();
				path = pre + suffix;
			}
		}
		return path;
	}

	/**
	 * 获取文件名称的后缀
	 *
	 * @param filename 文件名 或 文件路径
	 * @return 文件后缀
	 */
	public static String getFilenameSuffix(String filename) {
		String suffix = null;
		String originalFilename = filename;
		if (StringUtils.isNotBlank(filename)) {
			if (filename.contains(SEPARATOR)) {
				filename = filename.substring(filename.lastIndexOf(SEPARATOR) + 1);
			}
			if (filename.contains(POINT)) {
				suffix = filename.substring(filename.lastIndexOf(POINT) + 1);
			} else {
				if (log.isErrorEnabled()) {
					log.error("filename error without suffix : {}", originalFilename);
				}
			}
		}
		return suffix;
	}

	/**
	 * 下载
	 * @param fileKey
	 * @param storageServer
	 * @return
	 * @throws FastDfsException
	 * @author baill
	 */
	public InputStream download(String fileKey, StorageServer storageServer) throws FastDfsException {
		TrackerServer trackerServer = TrackerServerPool.borrowObject(fastdfsConfigPath);
		StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
		InputStream inputStream = null;
		byte[] content = new byte[0];
		try {
			content = storageClient.download_file1(fileKey);
		} catch (Exception e) {
			// 返还对象
			TrackerServerPool.returnObject(trackerServer, fastdfsConfigPath);
			log.error("下载文件异常："+e);
			throw new FastDfsException();
		}
		if (content == null || content.length == 0) {
			return null;
		}
		inputStream = new ByteArrayInputStream(content);
		// 返还对象
		TrackerServerPool.returnObject(trackerServer, fastdfsConfigPath);
		return inputStream;
	}

	/**
	 * 批量下载
	 * @param files
	 * @param storageServer
	 * @return
	 * @throws FastDfsException
	 * @author baill
	 */
	public ByteArrayOutputStream batchDownload2(List<FsFileInfo> files, StorageServer storageServer)
			throws FastDfsException {
		TrackerServer trackerServer = TrackerServerPool.borrowObject(fastdfsConfigPath);
		StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
		InputStream inputStream = null;
		ByteArrayOutputStream baos = null;
		try {
			byte[] buffer = new byte[1024];
			// 创建一个新的 byte 数组输出流，它具有指定大小的缓冲区容量
			baos = new ByteArrayOutputStream();
			// 创建一个新的缓冲输出流，以将数据写入指定的底层输出流
			BufferedOutputStream fos = new BufferedOutputStream(baos);
			ZipOutputStream zos = new ZipOutputStream(fos);
			for (FsFileInfo file : files) {
				// 获取各个文件的数据流
				String filepath = toLocal(file.getFileKey().trim());
				byte[] content = storageClient.download_file1(filepath);
				if (content == null || content.length == 0) {
					return null;
				}
				inputStream = new ByteArrayInputStream(content);

				// 压缩文件内的文件名称
				String fileName = file.getCreateTime() + "_" + RandomUtils.nextInt(2, 300) + "_" + file.getFileName();
				zos.putNextEntry(new ZipEntry(fileName));

				while (inputStream.read(buffer) > 0) {
					// 将文件读入压缩文件内
					zos.write(buffer);
				}
				inputStream.close();
				zos.closeEntry();
			}
			fos.flush();
			fos.close();
			baos.close();
			zos.close();
		} catch (Exception e) {
			// 返还对象
			TrackerServerPool.returnObject(trackerServer, fastdfsConfigPath);
			log.error("批量下载文件失败:"+e);
		}
		// 返还对象
		TrackerServerPool.returnObject(trackerServer, fastdfsConfigPath);
		return baos;
	}

    /**
     * 删除文件
     * @param filepath
     * @return
     * @throws FastDfsException
     * @author wangcl
     */
    public int deleteFile(String filepath, StorageServer storageServer) throws FastDfsException {
        TrackerServer trackerServer = TrackerServerPool.borrowObject(fastdfsConfigPath);
        StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
        int success;
        try {
            success = storageClient.delete_file1(filepath);
        } catch (IOException e) {
			// 返还对象
			TrackerServerPool.returnObject(trackerServer, fastdfsConfigPath);
            log.error("删除文件失败:"+e);
            throw new FastDfsException(GlobalEnum.FileResultEnum.DELETE_FAILURE.getCode(), GlobalEnum.FileResultEnum.DELETE_FAILURE.getMessage());
        } catch (MyException e) {
			// 返还对象
			TrackerServerPool.returnObject(trackerServer, fastdfsConfigPath);
            log.error("删除文件失败:"+e);
			throw new FastDfsException(GlobalEnum.FileResultEnum.DELETE_FAILURE.getCode(), GlobalEnum.FileResultEnum.DELETE_FAILURE.getMessage());
        }
        // 返还对象
        TrackerServerPool.returnObject(trackerServer, fastdfsConfigPath);
        return success;
    }

	/**
	 * 获取访问服务器的token，拼接到地址后面
	 * @param filepath 文件路径 group1/M00/00/00/wKgzgFnkTPyAIAUGAAEoRmXZPp876.jpeg
	 * @return 返回token，如： token=078d370098b03e9020b82c829c205e1f&ts=1508141521
	 * @author wangcl
	 */
	public String getToken(String filepath) throws FastDfsException, UnsupportedEncodingException, NoSuchAlgorithmException, MyException {
		int ts = (int) Instant.now().getEpochSecond();
		String token;
		TrackerServer trackerServer = TrackerServerPool.borrowObject(fastdfsConfigPath);
		String httpSecretKey = TrackerServerPool.getPara(GlobalConstant.HTTP_SECRET_KEY);
		if(StringUtils.isEmpty(httpSecretKey)){
			TrackerServerPool.returnObject(trackerServer, fastdfsConfigPath);
			log.error("生成token失败:httpSecretKey为空");
			return null;
		}
		token = ProtoCommon.getToken(getFilename(filepath), ts, httpSecretKey);
		TrackerServerPool.returnObject(trackerServer, fastdfsConfigPath);
        StringBuilder sb = new StringBuilder();
		sb.append("token=").append(token);
		sb.append("&ts=").append(ts);
		return sb.toString();
	}

	/**
	 * 获取FastDFS文件的名称，如：M00/00/00/wKgzgFnkTPyAIAUGAAEoRmXZPp876.jpeg
	 * @param fileId 包含组名和文件名，如：group1/M00/00/00/wKgzgFnkTPyAIAUGAAEoRmXZPp876.jpeg
	 * @return FastDFS 返回的文件名：M00/00/00/wKgzgFnkTPyAIAUGAAEoRmXZPp876.jpeg
	 * @author wangcl
	 */
	public String getFilename(String fileId){
		String[] results = new String[2];
		StorageClient1.split_file_id(fileId, results);
		return results[1];
	}
}
