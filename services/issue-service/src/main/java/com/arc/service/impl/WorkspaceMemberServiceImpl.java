package com.arc.service.impl;

import com.arc.dto.WorkspaceMemberDTO;
import com.arc.entity.UserProjection;
import com.arc.entity.Workspace;
import com.arc.entity.WorkspaceMember;
import com.arc.pojo.WorkspaceMemberPojo;
import com.arc.repository.UserProjectionRepository;
import com.arc.repository.WorkspaceMemberRepository;
import com.arc.repository.WorkspaceRepository;
import com.arc.service.WorkspaceMemberService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WorkspaceMemberServiceImpl implements WorkspaceMemberService {

    private final WorkspaceMemberRepository workspaceMemberRepository;

    private final UserProjectionRepository userProjectionRepository;

    private final WorkspaceRepository workspaceRepository;


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
        List<String> emails = workspacePojo.getInvitedEmail();
        List<WorkspaceMember> invitedMembers = emails.stream().distinct().map(str -> {
                    WorkspaceMember member = new WorkspaceMember();
                    member.setWorkspaceId(workspace);
                    member.setInvitedEmail(str);
                    return member;
                }).collect(Collectors.toCollection(ArrayList::new));
        invitedMembers.add(workspaceMember);
        workspaceMemberRepository.saveAll(invitedMembers);
        WorkspaceMemberDTO dto = new WorkspaceMemberDTO();
        return dto;
    }
}
