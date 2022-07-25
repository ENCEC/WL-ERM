package com.share.auth.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ds.entity.base.BaseModel;
import com.gillion.ec.core.annotations.Generator;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @author DaoServiceGenerator
 */
@SuppressWarnings("JpaDataSourceORMInspection")
        @EqualsAndHashCode(callSuper = true)
    @Data
    @Entity
@Table(name = "eb_contry")
public class EbContry extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    /**主键*/
    @Id
    @Column(name = "ebcy_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long ebcyId;

    /**创建时间*/
    @Column(name = "create_time")
    private Date createTime;

    /**创建人*/
    @Column(name = "creator")
    private String creator;

    /**国家CODE*/
    @Column(name = "ebcy_code")
    private String ebcyCode;

    /**国家中文名称*/
    @Column(name = "ebcy_name_cn")
    private String ebcyNameCn;

    /**国家英文名称*/
    @Column(name = "ebcy_name_en")
    private String ebcyNameEn;

    /**国家英文名简写*/
    @Column(name = "ebcy_short_name_en")
    private String ebcyShortNameEn;

    /**预留字段*/
    @Column(name = "ebcy_subdate1")
    private Date ebcySubdate1;

    /**预留字段*/
    @Column(name = "ebcy_subdate2")
    private Date ebcySubdate2;

    /**预留字段*/
    @Column(name = "ebcy_subdate3")
    private Date ebcySubdate3;

    /**预留字段*/
    @Column(name = "ebcy_subnum1")
    private BigDecimal ebcySubnum1;

    /**预留字段*/
    @Column(name = "ebcy_subnum2")
    private BigDecimal ebcySubnum2;

    /**预留字段*/
    @Column(name = "ebcy_subnum3")
    private BigDecimal ebcySubnum3;

    /**预留字段*/
    @Column(name = "ebcy_substr1")
    private String ebcySubstr1;

    /**预留字段*/
    @Column(name = "ebcy_substr2")
    private String ebcySubstr2;

    /**预留字段*/
    @Column(name = "ebcy_substr3")
    private String ebcySubstr3;

    /**修改人*/
    @Column(name = "modifier")
    private String modifier;

    /**修改时间*/
    @Column(name = "modify_time")
    private Date modifyTime;

    /**时间戳*/
    @Column(name = "rec_ver")
    private BigDecimal recVer;

}