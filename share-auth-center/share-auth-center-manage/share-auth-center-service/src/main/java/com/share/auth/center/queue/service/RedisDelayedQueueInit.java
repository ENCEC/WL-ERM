package com.share.auth.center.queue.service;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 初始化延迟队列监听
 *
 * @author tujx
 * @date 2021/07/05
 */
@Slf4j
@Component
public class RedisDelayedQueueInit<T> implements CommandLineRunner, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void run(String... args) throws Exception {
        this.applicationContext.getBeansOfType(RedisDelayedQueueListener.class).values().forEach(taskEventListenerEntryValue -> {
            String listenerName = taskEventListenerEntryValue.getClass().getSimpleName();
            executeQueue(listenerName, taskEventListenerEntryValue);
        });
    }

    /**
     * 执行延时队列任务
     * @param queueName 队列名称
     * @param redisDelayedQueueListener 监听器
     */
    private void executeQueue(String queueName, RedisDelayedQueueListener<T> redisDelayedQueueListener) {
        log.info("延时队列：{}，启动延时任务线程", queueName);
        // 线程池
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("delayQueue-" + queueName + "-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(10), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        singleThreadPool.execute(() -> this.executeThread(queueName, redisDelayedQueueListener));
    }

    /**
     * 执行延时队列线程
     * @param queueName 队列名称
     * @param redisDelayedQueueListener 监听器
     */
    private void executeThread(String queueName, RedisDelayedQueueListener<T> redisDelayedQueueListener) {
        RBlockingQueue<T> blockingFairQueue = redissonClient.getBlockingQueue(queueName);
        redissonClient.getDelayedQueue(blockingFairQueue);
        while (true) {
            try {
                log.info("延时队列：{}，获取可执行的延时任务", queueName);
                T t = blockingFairQueue.take();
                log.info("延时队列：{}，执行延时队列任务", queueName);
                redisDelayedQueueListener.invoke(t);
                log.info("延时队列：{}，执行延时队列任务--完成", queueName);
            } catch (InterruptedException e) {
                log.error("延时队列：{}，延时任务执行失败", queueName, e);
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    /**
     * 获取应用上下文
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
