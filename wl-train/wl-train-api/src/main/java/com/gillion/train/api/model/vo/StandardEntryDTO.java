package com.gillion.train.api.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.annotations.Generator;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName StandardEntryDTO
 * @Author weiq
 * @Date 2022/8/1 13:18
 * @Version 1.0
 **/
public class StandardEntryDTO implements Serializable {
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
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
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

    /**统筹人ID*/
    @Column(name = "ordinator_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private String ordinatorId;

    /**版本号*/
    @Column(name = "record_version")
    private Integer recordVersion;

    /**状态（0：启用 1：禁用）*/
    @Column(name = "status")
    private Boolean status;

    /**执行角色名称*/
    @Column(name = "actionRoleName")
    private String actionRoleName;

    /**执行岗位名称*/
    @Column(name = "applyPostName")
    private String applyPostName;
}
