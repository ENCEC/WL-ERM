package com.share.message.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 邮件新增、编辑VO
 * @author tujx
 * @date 2020/10/16
 */
@ApiModel(value = "邮件新增、编辑VO")
@Data
public class MsgEmailConfigVO {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long msgEmailConfigId;


    /**
     * 邮件配置名称
     */
    @ApiModelProperty(value = "邮件配置名称")
    private String emailConfigName;

    /**
     * 邮箱类型
     */
    @ApiModelProperty(value = "邮箱类型")
    private String emailType;

    /**
     * 服务器地址
     */
    @ApiModelProperty(value = "服务器地址")
    private String host;

    /**
     * 服务器端口
     */
    @ApiModelProperty(value = "服务器端口")
    private Integer port;

    /**
     * 邮件协议
     */
    @ApiModelProperty(value = "邮件协议")
    private String protocol;

    /**
     * 发送方邮箱
     */
    @ApiModelProperty(value = "发送方邮箱")
    private String emailAddress;

    /**
     * 发送方邮箱密码
     */
    @ApiModelProperty(value = "发送方邮箱密码")
    private String emailPassword;

    /**
     * 邮箱用户名
     */
    @ApiModelProperty(value = "邮箱用户名")
    private String emailUser;

    /**
     * 使用方系统编码
     */
    @ApiModelProperty(value = "使用方系统编码")
    private String systemId;
}
