package com.ii.app.services;

import com.ii.app.services.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {

    public final JavaMailSender emailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendRegisterMail(String receiver, String identifier) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receiver);
        message.setSubject("Rejestracja w wirtualnym systemie");
        message.setText("<html><body><h2>Dziękujemy za rejestrację w systemie.</h2><br/> Twój identyfikator użytkownika to: " + identifier+"</body></html>");

        emailSender.send(message);
    }
}
