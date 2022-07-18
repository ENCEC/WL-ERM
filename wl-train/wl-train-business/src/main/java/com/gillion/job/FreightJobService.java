package com.gillion.job;

import com.gillion.ec.scheduler.worker.handler.JobContext;

/**
 * @author wengms
 * @date 2021/1/11 2:02 下午
 * @email wengms@gillion.com.cn
 */
public interface FreightJobService {
    /**
     * 简单的任务处理
     */
    void simpleJob();


    /**
     * 分片任务处理
     * @param sharded
     */
    void shardJob(int sharded);


    /**
     * 任务中获取上下文
     * @param jobContext
     */
    void contextJob(JobContext jobContext);

}
