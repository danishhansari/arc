package com.arc.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IssuePojo {
    private String title;

    private String description;

    private UUID issueCreator;

    private UUID projectId;

    private UUID assignee;
}
