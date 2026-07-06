package com.arc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class InvitationWorkspaceDTO {

    private String workspaceName;
    private String inviterName;
    private List<String> emails;
}
