package com.share.message.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author wangzicheng
 * @description
 * @date 2021年05月27日 11:39
 */
@ApiModel("消息VO")
@Data
public class MsgMessageVO implements Serializable {

    /**主键*/
    @ApiModelProperty("主键")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long msgMessageId;

    /**发送方系统id*/
    @ApiModelProperty("发送方系统id")
    private String businessSystemId;

    @ApiModelProperty("接收人")
    private List<UserVO> userVOList;

    /**创建时间*/
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**是否成功（0-失败，1-成功）*/
    @ApiModelProperty("是否成功（true-失败，false-成功）")
    private Boolean isSuccess;

    /**消息内容*/
    @ApiModelProperty("消息内容")
    private String messageContent;

    /**消息等级*/
    @ApiModelProperty("消息等级")
    private String messageLevel;

    /**消息标题*/
    @ApiModelProperty("消息标题")
    private String messageTitle;

    /**消息类型（1-平台通知）*/
    @ApiModelProperty("消息类型（1-平台通知）")
    private String messageType;

    /**失败原因*/
    @ApiModelProperty("失败原因")
    private String reason;

    /**接收方系统id（接收方系统id多个时用逗号隔开）*/
    @ApiModelProperty("接收方系统id")
    private String targetSystemId;
}
