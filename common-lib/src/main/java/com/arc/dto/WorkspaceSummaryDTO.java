package com.arc.dto;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceSummaryDTO {
    private UUID workspaceId;
    private String workspaceName;
    private Long workspaceMembers;
}
