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
@Table(name = "sys_tag")
public class SysTag extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    /**id*/
    @Id
    @Column(name = "sys_tag_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long sysTagId;

    /**标签名称*/
    @Column(name = "tag_name")
    private String tagName;

    /**标签描述*/
    @Column(name = "tag_description")
    private String tagDescription;

    /**是否禁用(0禁用,1启用)*/
    @Column(name = "status")
    private Boolean status;

    /**创建人id*/
    @Column(name = "creator_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long creatorId;

    /**创建人名称*/
    @Column(name = "creator_name")
    private String creatorName;

    /**创建时间*/
    @Column(name = "create_time")
    private Date createTime;

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

    /**逻辑删除flag*/
    @Column(name = "is_deleted")
    private Boolean isDeleted;

}