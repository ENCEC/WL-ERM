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
@Table(name = "schedule_context")
public class ScheduleContext extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    @Id
    @Column(name = "id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long id;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "last_execute_time")
    private Date lastExecuteTime;

    @Column(name = "next_execute_time")
    private Date nextExecuteTime;

    @Column(name = "schedule_code")
    private String scheduleCode;

    @Column(name = "schedule_status")
    private Boolean scheduleStatus;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "worker_servers")
    private String workerServers;

}