package com.arc.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Setter
@Getter
public class WorkspaceMemberDTO {
    private UUID id;

    private WorkspaceDTO workspaceId;

    private UserDTO userId;

    private String invitedEmail;

    private Timestamp invitedAt;

    private String name;

    private String title;
}
