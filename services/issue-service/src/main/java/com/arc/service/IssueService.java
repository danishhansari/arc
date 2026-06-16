package com.arc.service;

import com.arc.dto.IssueDTO;
import com.arc.pojo.IssuePojo;

import java.util.List;
import java.util.UUID;

public interface IssueService {
    IssueDTO create(IssuePojo pojo);

    List<IssueDTO> getIssuesByProject(UUID projectId, String sortDirection);

    IssueDTO update(IssuePojo pojo, UUID id);

    IssueDTO getIssue(UUID id);
}
