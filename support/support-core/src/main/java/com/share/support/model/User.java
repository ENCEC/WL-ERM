package com.share.support.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author:chenxf
 * @Description: 用户信息表
 * @Date: 15:56 2020/12/10
 * @Param:
 * @Return:
 */
public class User implements Serializable,com.gillion.eds.sso.IUser {
    private static final long serialVersionUID = 1;

    /**
     * id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long uemUserId;

    /**
     * 用户名
     */
    private String account;

    private String appCode;

    /**
     * 审批客服
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long auditor;

    /**
     * 审批备注
     */
    private String auditRemark;

    /**
     * 审批状态（0待审批，1审批通过，2审批失败）
     */
    private String auditStatus;

    /**
     * 审批时间
     */
    private Date auditTime;

    /**
     * 绑定企业
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long blindCompanny;

    /**
     * 绑定企业时间
     */
    private Date blindCompannyTime;

    /**
     * 身份证反面图片地址id
     */
    private String cardBackUrlId;

    /**
     * 身份证正面图片地址id
     */
    private String cardPositiveUrlId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long creatorId;

    /**
     * 创建人名称
     */
    private String creatorName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 政务账号绑定ID
     */
    private String gvmId;

    /**
     * 身份证号码
     */
    private String idCard;

    /**
     * 启/禁用时间
     */
    private Date invalidTime;

    /**
     * 是否同意协议(0不同意，1同意)
     */
    private Boolean isAgreemeent;

    /**
     * 是否显示（0显示，1隐藏）
     */
    private Boolean isDisplayed;

    /**
     * 是否禁用(0禁用,1启用)
     */
    private Boolean isValid;

    /**
     * 手机号
     */
    private String mobile;

    /**修改人id*/
    @JsonSerialize(using = ToStringSerializer.class)
    private Long modifierId;

    /**
     * 修改人名称
     */
    private String modifierName;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 姓名
     */
    private String name;

    /**
     * 来源应用
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long oriApplication;

    /**
     * 密码
     */
    private String password;

    /**
     * QQ绑定ID
     */
    private String qqId;

    /**
     * 版本号
     */
    private Integer recordVersion;

    /**
     * 用户评分
     */
    private Integer score;

    /**
     * 性别（0男，1女）
     */
    private Boolean sex;

    /**
     * 用户来源
     */
    private String source;

    /**
     * 实名信息ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long uemIdCardId;

    /**
     * 用户类型（0-普通用户，1-企业用户，2-企业管理员）
     */
    private String userType;

    /**
     * 微信绑定ID
     */
    private String wxId;
    /**
     * jwt解析出来的角色信息和企业信息
     */
    private Long sysRoleId;

    private String sysRoleName;

    private String roleCode;

    private String companyName;

    /**
     * 以下加个实体的信息需要另外调用账号权限接口查询之后才有数据
     */
    private Role role;

    /**
     * 角色列表
     */
    private List<Role> roleList;

    /**
     * 项目列表
     */
    private List<Project> projectList;

    private Company company;

    /**
     * 所属企业的下级企业
     */
    private List<Company> childrenCompanyList;

    /**
     * 当前访问应用id
     */
    private String clientId;

    /**
     * 当前访问应用角色对应关系
     */
    private Map<String, String> currentClientRoleMap;

    /**
     * 过期时间
     */
    private Long expireTime;

    public Long getUemUserId() {
        return uemUserId;
    }

    public void setUemUserId(Long uemUserId) {
        this.uemUserId = uemUserId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public Long getBlindCompanny() {
        return blindCompanny;
    }

    public void setBlindCompanny(Long blindCompanny) {
        this.blindCompanny = blindCompanny;
    }

    public Date getBlindCompannyTime() {
        return blindCompannyTime;
    }

    public void setBlindCompannyTime(Date blindCompannyTime) {
        this.blindCompannyTime = blindCompannyTime;
    }

    public String getCardBackUrlId() {
        return cardBackUrlId;
    }

    public void setCardBackUrlId(String cardBackUrlId) {
        this.cardBackUrlId = cardBackUrlId;
    }

    public String getCardPositiveUrlId() {
        return cardPositiveUrlId;
    }

    public void setCardPositiveUrlId(String cardPositiveUrlId) {
        this.cardPositiveUrlId = cardPositiveUrlId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGvmId() {
        return gvmId;
    }

    public void setGvmId(String gvmId) {
        this.gvmId = gvmId;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Date getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }

    public Boolean getIsAgreemeent() {
        return isAgreemeent;
    }

    public void setIsAgreemeent(Boolean agreemeent) {
        isAgreemeent = agreemeent;
    }

    public Boolean getIsDisplayed() {
        return isDisplayed;
    }

    public void setIsDisplayed(Boolean displayed) {
        isDisplayed = displayed;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean valid) {
        isValid = valid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOriApplication() {
        return oriApplication;
    }

    public void setOriApplication(Long oriApplication) {
        this.oriApplication = oriApplication;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQqId() {
        return qqId;
    }

    public void setQqId(String qqId) {
        this.qqId = qqId;
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

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getUemIdCardId() {
        return uemIdCardId;
    }

    public void setUemIdCardId(Long uemIdCardId) {
        this.uemIdCardId = uemIdCardId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Company> getChildrenCompanyList() {
        return childrenCompanyList;
    }

    public void setChildrenCompanyList(List<Company> childrenCompanyList) {
        this.childrenCompanyList = childrenCompanyList;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public Long getSysRoleId() {
        return sysRoleId;
    }

    public void setSysRoleId(Long sysRoleId) {
        this.sysRoleId = sysRoleId;
    }

    public String getSysRoleName() {
        return sysRoleName;
    }

    public void setSysRoleName(String sysRoleName) {
        this.sysRoleName = sysRoleName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Map<String, String> getCurrentClientRoleMap() {
        return currentClientRoleMap;
    }

    public void setCurrentClientRoleMap(Map<String, String> currentClientRoleMap) {
        this.currentClientRoleMap = currentClientRoleMap;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public Object getUserId() {
        return uemUserId;
    }

    @Override
    public String getUsername() {
        return name;
    }
}
