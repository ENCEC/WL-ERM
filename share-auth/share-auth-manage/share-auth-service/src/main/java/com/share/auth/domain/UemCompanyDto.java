package com.share.auth.domain;

import com.share.auth.model.entity.UemCustomerType;
import com.share.auth.model.vo.UemCompanyCargoTypeDTO;
import io.swagger.annotations.Api;
import lombok.Data;

import java.util.List;

/**
 * @date 20201021
 * @author xrp
 * */
@Api("企业信息表")
@Data
public class UemCompanyDto {

    /**
     * 用户表ID
     * */
    private String uemUserId;


    /**
     * 物流交换代码
     * */
    private String companyCode;

    /**
     * 企业或机构类型
     * */
    private String organizationType;

    /**
     * 企业中文名称
     * */
    private String companyNameCn;

    /**
     * 企业简称
     * */
    private String companyAbbreviName;

    /**法人类型*/
    private String legalType;

    /**法人名称*/
    private String legalName;

    /**法人身份证*/
    private String legalCard;

    /**企业联系人*/
    private String contact;

    /**联系人手机*/
    private String contactTel;

    /**联系人邮箱*/
    private String contactMail;

    /**统一社会信用代码或机构代码*/
    private String organizationCode;

    /**企业证书*/
    private String fileUrlId;

    /**所在地区 国家*/
    private String locCountryName;

    /**所在地区 省份*/
    private String locProvinceName;

    /**所在地区 城市*/
    private String locCityName;

    /**所在地区 地区*/
    private String locDistrictName;

    /**详细地址*/
    private String locAddress;

    /**企业电话*/
    private String companyTel;

    /**上级企业*/
    private String belongCompany;

    /**查看下级数据*/
    private Boolean isSuperior;

    /**用户类型  物流用户提供方*/
    private String companyTypeCode;

    /**用户类型  物流用户需求方*/
    private String selectedItemCode;

    /**审批状态（0待审批，1审批通过，2审批拒绝）*/
    private String auditStatus;

    List<UemCustomerType> uemCustomerTypeList;
    /**上级企业名称*/
    private String belongCompanyName;
    /**国家code*/
    private String locCountryCode;
    /**省份Code*/
    private String locProvinceCode;
    /**城市code*/
    private String locCityCode;
    /**县区code*/
    private String locDistrictCode;
    /**是否重点企业（0否，1是）*/
    private Boolean isFocusCompany;

    List<UemCompanyCargoTypeDTO> uemCompanyCargoTypeDTOList;



}
