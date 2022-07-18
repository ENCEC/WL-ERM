package com.share.auth.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;

import java.io.Serializable;


/**
 * 企业用户类型信息
 * @author chenhy
 * @date 20210625
 */
@Data
public class CompanyCustomTypeVO implements Serializable {

    private static final long serialVersionUID = 1;

    /**企业id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemCompanyId;

    /**上级企业*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long belongCompany;

    /**上级企业名称*/
    private String belongCompanyName;

    /**承运商类型（0：注册企业；1：非注册企业）*/
    private String carrierType;

    /**企业简称*/
    private String companyAbbreviName;

    /**物流交换代码*/
    private String companyCode;

    /**企业中文名称*/
    private String companyNameCn;

    /**企业英文名称*/
    private String companyNameEn;

    /**企业电话*/
    private String companyTel;

    /**企业联系人*/
    private String contact;

    /**联系人邮箱*/
    private String contactMail;

    /**联系人手机*/
    private String contactTel;

    /**数据来源（0用户新增，2客服新增）*/
    private String dataSource;

    /**是否禁用(1启用,0禁用)*/
    private Boolean isValid;

    /**法人名称*/
    private String legalName;

    /**法人类型*/
    private String legalType;

    /**详细地址*/
    private String locAddress;

    /**所在城市*/
    private String locCityCode;

    /**城市名称*/
    private String locCityName;

    /**所在国家*/
    private String locCountryCode;

    /**国家名称*/
    private String locCountryName;

    /**所在区/县*/
    private String locDistrictCode;

    /**区/县名称*/
    private String locDistrictName;

    /**所在省*/
    private String locProvinceCode;

    /**所在省名称*/
    private String locProvinceName;

    /**助记码*/
    private String memoryCode;

    /**组织机构代码*/
    private String organizationCode;

    /**企业或机构类型*/
    private String organizationType;

    /**最顶层企业id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long topCompany;

    /**id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemCustomerTypeId;

    /**企业类型code*/
    private String companyTypeCode;

    /**企业类型选中项code*/
    private String selectedItemCode;

}
