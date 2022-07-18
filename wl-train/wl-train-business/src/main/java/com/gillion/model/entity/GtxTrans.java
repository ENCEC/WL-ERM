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
import java.lang.Short;
import java.lang.String;
import java.util.Date;


/**
 * @author DaoServiceGenerator
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "gtx_trans")
public class GtxTrans extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1;

    /**主键*/
    @Id
    @Column(name = "gtx_trans_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long gtxTransId;

    /**事务名称*/
    @Column(name = "trans_name")
    private String transName;

    /**事务ID，全局唯一*/
    @Column(name = "xid")
    private String xid;

    /**当前实例标识*/
    @Column(name = "identifier")
    private String identifier;

    @Column(name = "participants")
    private String participants;

    /**事务状态*/
    @Column(name = "tx_status")
    private Short txStatus;

    @Column(name = "tx_mode")
    private Short txMode;

    /**事务创建时间*/
    @Column(name = "tx_create_time")
    private Date txCreateTime;

    /**修改时间*/
    @Column(name = "modify_time")
    private Date modifyTime;

    @Column(name = "modify_time_sec")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long modifyTimeSec;

    /**恢复次数*/
    @Column(name = "try_count")
    private Short tryCount;

}