package com.heitor.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

public class SmtpEmailService extends AbstractEmailService{

    @Autowired
    private MailSender mailSender;

    @Autowired
    private JavaMailSender javaMailSender;

    private Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

    @Override
    public void sendEmail(SimpleMailMessage simpleMailMessage) {
        LOG.info("ENVIANDO EMAIL");
        mailSender.send(simpleMailMessage);
        LOG.info("Email Enviado");
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        LOG.info("ENVIANDO EMAIL");
        javaMailSender.send(msg);
        LOG.info("Email Enviado");

    }
}
