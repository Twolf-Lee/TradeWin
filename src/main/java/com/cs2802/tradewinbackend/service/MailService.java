package com.cs2802.tradewinbackend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Service
public class MailService {

    @Value("${spring.mail.username}")
    private String mailUsername;
    @Resource
    private JavaMailSender javaMailSender;
    @Resource
    private TemplateEngine templateEngine;

    /**
     * 激活账号的邮件 发送
     * @param activateUrl
     * @param email
     */
    public void senMailForActivateAccount(String activateUrl, String email){
        //创建邮件对象
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true);
            //创建邮件template
            message.setSubject("Welcome to TradeWin - Activate Account");
            //Set mail sender
            message.setFrom(mailUsername);
            //设置邮件的接收者（多个）
            message.setTo(email);
            //设置邮件的抄送人
            //message.setCc();
            //设置邮件的秘送人
            //message.setBcc();
            //设置邮件的发送日期
            message.setSentDate(new Date());
            //创建上下文环境
            Context context = new Context();
            context.setVariable("activateUrl",activateUrl);
            String text = templateEngine.process("activate-account.html",context);
            //邮件的发送
            message.setText(text,true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        javaMailSender.send(mimeMessage);

    }

}
