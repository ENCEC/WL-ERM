package com.share.auth.center.queue.service;

/**
 * 队列监听接口
 *
 * @author tujx
 * @date 2021/07/05
 */
public interface RedisDelayedQueueListener<T> {
    /**
     * 执行方法
     *
     * @param t 泛型类
     */
    void invoke(T t);
}
