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
import java.util.Date;


/**
 * @author DaoServiceGenerator
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "children")
public class Children extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1;

    /**孩子编号*/
    @Id
    @Column(name = "id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long id;

    /**孩子名称*/
    @Column(name = "child_name")
    private String childName;

    /**孩子性别*/
    @Column(name = "sex")
    private Boolean sex;

    /**礼物id*/
    @Column(name = "gift_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long giftId;

    /**礼品编号*/
    @Column(name = "gift_code")
    private String giftCode;

    /**乐观锁版本号*/
    @Column(name = "version")
    private Integer version;

    /**是否逻辑删除*/
    @Column(name = "destroyed")
    private Boolean destroyed;

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

    /**用户属性字段*/
    @Column(name = "user_attribute")
    private String userAttribute;

    /**手机号，与parents_mobile保持一致*/
    @Column(name = "mobile")
    private String mobile;

    /**父母的手机联系方式，与mobile保持一致*/
    @Column(name = "parents_mobile")
    private String parentsMobile;

    /**固定电话*/
    @Column(name = "phone")
    private String phone;

    /**邮箱*/
    @Column(name = "mailbox")
    private String mailbox;

    /**年龄*/
    @Column(name = "age")
    private Integer age;

    /**学龄*/
    @Column(name = "school_age")
    private Integer schoolAge;

    /**英文名字*/
    @Column(name = "english_name")
    private String englishName;

    /**幼儿园入学日期*/
    @Column(name = "enrollment_date")
    private Date enrollmentDate;

    /**幼儿园毕业日期*/
    @Column(name = "graduation_date")
    private Date graduationDate;

}