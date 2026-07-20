package com.arc.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceDTO {
    private UUID id;

    private String name;

    private String url;

    private UUID workspaceCreator;
}
