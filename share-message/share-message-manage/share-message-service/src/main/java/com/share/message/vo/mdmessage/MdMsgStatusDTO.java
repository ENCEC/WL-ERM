package com.share.message.vo.mdmessage;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wangzicheng
 * @description
 * @date 2021年05月31日 11:41
 */
@ApiModel("更新消息已读入参")
@Data
public class MdMsgStatusDTO {

    @ApiModelProperty("接收人id")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long currentUserId;

    @ApiModelProperty("消息主键集合")
    private List<Long> mdMessageIdList;

    @ApiModelProperty("当前系统代码")
    private String targetSystemId;
}
