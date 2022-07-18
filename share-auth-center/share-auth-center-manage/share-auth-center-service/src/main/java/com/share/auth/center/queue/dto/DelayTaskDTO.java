package com.share.auth.center.queue.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 延时任务数据
 * @author chenhy
 * @date 20210805
 */
@Data
public class DelayTaskDTO implements Serializable {

    private static final long serialVersionUID = 1;

    /**
     * 锁定的账号
     */
    private String account;

}
