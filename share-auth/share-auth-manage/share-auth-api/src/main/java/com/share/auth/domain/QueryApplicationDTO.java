package com.share.auth.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author:chenxf
 * @Description: 应用查询dto
 * @Date: 17:50 2020/11/28
 * @Param: 
 * @Return:
 *
 */
@Data
public class QueryApplicationDTO implements Serializable{
    private static final long serialVersionUID = 1;

    /**id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long sysApplicationId;

    /**应用名称*/
    private String applicationName;

    /**应用简称*/
    private String applicationAbbreviName;

    /**应用代码*/
    private String applicationCode;

}
