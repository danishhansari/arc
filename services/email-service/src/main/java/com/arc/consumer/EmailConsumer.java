package com.arc.consumer;

import com.arc.dto.EmailDTO;
import com.arc.dto.InvitationWorkspaceDTO;
import com.arc.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class EmailConsumer {

    private final EmailService emailService;

    @KafkaListener(topics = "email_verification_otp", groupId = "user-group")
    public void consume(EmailDTO emailDTO) throws MessagingException {
        emailService.sendVerificationMail(emailDTO);
    }

    @KafkaListener(topics = "invitation_to_new_users", groupId = "user-group")
    public void consume(InvitationWorkspaceDTO invitationWorkspaceDTO) throws MessagingException {
        emailService.sendInvitationEmails(invitationWorkspaceDTO);
    }

}
