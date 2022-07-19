package com.share.message.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangzicheng
 * @description
 * @date 2021年05月27日 14:30
 */
@Data
public class UserVO implements Serializable {
    /**
     * 接收人id
     */
    @ApiModelProperty("接收人id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long userId;
    /**
     * 接收人
     */
    @ApiModelProperty("接收人")
    private String userName;
    /**
     * 是否已读（true-未读，false-已读）
     */
    @ApiModelProperty("是否已读（true-未读，false-已读）")
    private Boolean isReaded;
}
