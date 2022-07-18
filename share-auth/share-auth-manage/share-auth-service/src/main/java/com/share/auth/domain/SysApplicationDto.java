package com.share.auth.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xrp
 * @date 2020/11/5 0005
 */
@Api("应用管理表")
@Data
public class SysApplicationDto implements Serializable {

    /**应用管理id*/
    private String sysApplicationId;
    /**应用名称*/
    private String applicationName;
    /**应用代码*/
    private String applicationCode;
    /**应用地址*/
    private String applicationUrl;
    /**是否禁用(0禁用,1启用)*/
    private Boolean isValid;
    /**启/禁用时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date invalidTime;
    /**显示页数*/
    private Integer pageNo;
    /**显示条数*/
    private Integer pageSize;
    /**应用简称*/
    private String applicationAbbreviName;
    /**维护企业*/
    private String relatedEnterprise;
    /**维护联系人*/
    private String contact;
    /**维护联系人手机*/
    private String contactTel;
    /**应用描述*/
    private String applicationRemark;
    /**维护人*/
    private String modifierName;

}
