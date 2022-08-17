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
@Table(name = "uem_user_project")
public class UemUserProject extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    @Id
    @Column(name = "uem_user_project_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long uemUserProjectId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "creator_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long creatorId;

    @Column(name = "creator_name")
    private String creatorName;

    @Column(name = "modifier_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long modifierId;

    @Column(name = "modifier_name")
    private String modifierName;

    @Column(name = "modify_time")
    private Date modifyTime;

    @Column(name = "record_version")
    private Integer recordVersion;

    /**项目id*/
    @Column(name = "uem_project_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemProjectId;

    /**用户ID*/
    @Column(name = "uem_user_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemUserId;

}
