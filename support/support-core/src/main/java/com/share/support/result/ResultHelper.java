package com.share.support.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回类
 * @author wangcl
 * @date 2020/12/31
 */
@Data
@ApiModel(value="返回对象",description="返回类" )
public class ResultHelper<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "是否成功标识",example="true")
    private Boolean success;
    @ApiModelProperty(value = "success为false时，返回的错误信息")
    private Object errorMessages;
    @ApiModelProperty(value = "success为false时，返回的错误码",example="500")
    private Object errorCode;
    @ApiModelProperty(value = "具体的返回对象")
    private T data;
}
