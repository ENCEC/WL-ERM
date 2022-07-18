package com.share.auth.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;

import java.io.Serializable;

/**
 * 企业信息VO
 * @author chenhy
 * @date 2021-05-31
 */
@Data
public class UemCompanyVO implements Serializable {

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

    /**是否重点企业（0否，1是）*/
    private Boolean isFocusCompany;

    /**审批状态（0待审批，1审批通过，2审批拒绝）*/
    private String auditStatus;

}
