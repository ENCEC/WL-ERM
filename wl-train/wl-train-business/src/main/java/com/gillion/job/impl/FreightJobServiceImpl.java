package com.gillion.job.impl;

import com.gillion.ec.scheduler.utils.ThreadUtils;
import com.gillion.ec.scheduler.worker.Scheduled;
import com.gillion.ec.scheduler.worker.handler.JobContext;
import com.gillion.job.FreightJobService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

/**
 * @author wengms
 * @date 2021/1/11 2:03 下午
 * @email wengms@gillion.com.cn
 */
@Service
@Slf4j
public class FreightJobServiceImpl implements FreightJobService {

    @Scheduled("SIMPLE_JOB")
    @Override
    public void simpleJob() {
        ThreadUtils.sleep(3000);
        log.info("进入定时调度SIMPLE_JOB");
    }

    @Scheduled("SHARD_JOB")
    @Override
    public void shardJob(int sharded) {
        log.info("进入定时调度：SHARD_JOB,当前分片为：{}",sharded);
        if (RandomUtils.nextInt(0,100)%5==0){
            throw new RuntimeException("发生异常啦、。、");
        }
    }

    @Scheduled(value = "CONTEXT_JOB")
    @Override
    public void contextJob(JobContext jobContext) {

        ThreadUtils.sleep(3000);
        log.info("从：{}触发了定时调度任务:{} 任务标记为:{}",
                jobContext.getSource(),
                jobContext.getScheduleCode(),
                jobContext.getTriggerId());
    }
}
