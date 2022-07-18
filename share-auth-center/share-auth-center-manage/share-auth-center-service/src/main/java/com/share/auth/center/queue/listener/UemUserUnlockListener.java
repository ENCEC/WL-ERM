package com.share.auth.center.queue.listener;

import com.share.auth.center.queue.dto.DelayTaskDTO;
import com.share.auth.center.queue.service.RedisDelayedQueueListener;
import com.share.auth.center.service.UemUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户解锁监听
 * @author chenhy
 * @date 20210805
 */
@Slf4j
@Component
public class UemUserUnlockListener implements RedisDelayedQueueListener<DelayTaskDTO> {

    @Autowired
    private UemUserService uemUserService;

    @Override
    public void invoke(DelayTaskDTO task) {
        log.info("解锁账号：{}", task.getAccount());
        uemUserService.unlockedUser(task.getAccount());
    }
}
