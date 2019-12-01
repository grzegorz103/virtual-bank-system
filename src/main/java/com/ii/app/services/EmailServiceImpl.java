package com.ii.app.services;

import com.ii.app.services.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {

    public final JavaMailSender emailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Async
    @Override
    public void sendRegisterMail(String receiver, String identifier) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receiver);
        message.setSubject("Rejestracja w wirtualnym systemie");
        message.setText("Dziękujemy za rejestrację w systemie. Twój identyfikator użytkownika to: " + identifier);

        try {
            emailSender.send(message);
        } catch (Exception ignored) {
        }
    }
}
