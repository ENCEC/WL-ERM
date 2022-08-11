package com.share.auth.model.entity;

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
@Table(name = "sys_post")
public class SysPost extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    /**岗位ID*/
    @Id
    @Column(name = "post_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long postId;

    /**创建者*/
    @Column(name = "create_by")
    private String createBy;

    /**创建时间*/
    @Column(name = "create_time")
    private Date createTime;

    /**岗位编码*/
    @Column(name = "post_code")
    private String postCode;

    /**岗位名称*/
    @Column(name = "post_name")
    private String postName;

    /**显示顺序*/
    @Column(name = "post_sort")
    private Integer postSort;

    /**备注*/
    @Column(name = "remark")
    private String remark;

    /**状态（0正常 1停用）*/
    @Column(name = "status")
    private String status;

    /**更新者*/
    @Column(name = "update_by")
    private String updateBy;

    /**更新时间*/
    @Column(name = "update_time")
    private Date updateTime;

}