package com.arc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IssueDTO {

    private UUID id;

    private Long issueNo;

    private String title;

    private String description;

    private UUID issueCreator;

    private UUID projectId;

    private UUID assignee;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
