package com.share.message.dto;

import lombok.*;

import java.util.Date;

/**
 * @Description  发送短信日志 Vo
 * @Author nily
 * @Date 2020/12/2
 * @Time 1:41 下午
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MsgSmsRecordDto {

    private Long msgSmsRecordId;

    private String businessSystemId;

    private String channelName;

    private String content;

    private Long createCompanyId;

    private String createCompanyName;

    private Date createTime;

    private Long creatorId;

    private String creatorName;

    private Boolean isSuccess;

    private String mobile;

    private Long modifierId;

    private String modifierName;

    private Long modifyCompanyId;

    private String modifyCompanyName;

    private Date modifyTime;

    private String nationCode;

    private String reason;

    private Integer recordVersion;

    private Integer retryCount;

    private String smsTemplateCode;

    private String smsTemplateName;

    private String smsType;

}
