package com.share.auth.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限申请VO
 * @author chenhy
 * @date 2021-05019
 */
@Data
public class UemUserPermissionVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemUserPermissionId;

    /**申请理由*/
    private String applyReason;

    /**审批人*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long auditor;

    /**审批备注*/
    private String auditRemark;

    /**审批状态（0待审批，1审批通过，2审批失败）*/
    private String auditStatus;

    /**审批时间*/
    private Date auditTime;

    /**创建时间*/
    private Date createTime;

    /**创建人id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long creatorId;

    /**创建人名称*/
    private String creatorName;

    /**修改人id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long modifierId;

    /**修改人名称*/
    private String modifierName;

    /**修改时间*/
    private Date modifyTime;

    /**版本号*/
    private Integer recordVersion;

    /**应用id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long sysApplicationId;

    /**关联用户id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemUserId;

    /**页码*/
    private Integer currentPage;

    /**每页数量*/
    private Integer pageSize;

    /**
     * 用户名
     */
    private String account;
    /**
     * 人员姓名
     */
    private String name;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 所属组织
     */
    private String companyNameCn;
    /**
     * 应用名称
     */
    private String applicationName;
    /**
     * getAuditStatus
     */
    private String auditorName;
    /**
     * 国交管理员审核名称
     */
    private String userAuditorName;
}
