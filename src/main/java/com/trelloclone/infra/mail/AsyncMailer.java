package com.trelloclone.infra.mail;

import com.trelloclone.domain.common.mail.Mailer;
import com.trelloclone.domain.common.mail.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@Slf4j
@RequiredArgsConstructor
public class AsyncMailer implements Mailer {

    private final JavaMailSender mailSender;

    @Async
    @Override
    public void send(Message message) {
        Assert.notNull(message, "Parameter `message` must not be null");

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            if (StringUtils.isNotBlank(message.getFrom())) {
                mailMessage.setFrom(message.getFrom());
            }
            if (StringUtils.isNotBlank(message.getSubject())) {
                mailMessage.setSubject(message.getSubject());
            }
            if (StringUtils.isNotEmpty(message.getBody())) {
                mailMessage.setText(message.getBody());
            }
            if (message.getTo() != null) {
                mailMessage.setTo(message.getTo());
            }

            mailSender.send(mailMessage);
        } catch (MailException e) {
            log.error("Failed to send mail message", e);
        }
    }
}
