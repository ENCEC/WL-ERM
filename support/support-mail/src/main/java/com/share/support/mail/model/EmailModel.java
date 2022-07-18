package com.share.support.mail.model;

import lombok.Data;

/**
 * 邮件接收对象
 * @author wangcl
 * @date 20200924
 */
@Data
public class EmailModel {
    /**
     * 接收邮箱
     */
    private String[] toEmail;
    /**
     * 发送邮箱
     */
    private String from;
    /**
     * 主题
     */
    private String subject;
    /**
     * 内容
     */
    private String content;
}
