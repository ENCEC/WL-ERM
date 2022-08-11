package com.gillion.model.entity;

import com.gillion.ds.entity.base.BaseModel;
import com.gillion.ec.core.annotations.Generator;
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
@Table(name = "sys_acl_table")
public class SysAclTable extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    @Id
    @Column(name = "acl_table_id")
    @Generator("snowFlakeGenerator")
    private Integer aclTableId;

    @Column(name = "acl_mode")
    private String aclMode;

    @Column(name = "acl_type")
    private Byte aclType;

    @Column(name = "creater_id")
    private Integer createrId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "updater_id")
    private Integer updaterId;

    @Column(name = "update_time")
    private Date updateTime;

}