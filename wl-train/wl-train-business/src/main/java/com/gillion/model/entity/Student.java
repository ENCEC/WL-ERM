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
import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @author DaoServiceGenerator
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "student")
public class Student extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1;

    /**学生id*/
    @Id
    @Column(name = "id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long id;

    /**学生姓名*/
    @Column(name = "name")
    private String name;

    /**密码*/
    @Column(name = "password")
    private String password;

    /**确认密码*/
    @Column(name = "confirm_password")
    private String confirmPassword;

    /**学生性别*/
    @Column(name = "sex")
    private Integer sex;

    /**邮箱*/
    @Column(name = "email")
    private String email;

    /**移动电话*/
    @Column(name = "mobile")
    private String mobile;

    /**学费*/
    @Column(name = "money")
    private BigDecimal money;

    /**分数*/
    @Column(name = "score")
    private Integer score;

    /**学生年龄*/
    @Column(name = "age")
    private Integer age;

    /**入学日期*/
    @Column(name = "enrollment")
    private Date enrollment;

    /**出生日期*/
    @Column(name = "birthday")
    private Date birthday;

    /**老师ID*/
    @Column(name = "teacher_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long teacherId;

    /**课程ID*/
    @Column(name = "course_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long courseId;

    /**创建人*/
    @Column(name = "creator")
    private String creator;

    /**创建时间*/
    @Column(name = "create_time")
    private Date createTime;

    /**修改人*/
    @Column(name = "modifier")
    private String modifier;

    /**修改时间*/
    @Column(name = "modify_time")
    private Date modifyTime;

    /**乐观锁*/
    @Column(name = "version")
    private Integer version;

    /**籍贯*/
    @Column(name = "address")
    private String address;

    /**逻辑删除*/
    @Column(name = "destroyed")
    private Boolean destroyed;

    /**用户属性字段*/
    @Column(name = "user_attribute")
    private String userAttribute;

}