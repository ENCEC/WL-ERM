package com.share.auth.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chenxq
 * @date 2021-03-15 14:18
 */
@Data
@ApiModel("用户企业信息")
public class UserAndCompanyVo implements Serializable {
    /**用户ID*/
    @ApiModelProperty("用户ID")
    private Long userId;
    /**企业信用代码*/
    @ApiModelProperty("企业信用代码")
    private String organizationCode;
    /**企业名称*/
    @ApiModelProperty("企业名称")
    private String companyNameCn;
    /**联系人*/
    @ApiModelProperty("联系人")
    private String contactName;
    /**联系电话*/
    @ApiModelProperty("联系电话")
    private String contactTel;
    /**应用代码*/
    @ApiModelProperty("应用代码")
    private String applicationCode;
}
