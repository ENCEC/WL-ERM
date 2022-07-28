package com.share.auth.model.vo;

import lombok.Data;

/**
 * @author chenxf
 * @date 2020-11-03 13:59
 */
@Data
public class CompanyTreeTableQueryVO {

    /**
     * 每页限制行数
     */
    private Integer pageSize;

    /**
     * 当前页
     */
    private Integer currentPage;

    /** 企业名称 */
    private String companyNameCn;

    /** 物流交换代码*/
    private String companyCode;

    private String auditStatus;
    /** 启用/禁用状态*/
    private Boolean isValid;

    private Long uemCompanyId;

    /**组织机构代码*/
    private String orgCode;
    /**来源*/
    private String dataSource;

}
