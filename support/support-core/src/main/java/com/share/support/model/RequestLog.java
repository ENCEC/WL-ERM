package com.share.support.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 请求日志实体类
 * @author chenhy
 * @date 2021-07-05
 */
@Data
public class RequestLog implements Serializable {

    private static final long serialVersionUID = 1;

    /**id*/
    private Long requestLogId;

    /**请求头参数*/
    private String headerParam;

    /**业务是否成功(0-成功；1-失败)*/
    private Boolean isSuccess;

    /**请求方式（GET、POST）*/
    private String method;

    /**查询参数*/
    private String queryString;

    /**请求来源地址*/
    private String remoteAddress;

    /**请求体*/
    private String requestBody;

    /**请求URL*/
    private String requestUrl;

    /**请求结果*/
    private String result;

    /**状态码*/
    private String statusCode;

    /**请求类型(0-请求外部系统；1-接收外部系统请求)*/
    private String type;

    /**用户id*/
    private Long uemUserId;

    /**用户名*/
    private String account;

}
