package com.arc.service;

import com.arc.dto.EmailDTO;
import com.arc.dto.UserDTO;

public interface KafkaProducerService {
    void sendUserDetailToIssueService(String topic, UserDTO dto);
    void sendAuthenticationEmail(String topic, EmailDTO emailDTO);
}
