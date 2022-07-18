package com.share.support.mail.service;

import com.share.support.mail.model.EmailModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 发送邮箱工具类
 * @author wangcl
 * @date 20200924
 */
public interface MailService {
    /**
     * 发送普通邮件
     * @param emailModel 邮件接收对象
     */
    void sendSimpleMailMessge(EmailModel emailModel);
}
