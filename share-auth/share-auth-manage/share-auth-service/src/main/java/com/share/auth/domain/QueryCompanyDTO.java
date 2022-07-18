package com.share.auth.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chenxf
 * @date 2020-10-26 11:12
 */
@Data
public class QueryCompanyDTO  implements Serializable{
    private static final long serialVersionUID = 1;
    /**id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemCompanyId;

    /**物流交换代码*/
    private String companyCode;

    /**企业或机构类型*/
    private String organizationType;

    /**企业中文名称*/
    private String companyNameCn;

    /**企业简称*/
    private String companyAbbreviName;

    /**企业英文名称*/
    private String companyNameEn;

    /**组织机构代码*/
    private String organizationCode;

    /**助记码*/
    private String memoryCode;

    /**法人类型*/
    private String legalType;

    /**法人名称*/
    private String legalName;

    /**法人身份证号*/
    private String legalCard;

    /**企业联系人*/
    private String contact;

    /**联系人手机*/
    private String contactTel;

    /**国家名称*/
    private String locCountryName;

    /**城市名称*/
    private String locCityName;

    /**所在省名称*/
    private String locProvinceName;

    /**区/县名称*/
    private String locDistrictName;

    /**详细地址*/
    private String locAddress;
}
