package com.share.auth.task;

import com.share.auth.model.querymodels.QSysRequestLog;
import com.share.auth.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * 日志定时任务
 * @author chenhy
 * @date 2021-07-05
 */
@Slf4j
@Component
@EnableScheduling
public class SysRequestLogTask {

    @Value("${log.retain-days:90}")
    private Integer logRetainDays;

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 定时清理{logRetainDays}天前的日志
     */
    @Scheduled(cron = "${log.clean-cron:0 0 0 * * ?}")
    public void cleanLogTask() {
        log.info("执行日志定时任务-定时清理{}天前日志-开始", logRetainDays);
        // 定时任务并发处理
        boolean lock = redisUtil.lock(applicationName, applicationName, 1800, TimeUnit.SECONDS);
        if (!lock) {
            log.info("执行日志定时任务-定时清理{}天前日志-结束，其他服务已加锁执行", logRetainDays);
            return;
        }
        // logRetainDays天前日期
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, -logRetainDays);
        // 删除日志
        int rows = QSysRequestLog.sysRequestLog.delete().where(QSysRequestLog.createTime.loe$(instance.getTime())).execute();
        log.info("执行日志定时任务-定时清理{}天前日志-结束，共删除{}条日志", logRetainDays, rows);
    }

}
