package com.share.support.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author:chenxf
 * @Description: 角色信息表
 * @Date: 15:56 2020/12/10
 * @Param:
 * @Return:
 */
public class Role implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long sysRoleId;

//    /**创建时间*/
//    private Date createTime;
//
//    /**创建人id*/
//    @JsonSerialize(using = ToStringSerializer.class)
//    private Long creatorId;
//
//    /**创建人名称*/
//    private String creatorName;

    /**
     * 启/禁用时间
     */
    private Date invalidTime;

    /**
     * 是否禁用(0禁用,1启用)
     */
    private Boolean isValid;

//    /**修改人id*/
//    @JsonSerialize(using = ToStringSerializer.class)
//    private Long modifierId;
//
//    /**修改人名称*/
//    private String modifierName;
//
//    /**修改时间*/
//    private Date modifyTime;
//
//    /**版本号*/
//    private Integer recordVersion;

//    /**角色描述*/
//    private String remark;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编码
     */
    private String roleCode;

//    /**父级角色*/
//    @JsonSerialize(using = ToStringSerializer.class)
//    private Long rolePid;
//
//    /**关联应用id*/
//    @JsonSerialize(using = ToStringSerializer.class)
//    private Long sysApplicationId;
//
//    /**最顶层角色id*/
//    @JsonSerialize(using = ToStringSerializer.class)
//    private Long topRoleId;

    public Long getSysRoleId() {
        return sysRoleId;
    }

    public void setSysRoleId(Long sysRoleId) {
        this.sysRoleId = sysRoleId;
    }

//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }
//
//    public Long getCreatorId() {
//        return creatorId;
//    }
//
//    public void setCreatorId(Long creatorId) {
//        this.creatorId = creatorId;
//    }
//
//    public String getCreatorName() {
//        return creatorName;
//    }
//
//    public void setCreatorName(String creatorName) {
//        this.creatorName = creatorName;
//    }

    public Date getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

//    public Long getModifierId() {
//        return modifierId;
//    }
//
//    public void setModifierId(Long modifierId) {
//        this.modifierId = modifierId;
//    }
//
//    public String getModifierName() {
//        return modifierName;
//    }
//
//    public void setModifierName(String modifierName) {
//        this.modifierName = modifierName;
//    }
//
//    public Date getModifyTime() {
//        return modifyTime;
//    }
//
//    public void setModifyTime(Date modifyTime) {
//        this.modifyTime = modifyTime;
//    }
//
//    public Integer getRecordVersion() {
//        return recordVersion;
//    }
//
//    public void setRecordVersion(Integer recordVersion) {
//        this.recordVersion = recordVersion;
//    }
//
//    public String getRemark() {
//        return remark;
//    }
//
//    public void setRemark(String remark) {
//        this.remark = remark;
//    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

//    public Long getRolePid() {
//        return rolePid;
//    }
//
//    public void setRolePid(Long rolePid) {
//        this.rolePid = rolePid;
//    }
//
//    public Long getSysApplicationId() {
//        return sysApplicationId;
//    }
//
//    public void setSysApplicationId(Long sysApplicationId) {
//        this.sysApplicationId = sysApplicationId;
//    }
//
//    public Long getTopRoleId() {
//        return topRoleId;
//    }
//
//    public void setTopRoleId(Long topRoleId) {
//        this.topRoleId = topRoleId;
//    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}
