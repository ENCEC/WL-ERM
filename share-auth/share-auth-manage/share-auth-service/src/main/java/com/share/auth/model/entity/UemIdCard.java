package com.share.auth.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ds.entity.base.BaseModel;
import com.gillion.ec.core.annotations.Generator;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @author DaoServiceGenerator
 */
@SuppressWarnings("JpaDataSourceORMInspection")
        @EqualsAndHashCode(callSuper = true)
    @Data
    @Entity
@Table(name = "uem_id_card")
public class UemIdCard extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    /**id*/
    @Id
    @Column(name = "uem_id_card_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long uemIdCardId;

    /**审批客服*/
    @Column(name = "auditor")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long auditor;

    /**审批备注*/
    @Column(name = "audit_remark")
    private String auditRemark;

    /**审批状态（0待审批，1审批通过，2审批失败）*/
    @Column(name = "audit_status")
    private String auditStatus;

    /**审批时间*/
    @Column(name = "audit_time")
    private Date auditTime;

    /**身份证反面图片地址id*/
    @Column(name = "card_back_url_id")
    private String cardBackUrlId;

    /**身份证正面图片地址id*/
    @Column(name = "card_positive_url_id")
    private String cardPositiveUrlId;

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

    /**身份证号码*/
    @Column(name = "id_card")
    private String idCard;

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

    /**姓名*/
    @Column(name = "name")
    private String name;

    /**版本号*/
    @Column(name = "record_version")
    @Version
    private Integer recordVersion;

    /**性别（0男，1女）*/
    @Column(name = "sex")
    private Boolean sex;

    /**关联用户id*/
    @Column(name = "uem_user_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemUserId;

}