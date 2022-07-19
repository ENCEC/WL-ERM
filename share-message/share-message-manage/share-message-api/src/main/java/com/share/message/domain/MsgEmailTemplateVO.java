package com.share.message.domain;

import lombok.Data;


/**
 * 邮件模板VO
 * @author tujx
 * @date 2020/10/20
 */
@Data
public class MsgEmailTemplateVO {

    /**
     * 邮件模板编码
     */
    private String emailTemplateCode;


    /**
     * 使用方系统id
     */
    private String systemId;
}
