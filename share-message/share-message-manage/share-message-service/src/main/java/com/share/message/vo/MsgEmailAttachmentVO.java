package com.share.message.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tujx
 * @description 邮件模板的附件VO
 * @date 2021/01/05
 */
@ApiModel(value = "邮件模板的附件VO")
@Data
public class MsgEmailAttachmentVO {

    /**
     * 附件主键
     */
    @ApiModelProperty(value = "附件主键")
    private Long msgEmailAttachmentId;

    /**
     * 附件类型
     */
    @ApiModelProperty(value = "附件类型")
    private String attachmentType;

    /**
     * 报表路径
     */
    @ApiModelProperty(value = "报表路径")
    private String attachmentUrl;
}
