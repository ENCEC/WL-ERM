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
@Table(name = "timer_version")
public class TimerVersion extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1;

    /**定时器id*/
    @Id
    @Column(name = "timer_id")
    @Generator("snowFlakeGenerator")
    private Integer timerId;

    /**当前实例标识*/
    @Column(name = "identifier")
    private String identifier;

    /**定时器版本， 当前时间／定时间隔*/
    @Column(name = "version")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long version;

    /**修改时间*/
    @Column(name = "modify_time")
    private Date modifyTime;

}