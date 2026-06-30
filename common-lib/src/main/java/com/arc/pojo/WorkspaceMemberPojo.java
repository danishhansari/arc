package com.arc.pojo;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
public class WorkspaceMemberPojo {
    private UUID workspaceId;

    private UUID userId;

    private List<String> invitedEmail;

    private String name;

    private String title;
}
