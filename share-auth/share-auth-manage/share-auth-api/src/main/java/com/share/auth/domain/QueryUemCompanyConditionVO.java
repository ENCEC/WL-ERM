package com.share.auth.domain;

import lombok.Data;

import java.util.List;

/**
 * 查询企业条件VO
 * @author chenhy
 * @date 2021-06-10
 */
@Data
public class QueryUemCompanyConditionVO {

    /**
     * 企业名称关键字，模糊查询
     */
    private String keyword;
    /**
     * 企业类型
     */
    private String companyTypeCode;
    /**
     * 企业类型选中项
     */
    private List<String> itemCodes;
    /**
     * 页码
     */
    private Integer currentPage;
    /**
     * 每页数量
     */
    private Integer pageSize;
    /**
     * 排除查询的企业id
     */
    private List<Long> notInUemCompanyIds;
}
