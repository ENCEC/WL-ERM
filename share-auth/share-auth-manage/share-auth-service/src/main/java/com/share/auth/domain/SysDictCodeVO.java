package com.share.auth.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author xrp
 * @date 2020/11/11 0011
 */
@Data
public class SysDictCodeVO implements Serializable {


    /**id*/
    @Id
    @ApiModelProperty("编码id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long sysDictCodeId;

    /**字典类型*/
    @ApiModelProperty("字典类型")
    private String dictTypeCode;


    @ApiModelProperty("字典编码")
    private String value;

    @ApiModelProperty("字典名称")
    private String label;

    @ApiModelProperty("编码id字典Id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long sysDictTypeId;
}
