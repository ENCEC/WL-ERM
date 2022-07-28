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
@Table(name = "eb_city")
public class EbCity extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    /**主键*/
    @Id
    @Column(name = "ebci_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long ebciId;

    /**创建时间*/
    @Column(name = "create_time")
    private Date createTime;

    /**创建人*/
    @Column(name = "creator")
    private String creator;

    /**城市CODE*/
    @Column(name = "ebci_city_code")
    private String ebciCityCode;

    /**城市中文名称*/
    @Column(name = "ebci_city_name_cn")
    private String ebciCityNameCn;

    /**城市英文名称*/
    @Column(name = "ebci_city_name_en")
    private String ebciCityNameEn;

    /**国外的国家编码*/
    @Column(name = "ebci_contry_code")
    private String ebciContryCode;

    /**省份表主键*/
    @Column(name = "ebci_ebpe_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long ebciEbpeId;

    /**省份CODE*/
    @Column(name = "ebci_province_code")
    private String ebciProvinceCode;

    /**预留字段*/
    @Column(name = "ebci_subdate1")
    private Date ebciSubdate1;

    /**预留字段*/
    @Column(name = "ebci_subdate2")
    private Date ebciSubdate2;

    /**预留字段*/
    @Column(name = "ebci_subdate3")
    private Date ebciSubdate3;

    /**预留字段*/
    @Column(name = "ebci_subnum1")
    private BigDecimal ebciSubnum1;

    /**预留字段*/
    @Column(name = "ebci_subnum2")
    private BigDecimal ebciSubnum2;

    /**预留字段*/
    @Column(name = "ebci_subnum3")
    private BigDecimal ebciSubnum3;

    /**预留字段*/
    @Column(name = "ebci_substr1")
    private String ebciSubstr1;

    /**预留字段*/
    @Column(name = "ebci_substr2")
    private String ebciSubstr2;

    /**预留字段*/
    @Column(name = "ebci_substr3")
    private String ebciSubstr3;

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