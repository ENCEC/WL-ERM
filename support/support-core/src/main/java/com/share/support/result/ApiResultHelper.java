package com.share.support.result;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * api以及feign统一返回类
 * @author wangcl
 * @date 2021/05/21
 */
@Data
public class ApiResultHelper<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 返回代码，0：成功，1：失败
     */
    private String resultCode;

    /**
     * resultCode为1时返回的错误信息
     */
    private String resultMsg;

    /**
     * 返回的数据对象
     */
    T data;
}
