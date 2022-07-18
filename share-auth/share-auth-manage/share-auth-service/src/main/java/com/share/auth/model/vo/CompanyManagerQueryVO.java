package com.share.auth.model.vo;

import lombok.Data;

/**
 * @author chenxf
 * @date 2020-10-30 15:28
 */
@Data
public class CompanyManagerQueryVO {
    /**
     * 每页限制行数
     */
    private Integer pageSize;

    /**
     * 当前页
     */
    private Integer currentPage;

    /** 用户名*/
    private String account;

    /** 姓名*/
    private String name;

    /** 绑定企业名称*/
    private String  companyName;

    /** 审批状态*/
    private String auditStatus;
}
