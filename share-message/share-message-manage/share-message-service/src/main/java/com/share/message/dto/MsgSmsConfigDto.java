package com.share.message.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
/**
 * @Description  MsgSmsConfigDto
 * @Author nily
 * @Date 2020/11/25
 * @Time 9:24 上午
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MsgSmsConfigDto {
    @NotEmpty
    private Long msgSmsConfigId;

    private String businessSystemId;

    private String channelName;

    private String keyword;

    private String smsServiceType;

    private String smsUrl;

    private String appKey;

    private String appSecret;

    private String ispNo;

    private String ispAccount;

    private String ispPassword;

    private Integer priority;


}
