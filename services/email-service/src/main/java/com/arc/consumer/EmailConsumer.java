package com.arc.consumer;

import com.arc.dto.EmailDTO;
import com.arc.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class EmailConsumer {

    private final EmailService emailService;

    @KafkaListener(topics = "email", groupId = "user-group")
    public void consume(EmailDTO emailDTO) throws MessagingException {
        emailService.sendVerificationMail(emailDTO);
    }
}
