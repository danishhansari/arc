package com.arc.service.impl;

import com.arc.dto.InvitationWorkspaceDTO;
import com.arc.dto.WorkspaceMemberDTO;
import com.arc.entity.UserProjection;
import com.arc.entity.Workspace;
import com.arc.entity.WorkspaceMember;
import com.arc.pojo.WorkspaceMemberPojo;
import com.arc.repository.UserProjectionRepository;
import com.arc.repository.WorkspaceMemberRepository;
import com.arc.repository.WorkspaceRepository;
import com.arc.service.KafkaProducerService;
import com.arc.service.WorkspaceMemberService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WorkspaceMemberServiceImpl implements WorkspaceMemberService {

    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final UserProjectionRepository userProjectionRepository;
    private final WorkspaceRepository workspaceRepository;
    private final KafkaProducerService kafkaProducerService;

    @Override
    @Transactional
    public WorkspaceMemberDTO create(WorkspaceMemberPojo workspacePojo, UUID userId) {
        UserProjection user = userProjectionRepository.findById(userId).orElseThrow();
        WorkspaceMember workspaceMember = new WorkspaceMember();
        Workspace workspace = workspaceRepository.findById(workspacePojo.getWorkspaceId()).orElseThrow();
        workspaceMember.setWorkspaceId(workspace);
        workspaceMember.setName(workspacePojo.getName());
        workspaceMember.setTitle(workspacePojo.getTitle());
        workspaceMember.setUserId(user);
        workspaceMember.setIsActive(true);
        List<String> emails = workspacePojo.getInvitedEmail();
        List<String> newUsers = new ArrayList<>();
        Map<String, UserProjection> userMap = userProjectionRepository
                .findByEmailIn(workspacePojo.getInvitedEmail())
                .stream()
                .collect(Collectors
                        .toMap(UserProjection::getEmail,
                                userProjection -> userProjection));
        List<WorkspaceMember> invitedMembers = emails.stream().distinct().map(str -> {
                    WorkspaceMember member = new WorkspaceMember();
                    member.setWorkspaceId(workspace);
                    UserProjection existingUser = userMap.get(str);
                    if(existingUser != null) {
                        member.setUserId(existingUser);
                    } else {
                        member.setInvitedEmail(str);
                        newUsers.add(str);
                    }
                    return member;
                }).collect(Collectors.toCollection(ArrayList::new));
        invitedMembers.add(workspaceMember);
        workspaceMemberRepository.saveAll(invitedMembers);
        InvitationWorkspaceDTO invitationWorkspaceDTO = new InvitationWorkspaceDTO(workspace.getName(),
                workspacePojo.getName(), newUsers);
        kafkaProducerService.sendInvitationToNewUsers("invitation_to_new_users", invitationWorkspaceDTO);
        return new WorkspaceMemberDTO();
    }
}
