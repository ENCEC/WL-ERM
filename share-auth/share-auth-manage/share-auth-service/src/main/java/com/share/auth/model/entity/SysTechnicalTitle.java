package com.share.auth.model.entity;

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
@Table(name = "sys_technical_title")
public class SysTechnicalTitle extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    /**岗位职称ID*/
    @Id
    @Column(name = "technical_title_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long technicalTitleId;

    /**创建者id*/
    @Column(name = "creator_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long creatorId;

    /**创建者*/
    @Column(name = "creator_name")
    private String creatorName;

    /**创建时间*/
    @Column(name = "create_time")
    private Date createTime;

    /**岗位ID*/
    @Column(name = "post_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long postId;

    /**备注*/
    @Column(name = "remark")
    private String remark;

    /**工龄*/
    @Column(name = "seniority")
    private String seniority;

    /**状态（0正常 1停用）*/
    @Column(name = "status")
    private String status;

    /**职称名称*/
    @Column(name = "technical_name")
    private String technicalName;

    /**更新者id*/
    @Column(name = "modifier_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long modifierId;

    /**更新者*/
    @Column(name = "modifier_name")
    private String modifierName;

    /**更新时间*/
    @Column(name = "modify_time")
    private Date modifyTime;

}