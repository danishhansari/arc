package com.arc.service.impl;

import com.arc.dto.EmailDTO;
import com.arc.dto.UserDTO;
import com.arc.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void sendUserDetailToIssueService(String topic, UserDTO dto) {
        kafkaTemplate.send(topic, dto.getEmail(), dto);
    }

    @Override
    public void sendAuthenticationEmail(String topic, EmailDTO emailDTO) {
        kafkaTemplate.send(topic, emailDTO);
    }

}
