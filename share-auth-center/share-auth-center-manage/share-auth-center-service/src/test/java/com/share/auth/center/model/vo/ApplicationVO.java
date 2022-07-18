package com.share.auth.center.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 应用VO
 * @author wangcl
 * @date 20201021
 * @description 测试一对多查询
 */
@Data
public class ApplicationVO implements Serializable {
    /**用户id*/
    private Long uemUserId;
    /**用户名*/
    private String account;
    /**手机号*/
    private String mobile;
    /**邮箱*/
    private String email;
    /**用户来源*/
    private String source;
    /**来源应用*/
    private Long oriApplication;
    /**应用名称*/
    private String applicationName;
}
