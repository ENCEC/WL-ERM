package com.share.auth.model.vo;

import lombok.Data;

/**
 * @author chenxf
 * @date 2020-10-29 10:36
 */
@Data
public class CompanyCheckQueryVO {

    /**
     * 每页限制行数
     */
    private Integer pageSize;

    /**
     * 当前页
     */
    private Integer currentPage;

    /**
     * 企业中文名称
     */
    private String companyNameCn;

    /**
     * 企业物流交换代码
     */
    private String companyCode;
    /**
     * 审批状态
    */
    private String auditStatus;
}
