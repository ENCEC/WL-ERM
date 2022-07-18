package com.share.auth.center.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.gillion.ec.core.annotations.Generator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import com.gillion.ds.entity.base.BaseModel;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Date;


/**
 * @author DaoServiceGenerator
 */
@SuppressWarnings("JpaDataSourceORMInspection")
        @EqualsAndHashCode(callSuper = true)
    @Data
    @Entity
@Table(name = "uem_company")
public class UemCompany extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    /**id*/
    @Id
    @Column(name = "uem_company_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long uemCompanyId;

    /**审批客服*/
    @Column(name = "auditor")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long auditor;

    /**审批备注*/
    @Column(name = "audit_remark")
    private String auditRemark;

    /**审批状态（0待审批，1审批通过，2审批拒绝）*/
    @Column(name = "audit_status")
    private String auditStatus;

    /**审批时间*/
    @Column(name = "audit_time")
    private Date auditTime;

    /**上级企业*/
    @Column(name = "belong_company")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long belongCompany;

    /**上级企业名称*/
    @Column(name = "belong_company_name")
    private String belongCompanyName;

    /**承运商类型（0：注册企业；1：非注册企业）*/
    @Column(name = "carrier_type")
    private String carrierType;

    /**企业简称*/
    @Column(name = "company_abbrevi_name")
    private String companyAbbreviName;

    /**物流交换代码*/
    @Column(name = "company_code")
    private String companyCode;

    /**企业中文名称*/
    @Column(name = "company_name_cn")
    private String companyNameCn;

    /**企业英文名称*/
    @Column(name = "company_name_en")
    private String companyNameEn;

    /**企业电话*/
    @Column(name = "company_tel")
    private String companyTel;

    /**企业联系人*/
    @Column(name = "contact")
    private String contact;

    /**联系人邮箱*/
    @Column(name = "contact_mail")
    private String contactMail;

    /**联系人手机*/
    @Column(name = "contact_tel")
    private String contactTel;

    /**创建时间*/
    @Column(name = "create_time")
    private Date createTime;

    /**创建人id*/
    @Column(name = "creator_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long creatorId;

    /**创建人名称*/
    @Column(name = "creator_name")
    private String creatorName;

    /**数据来源（0用户新增，2客服新增，3-国家综合交通运输信息平台）*/
    @Column(name = "data_source")
    private String dataSource;

    /**企业证书上传地址*/
    @Column(name = "file_url_id")
    private String fileUrlId;

    /**国际货运代理人工商登记材料*/
    @Column(name = "international_business_file_url")
    private String internationalBusinessFileUrl;

    /**启/禁用时间*/
    @Column(name = "invalid_time")
    private Date invalidTime;

    /**是否重点企业（0否，1是）*/
    @Column(name = "is_focus_company")
    private Boolean isFocusCompany;

    /**是否可以查看下级组织数据（0否，1是）*/
    @Column(name = "is_superior")
    private Boolean isSuperior;

    /**是否禁用(1启用,0禁用)*/
    @Column(name = "is_valid")
    private Boolean isValid;

    /**法人身份证号*/
    @Column(name = "legal_card")
    private String legalCard;

    /**法人名称*/
    @Column(name = "legal_name")
    private String legalName;

    /**法人类型*/
    @Column(name = "legal_type")
    private String legalType;

    /**详细地址*/
    @Column(name = "loc_address")
    private String locAddress;

    /**所在城市*/
    @Column(name = "loc_city_code")
    private String locCityCode;

    /**城市名称*/
    @Column(name = "loc_city_name")
    private String locCityName;

    /**所在国家*/
    @Column(name = "loc_country_code")
    private String locCountryCode;

    /**国家名称*/
    @Column(name = "loc_country_name")
    private String locCountryName;

    /**所在区/县*/
    @Column(name = "loc_district_code")
    private String locDistrictCode;

    /**区/县名称*/
    @Column(name = "loc_district_name")
    private String locDistrictName;

    /**所在省*/
    @Column(name = "loc_province_code")
    private String locProvinceCode;

    /**所在省名称*/
    @Column(name = "loc_province_name")
    private String locProvinceName;

    /**助记码*/
    @Column(name = "memory_code")
    private String memoryCode;

    /**修改人id*/
    @Column(name = "modifier_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long modifierId;

    /**修改人名称*/
    @Column(name = "modifier_name")
    private String modifierName;

    /**修改时间*/
    @Column(name = "modify_time")
    private Date modifyTime;

    /**组织机构代码*/
    @Column(name = "organization_code")
    private String organizationCode;

    /**企业或机构类型*/
    @Column(name = "organization_type")
    private String organizationType;

    /**组织机构代码*/
    @Column(name = "org_code")
    private String orgCode;

    /**组织顺序*/
    @Column(name = "org_seq")
    private String orgSeq;

    /**版本号*/
    @Column(name = "record_version")
    @Version
    private Integer recordVersion;

    /**企业评分*/
    @Column(name = "score")
    private Integer score;

    /**最顶层企业id*/
    @Column(name = "top_company")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long topCompany;

    /**企业历史信息ID*/
    @Column(name = "uem_company_history_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemCompanyHistoryId;

}