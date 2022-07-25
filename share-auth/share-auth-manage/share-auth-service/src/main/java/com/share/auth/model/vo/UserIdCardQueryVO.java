package com.share.auth.model.vo;

import lombok.Data;

/**
 * @author chenxf
 * @date 2020-10-29 14:48
 */
@Data
public class UserIdCardQueryVO {

    /**
     * 每页限制行数
     */
    private Integer pageSize;

    /**
     * 当前页
     */
    private Integer currentPage;
    /**
     * 姓名
     */
    private String name;
    /**
     * 用户名
     */
    private String account;
    /**
     * 审批状态
     */
    private String auditStatus;
    /**
     * 手机号
     */
    private String mobile;

}
