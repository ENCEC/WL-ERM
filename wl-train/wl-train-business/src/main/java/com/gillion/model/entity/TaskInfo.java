package com.gillion.model.entity;

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
@Table(name = "task_info")
public class TaskInfo extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    /**任务ID*/
    @Id
    @Column(name = "task_info_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long taskInfoId;

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

    /**实际完成日期*/
    @Column(name = "end_date")
    private Date endDate;

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

    /**父级任务ID*/
    @Column(name = "parent_task_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long parentTaskId;

    /**计划完成日期*/
    @Column(name = "plan_end_date")
    private Date planEndDate;

    /**计划开始日期*/
    @Column(name = "plan_start_date")
    private Date planStartDate;

    /**发布日期*/
    @Column(name = "publish_date")
    private Date publishDate;

    /**版本号*/
    @Column(name = "record_version")
    private Integer recordVersion;

    /**实际开始日期*/
    @Column(name = "start_date")
    private Date startDate;

    /**任务状态（0：待完成 1：执行中 2：已完成）*/
    @Column(name = "status")
    private Integer status;

    /**任务标题*/
    @Column(name = "task_title")
    private String taskTitle;

    /**任务类型*/
    @Column(name = "task_type")
    private String taskType;

}