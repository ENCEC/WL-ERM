package com.share.message.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wangzicheng
 * @description
 * @date 2021年05月25日 15:22
 */
@ApiModel("查询消息模板入参")
@Data
public class QueryMsgMessageTemplateDTO {

    /**
     * 使用方业务系统ID，引用账号体系的ID
     */
    @ApiModelProperty("使用方业务系统ID")
    private String businessSystemId;
    /**
     *消息模板编号
     */
    @ApiModelProperty("消息模板编号")
    private String msgTemplateCode;
    /**
     *消息模板名称
     */
    @ApiModelProperty("消息模板名称")
    private String msgTemplateName;
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
}
