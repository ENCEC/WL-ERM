package com.share.message.dto;

import lombok.Data;

/**
 * @author tujx
 * @description 邮件内容DTO
 * @date 2021/01/05
 */
@Data
public class EmailContentDTO {

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件正文
     */
    private String content;

    /**
     * 邮件附件所在父级文件夹
     */
    private String attachmentParentPath;

    /**
     * 发送标识
     */
    private String sendBatch;
}
