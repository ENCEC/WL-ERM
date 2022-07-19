package com.share.message.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author wangzicheng
 * @description
 * @date 2021年05月27日 11:39
 */
@Data
public class MsgMessageVO implements Serializable {

    /**主键*/
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long msgMessageId;

    /**发送方系统id*/
    private String businessSystemId;

    private List<UserVO> userVOList;

    private String userName;

    private Boolean isReaded;

    /**创建时间*/
    private Date createTime;

    /**是否成功（0-失败，1-成功）*/
    private Boolean isSuccess;

    /**消息内容*/
    private String messageContent;

    /**消息等级*/
    private String messageLevel;

    /**消息标题*/
    private String messageTitle;

    /**消息类型（1-平台通知）*/
    private String messageType;

    /**失败原因*/
    private String reason;

    /**接收方系统id（接收方系统id多个时用逗号隔开）*/
    private String targetSystemId;
}
