package com.share.message.vo.mdmessage;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wangzicheng
 * @description
 * @date 2021年05月28日 13:54
 */
@ApiModel("用户消息VO")
@Data
public class MdMessageVO implements Serializable {

    /**主键*/
    @ApiModelProperty("主键")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long mdMessageId;


    /**消息时间*/
    @ApiModelProperty("消息时间")
    private Date notifyTime;

    /**是否已读（0-未读，1-已读）*/
    @ApiModelProperty("是否已读（true-未读，false-已读）")
    private Boolean messageStatus;

    /**消息内容*/
    @ApiModelProperty("消息内容")
    private String messageContent;

    /**消息标题*/
    @ApiModelProperty("消息标题")
    private String messageTitle;

    /**消息类型（1-平台通知）*/
    @ApiModelProperty("消息类型（1-平台通知）")
    private String messageType;

    /**来源*/
    @ApiModelProperty("发送方系统id")
    private String businessSystemId;

}
