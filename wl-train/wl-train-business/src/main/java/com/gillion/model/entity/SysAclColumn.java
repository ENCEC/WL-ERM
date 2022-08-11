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
@Table(name = "sys_acl_column")
public class SysAclColumn extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    @Id
    @Column(name = "acl_column_id")
    @Generator("snowFlakeGenerator")
    private Integer aclColumnId;

    @Column(name = "acl_table_id")
    private Integer aclTableId;

    @Column(name = "column_name")
    private String columnName;

    @Column(name = "creater_id")
    private Integer createrId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "remark")
    private String remark;

    @Column(name = "updater_id")
    private Integer updaterId;

    @Column(name = "update_time")
    private Date updateTime;

}