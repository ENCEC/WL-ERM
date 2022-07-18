package com.share.auth.domain;

import lombok.Data;

/**
 * @Author:chenxf
 * @Description: 应用查询结果封装类
 * @Date: 17:50 2020/11/28
 * @Param: 
 * @Return:
 *
 */
@Data
public class QuerySysResourceResultVO {
    private String code;

    private String msg;

    private Object data;
}
