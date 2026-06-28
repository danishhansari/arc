package com.arc.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class WorkspaceDTO {
    private UUID id;

    private String name;

    private String url;

    private UUID workspaceCreator;
}
