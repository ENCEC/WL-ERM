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
 * @date 2021年05月28日 13:49
 */
@ApiModel("用户查询消息入参")
@Data
public class QueryMessageByUserDTO {

    /**
     * 当前页数
     */
    @ApiModelProperty("当前页数")
    private Integer currentPage;
    /**
     * 每页总条数
     */
    @ApiModelProperty("每页总条数")
    private Integer pageSize;

    /**
     * 当前登录用户ID
     */
    @ApiModelProperty("当前登录用户ID")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long currentUserId;


    @ApiModelProperty("消息ID")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long mdMessageId;

    /**
     * 当前系统代码
     */
    @ApiModelProperty("当前系统代码")
    private String targetSystemId;

    /**
     * 是否阅读
     */
    @ApiModelProperty("是否阅读")
    private Boolean isReaded;
}
