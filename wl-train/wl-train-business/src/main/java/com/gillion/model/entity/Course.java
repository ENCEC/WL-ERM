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
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;


/**
 * @author DaoServiceGenerator
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "course")
public class Course extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1;

    /**ID*/
    @Id
    @Column(name = "id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long id;

    /**是否逻辑删除*/
    @Column(name = "destroyed")
    private Boolean destroyed;

    /**创建人*/
    @Column(name = "creator")
    private String creator;

    /**课程名称*/
    @Column(name = "course_name")
    private String courseName;

    /**修改时间*/
    @Column(name = "modify_time")
    private Date modifyTime;

    /**创建时间*/
    @Column(name = "create_time")
    private Date createTime;

    /**课程节数*/
    @Column(name = "course_num")
    private Integer courseNum;

    /**用户属性字段*/
    @Column(name = "user_attribute")
    private String userAttribute;

    /**修改人*/
    @Column(name = "modifier")
    private String modifier;

    /**乐观锁版本号*/
    @Column(name = "version")
    private Integer version;

    /**课程内容*/
    @Column(name = "course_content")
    private String courseContent;

}