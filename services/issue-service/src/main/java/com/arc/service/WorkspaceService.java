package com.arc.service;

import com.arc.dto.WorkspaceDTO;
import com.arc.pojo.WorkspacePojo;

import java.util.UUID;

public interface WorkspaceService {
    WorkspaceDTO create(WorkspacePojo pojo, UUID userId);
    boolean exists(String name);
}
