package com.share.file.util.fastdfs;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.csource.common.IniFileReader;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * TrackerServer 对象池
 * <p>
 *
 * @author wangcl
 * @date 20201027
 */
@Component
@Slf4j
public class TrackerServerPool {
    /**
     * org.slf4j.Logger
     */
    private static Logger logger = LoggerFactory.getLogger(TrackerServerPool.class);

    /**
     * 最小空闲数
     */
    private static int minIdle = 2;
    /**
     * 等待时间
     */
    private static int maxWait = 3000;
    /**
     * 最大空闲数
     */
    private static int maxIdle = 30;

    private static Properties prop = new Properties();
    /**
     * TrackerServer 对象池.
     * GenericObjectPool 没有无参构造
     */
    private static GenericObjectPool<TrackerServer> trackerServer;

    private TrackerServerPool() {
    }

    private static synchronized GenericObjectPool<TrackerServer> getObjectPool(String propertiesName){
        if (trackerServer == null) {
            try {
                // 加载配置文件
                ClientGlobal.initByProperties(propertiesName);
                InputStream in = IniFileReader.loadFromOsFileSystemOrClasspathAsStream(propertiesName);
                if (in != null) {
                    prop.load(in);
                }
            } catch (IOException e) {
                log.error("初始化fastdfs线程池错误IOException：" + e);
            } catch (MyException e) {
                log.error("初始化fastdfs线程池错误MyException："+e);
            }

            if(logger.isDebugEnabled()){
                logger.debug("ClientGlobal configInfo: {}", ClientGlobal.configInfo());
            }
            // Pool配置
            GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
            poolConfig.setMinIdle(minIdle);
            poolConfig.setMaxIdle(maxIdle);
            poolConfig.setMaxWaitMillis(maxWait);
            poolConfig.setTestOnBorrow(false);
            poolConfig.setTestOnReturn(true);
            poolConfig.setTestWhileIdle(true);
            int maxStorageConnection = Integer.parseInt(prop.getProperty("max_storage_connection"));
            log.info("maxStorageConnection:"+ maxStorageConnection);
            if(maxStorageConnection > 0){
                poolConfig.setMaxTotal(maxStorageConnection);
            }
            trackerServer = new GenericObjectPool<>(new TrackerServerFactory(), poolConfig);
            AbandonedConfig abandonedConfig = new AbandonedConfig();
            //在Maintenance的时候检查是否有泄漏
            abandonedConfig.setRemoveAbandonedOnMaintenance(true);
            //borrow 的时候检查泄漏
            abandonedConfig.setRemoveAbandonedOnBorrow(true);
            //如果一个对象borrow之后20秒还没有返还给pool，认为是泄漏的对象
            abandonedConfig.setRemoveAbandonedTimeout(20);
            trackerServer.setAbandonedConfig(abandonedConfig);
            //5秒运行一次维护任务
            trackerServer.setTimeBetweenEvictionRunsMillis(5000);
        }
        return trackerServer;
    }

    /**
     * 获取 TrackerServer
     * @return TrackerServer
     * @throws FastDfsException
     */
    public static TrackerServer borrowObject(String propertiesName) throws FastDfsException {
        TrackerServer trackerServer = null;
        try {
            trackerServer = getObjectPool(propertiesName).borrowObject();
        } catch (Exception e) {
            log.error("获取 TrackerServer错误:"+e);
            if(e instanceof FastDfsException){
                throw (FastDfsException) e;
            }
        }
        return trackerServer;
    }

    /**
     * 回收 TrackerServer
     * @param trackerServer 需要回收的 TrackerServer
     */
    public static void returnObject(TrackerServer trackerServer, String propertiesName){
        getObjectPool(propertiesName).returnObject(trackerServer);
    }

    /**
     * 根据属性获取value
     * @param key
     * @return
     */
    public static String getPara(String key) {
        String value = prop.getProperty(key);
        if (StringUtils.isNotEmpty(value)) {
            return value;
        } else {
            return null;
        }
    }

}
