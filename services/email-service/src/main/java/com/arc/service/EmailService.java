package com.arc.service;

import com.arc.dto.EmailDTO;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendVerificationMail(EmailDTO user) throws MessagingException;
}
