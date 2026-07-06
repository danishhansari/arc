package com.arc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class WorkspaceSummaryDTO {
    private UUID workspaceId;
    private String workspaceName;
    private Long workspaceMembers;
}
