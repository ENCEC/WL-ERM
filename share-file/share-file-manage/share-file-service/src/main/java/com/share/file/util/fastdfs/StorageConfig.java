package com.share.file.util.fastdfs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.csource.common.IniFileReader;
import org.csource.fastdfs.StorageServer;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 文件服务读取storage配置
 * @author bll
 * @date 20201211
 */
@Data
@Component
public class StorageConfig {

	/**
	 * TrackerServer 配置文件路径
	 */
	@Value("${fastdfs.config.name}")
	private String fastdfsConfigPath;

	public StorageServer getStorageServer() throws IOException {
		Properties props = new Properties();
		InputStream in = IniFileReader.loadFromOsFileSystemOrClasspathAsStream(fastdfsConfigPath);
		StorageServer storageServer = null;
		if (in != null) {
			props.load(in);
			String ip = props.getProperty("storage_server.ip");
			if(StringUtils.isEmpty(ip)) {
				return null;
			}
			String portStr = props.getProperty("storage_server.port");
			String storePathStr = props.getProperty("storage_store_path");
			int port = Integer.parseInt(portStr);
			int storePath = Integer.parseInt(storePathStr);
			storageServer = new StorageServer(ip, port, storePath);
		}
		return storageServer;
	}

}
