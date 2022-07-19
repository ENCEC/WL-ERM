package com.share.message.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * @author wangzicheng
 * @description
 * @date 2021年05月25日 15:50
 */
@ApiModel("消息模板VO")
@Data
public class MsgMessageTemplateVO implements Serializable {
    /**
     * 消息模板主键ID
     */
    @ApiModelProperty("消息模板主键ID")
    @JsonSerialize(using = Long2String.class)
    @JsonDeserialize(using = String2Long.class)
    private Long msgMessageTemplateId;
    /**
     * 消息模板编号
     */
    @ApiModelProperty("消息模板编号")
    private String msgTemplateCode;
    /**
     * 消息模板名称
     */
    @ApiModelProperty("消息模板名称")
    private String msgTemplateName;
    /**
     * 使用方业务系统ID，引用账号体系的ID
     */
    @ApiModelProperty("使用方业务系统ID")
    private String businessSystemId;
    /**
     * 消息正文模板内容
     */
    @ApiModelProperty("消息正文模板内容")
    private String content;
    /**
     * 模板描述
     */
    @ApiModelProperty("模板描述")
    private String description;
    /**
     * 是否启用（0-禁用，1-启用），默认1
     */
    @ApiModelProperty("是否启用（0-禁用，1-启用），默认1")
    private Boolean isValid;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;
}
