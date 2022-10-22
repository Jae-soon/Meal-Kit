package com.js.mealkitecommerce.app.global.email.service;

import com.js.mealkitecommerce.app.entity.Customer;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {
    private JavaMailSender emailSender;

    @Async
    public void sendSimpleMessage(Customer customer, String title, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("a2346532@gmail.com");
        message.setTo(customer.getEmail());
        message.setSubject(title);
        message.setText(text);
        emailSender.send(message);
    }

    @Async
    public void sendSimpleMessage(Customer customer, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("a2346532@gmail.com");
        message.setTo(customer.getEmail());
        message.setSubject("임시 비밀번호");
        message.setText(text);
        emailSender.send(message);
    }
}