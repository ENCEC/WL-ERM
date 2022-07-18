package com.share.support.mail.service.impl;

import com.share.support.mail.model.EmailModel;
import com.share.support.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 发送邮件实现类
 * @author wangcl
 * @date 20200924
 */
@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;

    /**
     * 发送邮件
     * @param emailModel 邮件接收对象
     */
    @Override
    public void sendSimpleMailMessge(EmailModel emailModel) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailModel.getFrom());
        message.setTo(emailModel.getToEmail());
        message.setSubject(emailModel.getSubject());
        message.setText(emailModel.getContent());
        mailSender.send(message);
    }
}
