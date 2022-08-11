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
@Table(name = "eb_province")
public class EbProvince extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    /**主键*/
    @Id
    @Column(name = "ebpe_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long ebpeId;

    /**创建时间*/
    @Column(name = "create_time")
    private Date createTime;

    /**创建人*/
    @Column(name = "creator")
    private String creator;

    /**地区CODE*/
    @Column(name = "ebpe_area_code")
    private String ebpeAreaCode;

    /**国家CODE*/
    @Column(name = "ebpe_contry_code")
    private String ebpeContryCode;

    /**国家表主键*/
    @Column(name = "ebpe_ebcy_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long ebpeEbcyId;

    /**省份CODE*/
    @Column(name = "ebpe_province_code")
    private String ebpeProvinceCode;

    /**省份中文名称*/
    @Column(name = "ebpe_province_name_cn")
    private String ebpeProvinceNameCn;

    /**省份英文名称*/
    @Column(name = "ebpe_province_name_en")
    private String ebpeProvinceNameEn;

    /**省中文简称*/
    @Column(name = "ebpe_shortname")
    private String ebpeShortname;

    /**状态*/
    @Column(name = "ebpe_status")
    private String ebpeStatus;

    /**预留字段*/
    @Column(name = "ebpe_subdate1")
    private Date ebpeSubdate1;

    /**预留字段*/
    @Column(name = "ebpe_subdate2")
    private Date ebpeSubdate2;

    /**预留字段*/
    @Column(name = "ebpe_subdate3")
    private Date ebpeSubdate3;

    /**预留字段*/
    @Column(name = "ebpe_subnum1")
    private BigDecimal ebpeSubnum1;

    /**预留字段*/
    @Column(name = "ebpe_subnum2")
    private BigDecimal ebpeSubnum2;

    /**预留字段*/
    @Column(name = "ebpe_subnum3")
    private BigDecimal ebpeSubnum3;

    /**预留字段*/
    @Column(name = "ebpe_substr1")
    private String ebpeSubstr1;

    /**预留字段*/
    @Column(name = "ebpe_substr2")
    private String ebpeSubstr2;

    /**预留字段*/
    @Column(name = "ebpe_substr3")
    private String ebpeSubstr3;

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