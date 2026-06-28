package com.arc.service.impl;

import com.arc.assembler.WorkspaceAssembler;
import com.arc.dto.WorkspaceDTO;
import com.arc.entity.Workspace;
import com.arc.pojo.WorkspacePojo;
import com.arc.repository.WorkspaceRepository;
import com.arc.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    @Override
    public WorkspaceDTO create(WorkspacePojo pojo, UUID userId) {
        Workspace workspace = WorkspaceAssembler.getInstance().assembleDTO(pojo);
        workspace.setWorkspaceCreator(userId);
        workspace = workspaceRepository.save(workspace);
        return WorkspaceAssembler.getInstance().assembleDetails(workspace);
    }
}
