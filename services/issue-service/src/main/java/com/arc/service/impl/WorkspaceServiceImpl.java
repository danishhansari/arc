package com.arc.service.impl;

import com.arc.assembler.WorkspaceAssembler;
import com.arc.dto.WorkspaceDTO;
import com.arc.dto.WorkspaceSummaryDTO;
import com.arc.entity.UserProjection;
import com.arc.entity.Workspace;
import com.arc.entity.WorkspaceMember;
import com.arc.pojo.WorkspacePojo;
import com.arc.repository.WorkspaceMemberRepository;
import com.arc.repository.WorkspaceRepository;
import com.arc.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;

    @Override
    public WorkspaceDTO create(WorkspacePojo pojo, UUID userId) {
        Workspace workspace = WorkspaceAssembler.getInstance().assembleDTO(pojo);
        UserProjection userProjection = new UserProjection();
        userProjection.setId(userId);
        workspace.setWorkspaceCreator(userProjection);
        workspace = workspaceRepository.save(workspace);
        return WorkspaceAssembler.getInstance().assembleDetails(workspace);
    }

    @Override
    public boolean exists(String name) {
        return workspaceRepository.existsByUrl(name);
    }

    @Override
    public List<WorkspaceSummaryDTO> getInvolveWorkspace(String userId) {
        return workspaceMemberRepository
                .findUserWorkspaces(UUID.fromString(userId));
    }
}
