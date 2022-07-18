package com.share.auth.center.queue.service;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 基于Redisson的延迟队列
 *
 * @author tujx
 * @date 2021/07/05
 */
@Slf4j
@Component
public class RedisDelayedQueue {

    /**
     * 缺省延迟2秒发送
     */
    public static final Long DEFAULT_DELAY_TIME = 2L;
    /**
     * 缺省时间间隔为秒
     */
    public static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;

    @Autowired
    RedissonClient redissonClient;

    /**
     * 添加队列
     *
     * @param t         DTO传输类
     * @param delay     时间数量
     * @param timeUnit  时间单位
     * @param queueName 队列名称
     * @param <T>       DTO传输类
     */
    @Async
    public <T> void addQueue(T t, Long delay, TimeUnit timeUnit, String queueName) {
        if (delay == null) {
            delay = DEFAULT_DELAY_TIME;
        }
        if (timeUnit == null) {
            timeUnit = DEFAULT_TIME_UNIT;
        }
        RBlockingQueue<T> blockingFairQueue = redissonClient.getBlockingQueue(queueName);
        RDelayedQueue<T> delayedQueue = redissonClient.getDelayedQueue(blockingFairQueue);
        delayedQueue.offer(t, delay, timeUnit);
        log.info("添加队列{},delay:{},timeUnit:{}", queueName, delay, timeUnit);
    }
}
