package com.share.auth.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangcl
 * @date 20201021
 * @description 测试一对多查询
 */
@Data
public class ApplicationVO implements Serializable {
    private Long uemUserId;
    private String account;
    private String mobile;
    private String email;
    private String source;
    private Long oriApplication;
    private String applicationName;
}
