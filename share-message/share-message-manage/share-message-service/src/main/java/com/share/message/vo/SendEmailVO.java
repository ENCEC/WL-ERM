package com.share.message.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author tujx
 * @description 发送邮件VO
 * @date 2020/11/04
 */
@ApiModel(value = "发送邮件VO")
@Data
public class SendEmailVO {

    /**
     * 使用方系统编码
     */
    @ApiModelProperty(value = "使用方系统编码")
    private String systemId;

    /**
     * 邮件模板编码
     */
    @ApiModelProperty(value = "邮件模板编码")
    private String emailTemplateCode;

    /**
     * 收件账号，多个账号使用,分隔
     */
    @ApiModelProperty(value = "收件账号，多个账号使用,分隔")
    private String toEmail;

    /**
     * 抄送账号，多个账号使用,分隔
     */
    @ApiModelProperty(value = "抄送账号，多个账号使用,分隔")
    private String ccEmail;

    /**
     * 标题、内容的宏参数值以及模板的参数值
     */
    @ApiModelProperty(value = "标题、内容的宏参数值以及模板的参数值")
    private Map<String, Object> marcoAndAttachParams;

    @ApiModelProperty(value = "附件")
    private List<MultipartFile> fileList;

}
