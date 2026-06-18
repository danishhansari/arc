package com.arc.service;

import com.arc.dto.EmailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendEmailService {

    private final JavaMailSender javaMailSender;

    @KafkaListener(topics = "email", groupId = "user-group")
    public void consume(EmailDTO user) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(user.getEmail());
        message.setSubject("Send msg");
        message.setText("This is the credentials for login " + user.getCode());

        javaMailSender.send(message);
    }
}
