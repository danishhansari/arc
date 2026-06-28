package com.arc.dto;

import com.arc.pojo.WorkspacePojo;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class VerificationDTO {

    private String message;

    private String jwt;

    private UserDTO user;

    private List<WorkspaceDTO> organization;
}
