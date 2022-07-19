package com.share.message.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;


/**
 * @author wangzicheng
 * @description
 * @date 2021年05月27日 11:37
 */
@ApiModel("查询消息入参")
@Data
public class QueryMsgMessageDTO {

    /**
     * 当前页数
     */
    @ApiModelProperty("当前页数")
    private Integer pageNow;
    /**
     * 每页总条数
     */
    @ApiModelProperty("每页总条数")
    private Integer pageSize;

    @ApiModelProperty("发送时间从")
    private Date createTimeMin;

    @ApiModelProperty("发送时间到")
    private Date createTimeMax;
    /**
     * 消息标题
     */
    @ApiModelProperty("消息标题")
    private String messageTitle;
    /**
     * 用户名称
     */
    @ApiModelProperty("用户名称")
    private String userName;
    /**
     * 是否成功（0-失败，1-成功）
     */
    @ApiModelProperty("是否成功（true-失败，false-成功）")
    private Boolean isSuccess;
    /**
     * 接收方系统id
     */
    @ApiModelProperty("接收方系统id")
    private String targetSystemId;
    /**
     * 使用方系统id
     */
    @ApiModelProperty("使用方系统id")
    private String businessSystemId;
}
