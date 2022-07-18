package com.share.auth.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import com.share.auth.domain.UemCompanyRoleVO;
import com.share.auth.domain.UemCustomerTypeDTO;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author chenxf
 * @date 2020-11-03 14:08
 */
@Data
public class QueryCompanyTreeTableDTO implements Serializable{

    private static final long serialVersionUID = 1;
    /**id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemCompanyId;

    /**审批客服*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long auditor;

    /**审批备注*/
    private String auditRemark;

    /**审批状态（0待审批，1审批通过，2审批拒绝）*/
    private String auditStatus;

    /**审批时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date auditTime;

    /**上级企业*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long belongCompany;

    /**上级企业名称*/
    private String belongCompanyName;

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

    /**创建时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**创建人id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long creatorId;

    /**创建人名称*/
    private String creatorName;

    /**数据来源（0用户新增，2客服新增）*/
    private String dataSource;

    /**企业证书上传地址*/
    private String fileUrlId;

    /**启/禁用时间*/
//    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date invalidTime;

    /**是否可以查看下级组织数据（0否，1是）*/
    private Boolean isSuperior;

    /**是否禁用(0启用,1禁用)*/
    private Boolean isValid;

    /**法人身份证号*/
    private String legalCard;

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

    private String locProvinceCode;

    /**所在省名称*/
    private String locProvinceName;

    /**助记码*/
    private String memoryCode;

    /**修改人id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long modifierId;

    /**修改人名称*/
    private String modifierName;

    /**修改时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date modifyTime;

    /**组织机构代码*/
    private String organizationCode;

    /**企业或机构类型*/
    private String organizationType;

    /**版本号*/
    private Integer recordVersion;

    /**企业评分*/
    private Integer score;
    /**组织机构代码*/
    private String orgCode;

    /**企业历史信息ID*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemCompanyHistoryId;

    /**是否重点企业（0否，1是）*/
    private Boolean isFocusCompany;

    private Boolean haveChildCompany;

    private List<QueryCompanyTreeTableDTO> childrenList;

    private List<UemCustomerTypeDTO> uemCustomerTypeList;

    private List<UemCompanyCargoTypeDTO> uemCompanyCargoTypeDTOList;

    /**企业权限配置列表*/
    private List<UemCompanyRoleVO> uemCompanyRoleVoList;

}
