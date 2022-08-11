package com.gillion.util;

import com.gillion.ec.core.exceptions.BusinessRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author wangxinyu
 * @version 1.0
 * @date 2019/2/20 12:42
 * @description redis工具类
 */
@Slf4j
@Service("RedisUtil")
public class RedisUtil {

    private static final Long SUCCESS = 1L;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public RedisTemplate<String, Object> getInstance() {
        return redisTemplate;
    }

    /**
     * 获取 Object 类型 key-value
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置 Object 类型 key-value 并添加过期时间 (毫秒单位)
     *
     * @param key
     * @param value
     * @param time  过期时间,毫秒单位
     */
    public void setForTimeMills(String key, Object value, long time) {
        validateTimeIsNull(time);
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.MILLISECONDS);
    }

    /**
     * 设置 Object 类型 key-value 并添加过期时间 (秒单位)
     *
     * @param key
     * @param value
     * @param time  过期时间,秒单位
     */
    public void setForTimeSecs(String key, Object value, long time) {
        validateTimeIsNull(time);
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 设置 Object 类型 key-value 并添加过期时间 (分钟单位)
     *
     * @param key
     * @param value
     * @param time  过期时间,分钟单位
     */
    public void setForTimeMins(String key, Object value, long time) {
        validateTimeIsNull(time);
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.MINUTES);
    }

    /**
     * 设置 Object 类型 key-value 并添加过期时间 (小时单位)
     *
     * @param key
     * @param value
     * @param time  过期时间,小时单位
     */
    public void setForTimeHours(String key, Object value, long time) {
        validateTimeIsNull(time);
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.HOURS);
    }

    /**
     * 设置 Object 类型 key-value 并添加过期时间 (天单位)
     *
     * @param key
     * @param value
     * @param time  过期时间,天单位
     */
    public void setForTimeDays(String key, Object value, long time) {
        validateTimeIsNull(time);
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.DAYS);
    }


    /**
     * 设置 Object 类型 key-value 并添加过期时间 (自定义单位)
     *
     * @param key
     * @param value
     * @param time  过期时间,自定义单位
     */
    public void setForTimeCustom(String key, Object value, long time, TimeUnit type) {
        validateTimeIsNull(time);
        redisTemplate.opsForValue().set(key, value, time, type);
    }

    /**
     * 给一个指定的 key 值附加过期时间,自定义单位
     *
     * @param key
     * @param time
     * @param type
     * @return
     */
    public boolean expire(String key, long time, TimeUnit type) {
        validateTimeIsNull(time);
        return redisTemplate.boundValueOps(key).expire(time, type);
    }

    /**
     * 移除指定key 的过期时间
     *
     * @param key
     * @return
     */
    public boolean persist(String key) {
        return redisTemplate.boundValueOps(key).persist();
    }


    /**
     * 获取指定key 的过期时间,单位为秒
     *
     * @param key
     * @return
     */
    public Long getExpire(String key) {
        return redisTemplate.boundValueOps(key).getExpire();
    }

    /**
     * 删除 key-value
     *
     * @param key
     * @return
     */
    public boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    private void validateTimeIsNull(long time) {
        if (time <= 0) {
            throw new BusinessRuntimeException("redis失效时间不能为空");
        }
    }

    /**
     * 设置定时器任务锁
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setScheduler(final String key, Object value) {
        boolean result = false;
        try {
            return redisTemplate.opsForValue().setIfAbsent(key, value);
        } catch (Exception e) {
            log.info("{}", e.getMessage(), e);
        }
        return result;
    }

    /**
     * 加锁
     * @param key 加锁key
     * @param value key对应的value
     * @param timeOut 过期时间
     * @param timeUnit 时间单位
     * @return 是否加锁成功
     */
    public boolean lock(final String key, Object value, long timeOut, TimeUnit timeUnit) {
        try {
            return redisTemplate.opsForValue().setIfAbsent(key, value, timeOut, timeUnit);
        } catch (Exception e) {
            log.info("{}", e.getMessage(), e);
        }
        return false;
    }

    /**
     * 解锁
     * @param key 加锁key
     * @param value key对应的value
     * @return 是否解锁成功
     */
    public boolean unLock(final String key, Object value) {
        try {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            RedisScript<String> redisScript = new DefaultRedisScript<>(script, String.class);
            Long delResult = redisTemplate.delete(Collections.singletonList(key));
            return SUCCESS.equals(delResult);
        } catch (Exception e) {
            log.info("{}", e.getMessage(), e);
        }
        return false;
    }
}
