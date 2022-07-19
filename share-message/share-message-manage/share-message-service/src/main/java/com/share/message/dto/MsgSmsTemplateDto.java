package com.share.message.dto;


import lombok.*;

import javax.validation.constraints.NotEmpty;
/**
 * @Description MsgSmsTemplateDto
 * @Author nily
 * @Date 2020/11/25
 * @Time 9:24 上午
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MsgSmsTemplateDto {
    @NotEmpty
    private Long msgSmsTemplateId;

    private String businessSystemId;
    private String smsTemplateCode;
    private String smsTemplateName;
    private String description;
    private String content;
    private String smsType;
    private boolean isValid;
}
