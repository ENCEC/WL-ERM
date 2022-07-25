package com.share.auth.model.vo;

import lombok.Data;

import java.util.List;

/**
 * 查询下级企业id条件VO
 * @author chenhy
 * @date 2021-6-28
 */
@Data
public class QuerySubordinateCompanyIdsConditionVO {

    /**
     * 企业id
     */
    private List<Long> companyIds;

    /**
     * 提供方代码
     */
    private String supplyCode;

    /**
     * 港口代码
     */
    private String s7;

    /**
     * 政府机构代码
     */
    private String mechanismCode;

    /**
     * 其他政府机构代码
     */
    private String m3;

    /**
     * 需方企业代码
     */
    private String requirementCode;

    /**
     * 港口代码
     */
    private String r5;


}
