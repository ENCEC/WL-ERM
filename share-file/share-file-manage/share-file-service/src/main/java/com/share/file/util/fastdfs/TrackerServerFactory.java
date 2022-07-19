package com.share.file.util.fastdfs;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

/**
 * TrackerServer 工厂类，创建对象池需要 BasePooledObjectFactory 对象或子类.
 *
 * @author wangcl
 * @date 20201027
 */
public class TrackerServerFactory extends BasePooledObjectFactory<TrackerServer> {

    /**
     * @author wangcl
     * 创建链接
     * @return
     * @throws Exception
     */
    @Override
    public TrackerServer create() throws Exception {
        TrackerClient trackerClient = new TrackerClient();
        return trackerClient.getTrackerServer();
    }

    /**
     * @author wangcl
     * @param trackerServer
     * @return
     */
    @Override
    public PooledObject<TrackerServer> wrap(TrackerServer trackerServer) {
        return new DefaultPooledObject<>(trackerServer);
    }
}
