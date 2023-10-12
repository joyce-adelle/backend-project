package com.crust.backendproject.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService implements EmailSender {

    @Value("${app.email.from}")
    private String emailFrom;

    private final JavaMailSender javaMailSender;

    @Async
    @Override
    public void send(String to, String email) throws RuntimeException {

        try {
            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailMessage, "utf-8");
            mimeMessageHelper.setSubject("Confirm your email address");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(emailFrom);
            mimeMessageHelper.setText(email, true);
            javaMailSender.send(mailMessage);
        } catch (MessagingException | MailException e) {
            log.info(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
