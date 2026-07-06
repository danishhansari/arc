package com.arc.service;

import com.arc.dto.EmailDTO;
import com.arc.dto.InvitationWorkspaceDTO;
import jakarta.mail.MessagingException;

import java.util.List;

public interface EmailService {
    void sendVerificationMail(EmailDTO user) throws MessagingException;
    void sendInvitationEmails(InvitationWorkspaceDTO invitationWorkspaceDTO) throws MessagingException;
}
