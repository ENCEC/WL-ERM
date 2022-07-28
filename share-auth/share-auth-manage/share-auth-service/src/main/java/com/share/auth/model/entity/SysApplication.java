package com.share.auth.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ds.entity.base.BaseModel;
import com.gillion.ec.core.annotations.Generator;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * @author DaoServiceGenerator
 */
@SuppressWarnings("JpaDataSourceORMInspection")
        @EqualsAndHashCode(callSuper = true)
    @Data
    @Entity
@Table(name = "sys_application")
public class SysApplication extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    /**id*/
    @Id
    @Column(name = "sys_application_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long sysApplicationId;

    /**应用简称*/
    @Column(name = "application_abbrevi_name")
    private String applicationAbbreviName;

    /**应用代码*/
    @Column(name = "application_code")
    private String applicationCode;

    /**应用名称*/
    @Column(name = "application_name")
    private String applicationName;

    /**应用描述*/
    @Column(name = "application_remark")
    private String applicationRemark;

    /**应用密钥*/
    @Column(name = "application_secret")
    private String applicationSecret;

    /**应用地址*/
    @Column(name = "application_url")
    private String applicationUrl;

    /**维护联系人*/
    @Column(name = "contact")
    private String contact;

    /**维护联系人手机*/
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

    /**启/禁用时间*/
    @Column(name = "invalid_time")
    private Date invalidTime;

    /**是否禁用(0禁用,1启用)*/
    @Column(name = "is_valid")
    private Boolean isValid;

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

    /**版本号*/
    @Column(name = "record_version")
    private Integer recordVersion;

    /**维护企业*/
    @Column(name = "related_enterprise")
    private String relatedEnterprise;

}