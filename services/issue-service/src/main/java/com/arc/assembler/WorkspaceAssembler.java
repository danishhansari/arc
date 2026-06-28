package com.arc.assembler;

import com.arc.dto.WorkspaceDTO;
import com.arc.entity.Workspace;
import com.arc.pojo.WorkspacePojo;

public class WorkspaceAssembler {

    private static WorkspaceAssembler instance;
    private WorkspaceAssembler() {}

    public static WorkspaceAssembler getInstance() {
        if(instance == null) {
            synchronized (WorkspaceAssembler.class) {
                if(instance == null) {
                    instance = new WorkspaceAssembler();
                }
            }
        }
        return instance;
    }

    public Workspace assembleDTO (WorkspacePojo pojo) {
        Workspace workspace = new Workspace();
        workspace.setName(pojo.getName());
        workspace.setUrl(pojo.getUrl());
        return workspace;
    }

    public WorkspaceDTO assembleDetails(Workspace workspace) {
        WorkspaceDTO dto = new WorkspaceDTO();
        dto.setId(workspace.getId());
        dto.setName(workspace.getName());
        dto.setUrl(workspace.getUrl());
        dto.setWorkspaceCreator(workspace.getWorkspaceCreator());
        return dto;
    }
}
