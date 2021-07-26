package com.heitor.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailService extends AbstractEmailService{

    @Autowired
    private MailSender mailSender;

    private Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

    @Override
    public void sendEmail(SimpleMailMessage simpleMailMessage) {
        LOG.info("ENVIANDO EMAIL");
        LOG.info(simpleMailMessage.toString());
        mailSender.send(simpleMailMessage);
        LOG.info("Email Enviado");
    }
}
