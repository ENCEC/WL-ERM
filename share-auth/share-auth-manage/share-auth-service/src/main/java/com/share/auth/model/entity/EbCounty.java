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
@Table(name = "eb_county")
public class EbCounty extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    /**主键*/
    @Id
    @Column(name = "ebco_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long ebcoId;

    /**创建时间*/
    @Column(name = "create_time")
    private Date createTime;

    /**创建人*/
    @Column(name = "creator")
    private String creator;

    /**城市CODE*/
    @Column(name = "ebco_city_code")
    private String ebcoCityCode;

    /**区CODE*/
    @Column(name = "ebco_county_code")
    private String ebcoCountyCode;

    /**区中文名称*/
    @Column(name = "ebco_county_name_cn")
    private String ebcoCountyNameCn;

    /**区英文名称*/
    @Column(name = "ebco_county_name_en")
    private String ebcoCountyNameEn;

    /**城市主键*/
    @Column(name = "ebco_ebci_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long ebcoEbciId;

    /**预留字段*/
    @Column(name = "ebco_subdate1")
    private Date ebcoSubdate1;

    /**预留字段*/
    @Column(name = "ebco_subdate2")
    private Date ebcoSubdate2;

    /**预留字段*/
    @Column(name = "ebco_subdate3")
    private Date ebcoSubdate3;

    /**预留字段*/
    @Column(name = "ebco_subnum1")
    private BigDecimal ebcoSubnum1;

    /**预留字段*/
    @Column(name = "ebco_subnum2")
    private BigDecimal ebcoSubnum2;

    /**预留字段*/
    @Column(name = "ebco_subnum3")
    private BigDecimal ebcoSubnum3;

    /**预留字段*/
    @Column(name = "ebco_substr1")
    private String ebcoSubstr1;

    /**预留字段*/
    @Column(name = "ebco_substr2")
    private String ebcoSubstr2;

    /**预留字段*/
    @Column(name = "ebco_substr3")
    private String ebcoSubstr3;

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