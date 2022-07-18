package com.share.auth.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author:chenxf
 * @Description: 子企业封装类
 * @Date: 17:50 2020/11/28
 * @Param: 
 * @Return:
 *
 */
@Data
public class QueryUserSubCompanyDTO implements Serializable{
    private static final long serialVersionUID = 1;
    /**企业id*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long uemCompanyId;

    /**物流交换代码*/
    private String companyCode;

    /**企业中文名称*/
    private String companyNameCn;
}
