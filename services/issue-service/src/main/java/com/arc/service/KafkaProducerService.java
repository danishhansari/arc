package com.arc.service;

import com.arc.dto.EmailDTO;
import com.arc.dto.InvitationWorkspaceDTO;
import com.arc.dto.UserDTO;

import java.util.List;

public interface KafkaProducerService {
    void sendInvitationToNewUsers(String topic, InvitationWorkspaceDTO invitationWorkspaceDTO);
}
