package com.arc.service.impl;

import com.arc.dto.UserDTO;
import com.arc.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final KafkaTemplate<String, UserDTO> kafkaTemplate;

    @Override
    public void sendMessage(String topic, UserDTO dto) {
        System.out.println("I am sending an event");
        kafkaTemplate.send(topic, dto.getEmail(), dto)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println(
                                "Message sent. Topic=" +
                                        result.getRecordMetadata().topic() +
                                        ", Partition=" +
                                        result.getRecordMetadata().partition() +
                                        ", Offset=" +
                                        result.getRecordMetadata().offset()
                        );
                    } else {
                        ex.printStackTrace();
                    }
                });
    }
}
