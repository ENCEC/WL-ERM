package com.share.message.vo.mdmessage;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wangzicheng
 * @description
 * @date 2021年05月31日 11:25
 */
@ApiModel("未读消息统计入参")
@Data
public class UnReadCountDTO {

    @ApiModelProperty("接收人id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long currentUserId;

    @ApiModelProperty("接收方系统")
    private String targetSystemId;


}
