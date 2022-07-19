package com.share.message.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gillion.ec.core.utils.Long2String;
import com.gillion.ec.core.utils.String2Long;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author wangzicheng
 * @description
 * @date 2021年05月27日 16:35
 */
@Data
public class MsgMessageTemplateVO {

    /**
     * 消息模板主键ID
     */
    private Long msgMessageTemplateId;
    /**
     * 消息模板编号
     */
    private String msgTemplateCode;
    /**
     * 消息模板名称
     */
    private String msgTemplateName;
    /**
     * 使用方业务系统ID，引用账号体系的ID
     */
    private String businessSystemId;
    /**
     * 消息正文模板内容
     */
    private String content;
    /**
     * 模板描述
     */
    private String description;
    /**
     * 是否启用（0-禁用，1-启用），默认1
     */
    private Boolean isValid;
    /**
     * 创建时间
     */
    private Date createTime;
}
