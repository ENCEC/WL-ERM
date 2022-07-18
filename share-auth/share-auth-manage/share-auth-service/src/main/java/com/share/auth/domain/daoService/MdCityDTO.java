package com.share.auth.domain.daoService;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ds.entity.base.BaseModel;
import com.gillion.ec.core.annotations.Generator;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Date;

/**
 * @param
 * @Author Linja
 * @return
 * @Description
 * @Date 2021/10/14 11:32
 */
@Api("联想控件城市名查询")
@Data
public class MdCityDTO extends BaseModel implements Serializable {

    private String keyword;

    /**城市表主键*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long mdCityId;

    /**城市编码*/
    private String cityCode;

    /**城市名称*/
    private String cityName;

    /**城市英文名称*/
    private String cityNameEn;

    /**国家代码*/
    private String countryCode;

    /**国家中文名称*/
    private String countryNameCn;

    /**国家英文名称*/
    private String countryNameEn;

    /**创建人所属公司id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long createCompanyId;

    /**创建人所属公司name*/
    private String createCompanyName;

    /**创建时间*/
    private Date createTime;

    /**创建人id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long creatorId;

    /**创建名称*/
    private String creatorName;

    /**删除标志(0:未删除  1：删除)*/
    private Boolean isDeleted;

    /**有效状态(0：无效  1：有效）*/
    private Boolean isValid;

    /**国家表主键*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long mdCountryId;

    /**省州表主键*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long mdProvinceId;

    /**修改人id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long modifierId;

    /**修改人名称*/
    private String modifierName;

    /**修改人公司id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long modifyCompanyId;

    /**修改人所属公司name*/
    private String modifyCompanyName;

    /**修改时间*/
    private Date modifyTime;

    /**省州名称*/
    private String provinceCode;

    /**省州中文名称*/
    private String provinceNameCn;

    /**省州英文名称*/
    private String provinceNameEn;

    /**版本号*/
    @Version
    private Integer recordVersion;

    private String remark;

    /**当前页数**/
    @ApiModelProperty("当前页数")
    private Integer currentPage;

    @ApiModelProperty(" 当前显示条数")
    private Integer pageSize;

}
