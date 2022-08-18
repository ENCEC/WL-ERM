package com.gillion.model.entity;

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
@Table(name = "standard_entry")
public class StandardEntry extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    /**规范条目ID*/
    @Id
    @Column(name = "standard_entry_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long standardEntryId;

    /**执行周期*/
    @Column(name = "action_period")
    private Integer actionPeriod;

    /**执行说明*/
    @Column(name = "action_remark")
    private String actionRemark;

    /**执行角色ID*/
    @Column(name = "action_role_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long actionRoleId;

    /**执行序号*/
    @Column(name = "action_serial_num")
    private Integer actionSerialNum;

    /**执行时间*/
    @Column(name = "action_time")
    private Integer actionTime;

    /**适用岗位ID*/
    @Column(name = "apply_post_id")
    private String applyPostId;

    /**适用岗位职称*/
    @Column(name = "apply_professor_id")
    private String applyProfessorId;

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

    /**条目名称*/
    @Column(name = "entry_name")
    private String entryName;

    /**是否必须（0：是 1：否）*/
    @Column(name = "is_need")
    private Boolean isNeed;

    /**条目类型*/
    @Column(name = "item_type")
    private String itemType;

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

    /**统筹人ID列表*/
    @Column(name = "ordinator_id")
    private String ordinatorId;

    /**统筹人姓名列表*/
    @Column(name = "ordinator_name")
    private String ordinatorName;

    /**版本号*/
    @Column(name = "record_version")
    private Integer recordVersion;

    /**状态（0：启用 1：禁用）*/
    @Column(name = "status")
    private Boolean status;

}