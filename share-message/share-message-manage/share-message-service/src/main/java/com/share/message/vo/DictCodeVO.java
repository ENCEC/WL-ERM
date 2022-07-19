package com.share.message.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author tujx
 * @description 数据字典明细VO
 * @date 2021/01/04
 */
@ApiModel(value = "字典明细")
@Data
public class DictCodeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典明细编码
     */
    @ApiModelProperty(value = "明细编码")
    private String dictCode;

    /**
     * 字典明细名称
     */
    @ApiModelProperty(value = "明细名称")
    private String dictName;
}
