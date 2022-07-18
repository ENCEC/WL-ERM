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


/**
 * @author DaoServiceGenerator
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "md_global_business")
public class MdGlobalBusiness extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1;

    /**业务产品定义*/
    @Id
    @Column(name = "md_sub_business_global_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long mdSubBusinessGlobalId;

    /**全局业务产品代码*/
    @Column(name = "sub_business_global_code")
    private String subBusinessGlobalCode;

    /**全局业务产品名称*/
    @Column(name = "sub_business_global_name")
    private String subBusinessGlobalName;

    /**有效标志（Y/N）*/
    @Column(name = "active")
    private String active;

    /**删除标记 Y/N*/
    @Column(name = "is_deleted")
    private String isDeleted;

    /**备注*/
    @Column(name = "remark")
    private String remark;

    /**创建人*/
    @Column(name = "creator")
    private String creator;

    /**创建人组织*/
    @Column(name = "create_office")
    private String createOffice;

    /**创建时间*/
    @Column(name = "create_time")
    private String createTime;

    /**创建时区*/
    @Column(name = "create_time_zone")
    private String createTimeZone;

    /**最后修改人*/
    @Column(name = "last_modifyor")
    private String lastModifyor;

    /**最后修改人组织*/
    @Column(name = "last_modify_office")
    private String lastModifyOffice;

    /**最后修改时间*/
    @Column(name = "last_modify_time")
    private String lastModifyTime;

    /**最后修改市区*/
    @Column(name = "last_modify_time_zone")
    private String lastModifyTimeZone;

    @Column(name = "principal_group_code")
    private String principalGroupCode;

    /**版本号*/
    @Column(name = "record_version")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long recordVersion;

    /**是否免税*/
    @Column(name = "is_tax_free")
    private String isTaxFree;

}