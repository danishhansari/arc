package com.arc.service;

import com.arc.dto.UserDTO;

public interface KafkaProducerService {
    void sendMessage(String topic, UserDTO dto);
}
