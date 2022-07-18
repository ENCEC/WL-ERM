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
@Table(name = "employee")
public class Employee extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1;

    /**员工主键*/
    @Id
    @Column(name = "id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long employeeId;

    /**员工用户名*/
    @Column(name = "name")
    private String name;

    /**员工中文名称*/
    @Column(name = "cname")
    private String cname;

    /**密码*/
    @Column(name = "password")
    private String password;

    /**年龄*/
    @Column(name = "age")
    private Short age;

    /**性别*/
    @Column(name = "gender")
    private Boolean gender;

    /**邮箱*/
    @Column(name = "email")
    private String email;

    /**手机号码*/
    @Column(name = "mobile")
    private String mobile;

    /**地址*/
    @Column(name = "address")
    private String address;

    /**从属部门编号*/
    @Column(name = "department_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long departmentId;

    /**创建人编号*/
    @Column(name = "creator")
    private String creator;

    @Column(name = "create_time")
    private Date createTime;

    /**修改人编号*/
    @Column(name = "modifier")
    private String modifier;

    @Column(name = "birth_day")
    private Date birthDay;

    /**创建时间*/
    @Column(name = "modify_time")
    private Date modifyTime;

    /**乐观锁版本*/
    @Column(name = "version")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long version;

    /**删除标记*/
    @Column(name = "destroyed")
    private Boolean destroyed;

    /**组织编号*/
    @Column(name = "principal_group_code")
    private String principalGroupCode;
}