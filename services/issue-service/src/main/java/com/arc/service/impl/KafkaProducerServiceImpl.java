package com.arc.service.impl;

import com.arc.dto.InvitationWorkspaceDTO;
import com.arc.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void sendInvitationToNewUsers(String topic, InvitationWorkspaceDTO invitationWorkspaceDTO){
        kafkaTemplate.send(topic, invitationWorkspaceDTO);
    }

}
