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
@Table(name = "md_district")
public class MdDistrict extends BaseModel implements Serializable{
private static final long serialVersionUID=1;

    /**区/县表主键*/
    @Id
    @Column(name = "md_district_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    @Generator("snowFlakeGenerator")
    private Long mdDistrictId;

    /**城市编码*/
    @Column(name = "city_code")
    private String cityCode;

    /**城市名称*/
    @Column(name = "city_name")
    private String cityName;

    /**城市英文名称*/
    @Column(name = "city_name_en")
    private String cityNameEn;

    /**国家代码*/
    @Column(name = "country_code")
    private String countryCode;

    /**国家中文名称*/
    @Column(name = "country_name_cn")
    private String countryNameCn;

    /**国家英文名称*/
    @Column(name = "country_name_en")
    private String countryNameEn;

    /**创建人所属公司id*/
    @Column(name = "create_company_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long createCompanyId;

    /**创建人所属公司name*/
    @Column(name = "create_company_name")
    private String createCompanyName;

    /**创建时间*/
    @Column(name = "create_time")
    private Date createTime;

    /**创建人id*/
    @Column(name = "creator_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long creatorId;

    /**创建名称*/
    @Column(name = "creator_name")
    private String creatorName;

    /**区/县编码*/
    @Column(name = "district_code")
    private String districtCode;

    /**区/县中文名称*/
    @Column(name = "district_name")
    private String districtName;

    /**区县英文名称*/
    @Column(name = "district_name_en")
    private String districtNameEn;

    /**删除标志(0:未删除  1：删除)*/
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    /**有效状态(0：无效  1：有效）*/
    @Column(name = "is_valid")
    private Boolean isValid;

    /**城市表主键*/
    @Column(name = "md_city_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long mdCityId;

    /**国家表主键*/
    @Column(name = "md_country_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long mdCountryId;

    /**省州表主键*/
    @Column(name = "md_province_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long mdProvinceId;

    /**修改人id*/
    @Column(name = "modifier_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long modifierId;

    /**修改人名称*/
    @Column(name = "modifier_name")
    private String modifierName;

    /**修改人公司id*/
    @Column(name = "modify_company_id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long modifyCompanyId;

    /**修改人所属公司name*/
    @Column(name = "modify_company_name")
    private String modifyCompanyName;

    /**修改时间*/
    @Column(name = "modify_time")
    private Date modifyTime;

    /**省州代码*/
    @Column(name = "province_code")
    private String provinceCode;

    /**省州中文名称*/
    @Column(name = "province_name_cn")
    private String provinceNameCn;

    /**省州英文名称*/
    @Column(name = "province_name_en")
    private String provinceNameEn;

    /**版本号*/
    @Column(name = "record_version")
    private Integer recordVersion;

    @Column(name = "remark")
    private String remark;

}