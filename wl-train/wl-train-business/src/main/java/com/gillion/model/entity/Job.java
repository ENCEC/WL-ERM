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
@Table(name = "job")
public class Job extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    @Id
    @Column(name = "id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long id;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "cron")
    private String cron;

    @Column(name = "description")
    private String description;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "schedule_code")
    private String scheduleCode;

    @Column(name = "schedule_type")
    private Boolean scheduleType;

    @Column(name = "shard_count")
    private Integer shardCount;

    @Column(name = "thread_type")
    private Boolean threadType;

    @Column(name = "timeout")
    private Integer timeout;

    @Column(name = "update_time")
    private Date updateTime;

}