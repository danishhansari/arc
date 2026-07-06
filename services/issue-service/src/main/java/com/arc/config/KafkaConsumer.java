package com.arc.config;

import com.arc.dto.UserDTO;
import com.arc.entity.UserProjection;
import com.arc.entity.WorkspaceMember;
import com.arc.repository.UserProjectionRepository;
import com.arc.repository.WorkspaceMemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class KafkaConsumer {

    private final UserProjectionRepository userProjectionRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;

    @Transactional
    @KafkaListener(topics = "user_details_to_issue_service", groupId = "user-group")
    public void consume(UserDTO user) {
        UserProjection userProjection = new UserProjection();
        userProjection.setId(user.getId());
        userProjection.setCompanyName(user.getCompanyName());
        userProjection.setEmail(user.getEmail());
        userProjectionRepository.saveAndFlush(userProjection);

        List<WorkspaceMember> workspaceMember = workspaceMemberRepository.findByInvitedEmail(user.getEmail());
        workspaceMember
                .stream().filter(member -> member.getUserId() == null)
                .forEach(member -> member.setUserId(userProjection));
        workspaceMemberRepository.saveAll(workspaceMember);
    }
}
