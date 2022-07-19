package com.share.message.dto;

import com.share.message.model.entity.MsgEmailConfig;
import com.share.message.model.entity.MsgEmailTemplate;
import com.share.message.vo.SendEmailVO;
import lombok.Data;

/**
 * @author tujx
 * @description 邮件发送DTO
 * @date 2021/03/23
 */
@Data
public class SendEmailDTO {

    /**
     * 邮件收件账号
     */
    SendEmailVO sendEmailVO;

    /**
     * 邮件主题、正文以及附件信息
     */
    EmailContentDTO emailContentDTO;

    /**
     * 发送配置
     */
    MsgEmailConfig emailConfig;

    /**
     * 发送模板
     */
    MsgEmailTemplate emailTemplate;
}
