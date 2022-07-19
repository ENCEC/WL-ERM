package com.share.message.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 邮件模板VO
 * @author tujx
 * @date 2020/10/20
 */
@ApiModel(value = "邮件模板VO")
@Data
public class MsgEmailTemplateVO {

    /**
     * 模板主键
     */
    @ApiModelProperty(value = "模板主键，编辑时使用")
    private Long msgEmailTemplateId;


    /**
     * 邮件模板名称
     */
    @ApiModelProperty(value = "邮件模板名称")
    private String emailTemplateName;

    /**
     * 邮箱发送配置信息
     */
    @ApiModelProperty(value = "邮箱发送配置信息")
    private Long msgEmailConfigId;

    /**
     * 邮件模板描述
     */
    @ApiModelProperty(value = "邮件模板描述")
    private String description;

    /**
     * 邮件模板主题
     */
    @ApiModelProperty(value = "邮件模板主题")
    private String subject;

    /**
     * 邮件模板内容
     */
    @ApiModelProperty(value = "邮件模板内容")
    private String content;

    /**
     * 邮件模板附件
     */
    @ApiModelProperty(value = "邮件模板附件")
    private List<MsgEmailAttachmentVO> msgEmailAttachment;

    /**
     * 使用方系统id
     */
    @ApiModelProperty(value = "使用方系统编码")
    private String systemId;
}
