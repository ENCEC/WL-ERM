package com.share.auth.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;

import javax.persistence.Column;

/**
 * @version 1.0
 * @description企业基本信息VO
 * @Author: zhuyp
 * @Date: 2021/9/30 10:17
 */
@Data
public class CompanyBasicInfoVO {
    /**公司id**/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long companyId;

    /**公司名称**/
    private String companyName;

    /**企业地址**/
    private String locAddress;

    /**企业类型**/
    private String organizationType;

    /**法人名称*/
    private String legalName;

    /**组织机构代码*/
    private String organizationCode;

    /**'所在省'*/
    private String locProvinceCode;
    /**'所在城市'*/
    private String locCityCode;
    /**'所在区/县'*/
    private String locDistrictCode;

    /**角色类型*/
    private String identity;

}
