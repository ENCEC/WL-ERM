package com.share.auth.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: cjh
 * @Description: 联想控件返回结果
 * @Date: 2021-10-5
 */
@Data
public class UemCompanyNameVO implements Serializable {

    /**企业id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemCompanyId;

    /**企业中文名称*/
    private String companyNameCn;

    /**详细地址*/
    private String locAddress;

    /**企业电话*/
    private String companyTel;

    /**组织机构代码*/
    private String organizationCode;

    /**法人*/
    private String legalName;

    /**所在城市*/
    private String locCityCode;

    /**城市名称*/
    private String locCityName;

    /**所在国家*/
    private String locCountryCode;

    /**国家名称*/
    private String locCountryName;

    /**所在区/县*/
    private String locDistrictCode;

    /**区/县名称*/
    private String locDistrictName;

    /**所在省*/
    private String locProvinceCode;

    /**所在省名称*/
    private String locProvinceName;

}
