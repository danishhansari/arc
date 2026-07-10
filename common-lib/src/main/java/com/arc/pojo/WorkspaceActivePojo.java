package com.arc.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class WorkspaceActivePojo {

    private Boolean active;

    private UUID workspaceId;
}
