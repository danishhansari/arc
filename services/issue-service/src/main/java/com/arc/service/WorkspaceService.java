package com.arc.service;

import com.arc.dto.WorkspaceDTO;
import com.arc.dto.WorkspaceSummaryDTO;
import com.arc.pojo.WorkspaceActivePojo;
import com.arc.pojo.WorkspacePojo;
import java.util.List;
import java.util.UUID;

public interface WorkspaceService {
    WorkspaceDTO create(WorkspacePojo pojo, UUID userId);
    boolean exists(String name);
    List<WorkspaceSummaryDTO> getInvolveWorkspace(UUID userId);
    WorkspaceSummaryDTO getActiveWorkspace(UUID userId);
    WorkspaceDTO updateWorkspace(WorkspaceActivePojo workspaceActivePojo, UUID userId);
}
