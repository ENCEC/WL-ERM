package com.share.support.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author:chenxf
 * @Description: 企业信息表
 * @Date: 15:56 2020/12/10
 * @Param: 
 * @Return:
 *
 */
public class Company  implements Serializable {
    private static final long serialVersionUID = 1;

    /**id*/
    @JsonSerialize(using = ToStringSerializer.class)
    private Long uemCompanyId;

    /**审批客服*/
    @JsonSerialize(using = ToStringSerializer.class)
    private Long auditor;

    /**审批备注*/
    private String auditRemark;

    /**审批状态（0待审批，1审批通过，2审批拒绝）*/
    private String auditStatus;

    /**审批时间*/
    private Date auditTime;

    /**上级企业*/
    @JsonSerialize(using = ToStringSerializer.class)
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
    private Date createTime;

    /**创建人id*/
    @JsonSerialize(using = ToStringSerializer.class)
    private Long creatorId;

    /**创建人名称*/
    private String creatorName;

    /**数据来源（0用户新增，2客服新增）*/
    private String dataSource;

    /**企业证书上传地址*/
    private String fileUrlId;

    /**启/禁用时间*/
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

    /**所在省*/
    private String locProvinceCode;

    /**所在省名称*/
    private String locProvinceName;

    /**助记码*/
    private String memoryCode;

    /**修改人id*/
    @JsonSerialize(using = ToStringSerializer.class)
    private Long modifierId;

    /**修改人名称*/
    private String modifierName;

    /**修改时间*/
    private Date modifyTime;

    /**组织机构代码*/
    private String organizationCode;

    /**企业或机构类型*/
    private String organizationType;

    private String organizationTypeName;

    /**版本号*/
    private Integer recordVersion;

    /**企业评分*/
    private Integer score;

    /**最顶层企业id*/
    @JsonSerialize(using = ToStringSerializer.class)
    private Long topCompany;

    /**企业历史信息ID*/
    @JsonSerialize(using = ToStringSerializer.class)
    private Long uemCompanyHistoryId;

    /**下级企业*/
    private List<Company> childrenCompanyList;

    public Long getUemCompanyId() {
        return uemCompanyId;
    }

    public void setUemCompanyId(Long uemCompanyId) {
        this.uemCompanyId = uemCompanyId;
    }

    public Long getAuditor() {
        return auditor;
    }

    public void setAuditor(Long auditor) {
        this.auditor = auditor;
    }

    public String getAuditRemark() {
        return auditRemark;
    }

    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Long getBelongCompany() {
        return belongCompany;
    }

    public void setBelongCompany(Long belongCompany) {
        this.belongCompany = belongCompany;
    }

    public String getBelongCompanyName() {
        return belongCompanyName;
    }

    public void setBelongCompanyName(String belongCompanyName) {
        this.belongCompanyName = belongCompanyName;
    }

    public String getCompanyAbbreviName() {
        return companyAbbreviName;
    }

    public void setCompanyAbbreviName(String companyAbbreviName) {
        this.companyAbbreviName = companyAbbreviName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyNameCn() {
        return companyNameCn;
    }

    public void setCompanyNameCn(String companyNameCn) {
        this.companyNameCn = companyNameCn;
    }

    public String getCompanyNameEn() {
        return companyNameEn;
    }

    public void setCompanyNameEn(String companyNameEn) {
        this.companyNameEn = companyNameEn;
    }

    public String getCompanyTel() {
        return companyTel;
    }

    public void setCompanyTel(String companyTel) {
        this.companyTel = companyTel;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactMail() {
        return contactMail;
    }

    public void setContactMail(String contactMail) {
        this.contactMail = contactMail;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getFileUrlId() {
        return fileUrlId;
    }

    public void setFileUrlId(String fileUrlId) {
        this.fileUrlId = fileUrlId;
    }

    public Date getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }

    public Boolean getIsSuperior() {
        return isSuperior;
    }

    public void setIsSuperior(Boolean superior) {
        isSuperior = superior;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean valid) {
        isValid = valid;
    }

    public String getLegalCard() {
        return legalCard;
    }

    public void setLegalCard(String legalCard) {
        this.legalCard = legalCard;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getLegalType() {
        return legalType;
    }

    public void setLegalType(String legalType) {
        this.legalType = legalType;
    }

    public String getLocAddress() {
        return locAddress;
    }

    public void setLocAddress(String locAddress) {
        this.locAddress = locAddress;
    }

    public String getLocCityCode() {
        return locCityCode;
    }

    public void setLocCityCode(String locCityCode) {
        this.locCityCode = locCityCode;
    }

    public String getLocCityName() {
        return locCityName;
    }

    public void setLocCityName(String locCityName) {
        this.locCityName = locCityName;
    }

    public String getLocCountryCode() {
        return locCountryCode;
    }

    public void setLocCountryCode(String locCountryCode) {
        this.locCountryCode = locCountryCode;
    }

    public String getLocCountryName() {
        return locCountryName;
    }

    public void setLocCountryName(String locCountryName) {
        this.locCountryName = locCountryName;
    }

    public String getLocDistrictCode() {
        return locDistrictCode;
    }

    public void setLocDistrictCode(String locDistrictCode) {
        this.locDistrictCode = locDistrictCode;
    }

    public String getLocDistrictName() {
        return locDistrictName;
    }

    public void setLocDistrictName(String locDistrictName) {
        this.locDistrictName = locDistrictName;
    }

    public String getLocProvinceCode() {
        return locProvinceCode;
    }

    public void setLocProvinceCode(String locProvinceCode) {
        this.locProvinceCode = locProvinceCode;
    }

    public String getLocProvinceName() {
        return locProvinceName;
    }

    public void setLocProvinceName(String locProvinceName) {
        this.locProvinceName = locProvinceName;
    }

    public String getMemoryCode() {
        return memoryCode;
    }

    public void setMemoryCode(String memoryCode) {
        this.memoryCode = memoryCode;
    }

    public Long getModifierId() {
        return modifierId;
    }

    public void setModifierId(Long modifierId) {
        this.modifierId = modifierId;
    }

    public String getModifierName() {
        return modifierName;
    }

    public void setModifierName(String modifierName) {
        this.modifierName = modifierName;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
    }

    public String getOrganizationTypeName() {
        return organizationTypeName;
    }

    public void setOrganizationTypeName(String organizationTypeName) {
        this.organizationTypeName = organizationTypeName;
    }

    public Integer getRecordVersion() {
        return recordVersion;
    }

    public void setRecordVersion(Integer recordVersion) {
        this.recordVersion = recordVersion;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Long getTopCompany() {
        return topCompany;
    }

    public void setTopCompany(Long topCompany) {
        this.topCompany = topCompany;
    }

    public Long getUemCompanyHistoryId() {
        return uemCompanyHistoryId;
    }

    public void setUemCompanyHistoryId(Long uemCompanyHistoryId) {
        this.uemCompanyHistoryId = uemCompanyHistoryId;
    }

    public List<Company> getChildrenCompanyList() {
        return childrenCompanyList;
    }

    public void setChildrenCompanyList(List<Company> childrenCompanyList) {
        this.childrenCompanyList = childrenCompanyList;
    }
}