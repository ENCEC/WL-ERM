package com.share.auth.task;

import com.gillion.ec.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * @author chenxf
 * @date 2021-03-02 14:26
 */
@Slf4j
@Aspect
@Component
public class RedisTryLockAspect {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    InetAddress addr = null;

    @Around("execution(* *.*(..)) && @annotation(com.share.auth.task.RedisTryLock)")
    public void redisTryLockPoint(ProceedingJoinPoint pjp) throws Exception {
        String defKey = "redis:lock:";
        RedisTryLock annotation = null;
        Method method = null;
        Class aClass = pjp.getTarget().getClass();
        //获取该切点所在方法的名称,每一个使用切面的方法
        String name = pjp.getSignature().getName();
        try {
            //通过反射获得该方法
            method = aClass.getMethod(name);
            //获得该注解
            annotation = method.getAnnotation(RedisTryLock.class);
            //获取注解对应的值keyName，expireTime
            String keyName = annotation.keyName();
            Integer expireTime = annotation.expireTime();
            Assert.isTrue(0 != expireTime, "redis lock's expireTime is null");
            //获取本机ip
            try {
                addr = InetAddress.getLocalHost();

            } catch (UnknownHostException e) {
                log.error("获取IP失败--》", e);

            }
            String ip = addr.getHostAddress();
            // 设置redis key值

            defKey = StringUtils.isBlank(keyName) ? defKey + aClass.getDeclaringClass() + method.getName() : defKey + keyName;
            //根据redis 锁的原理判断是否执行成功，设值成功说明其他服务器没有执行定时任务，反则正在执行
            if (redisTemplate.opsForValue().setIfAbsent(defKey, ip)) {
                redisTemplate.expire(defKey, expireTime, TimeUnit.SECONDS);
                log.info("获得分布式锁成功! key:{}", defKey);
                pjp.proceed();
                redisTemplate.delete(defKey);
                log.info("定时任务执行完，释放分布式锁成功,key:{}", defKey);
                return;

            }
            Object redisVal = redisTemplate.opsForValue().get(defKey);
            log.info("{}已在{}机器上占用分布式锁，聚类任务正在执行", defKey, redisVal);

        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
            log.error("Facet aop failed error {}", e.getLocalizedMessage());

        } catch (Throwable throwable) {
//            throwable.printStackTrace();
            log.error("Facet aop failed error {}", throwable.getLocalizedMessage());
        }

    }
}
