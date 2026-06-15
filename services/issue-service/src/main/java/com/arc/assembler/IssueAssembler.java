package com.arc.assembler;

import com.arc.dto.IssueDTO;
import com.arc.entity.Issue;
import com.arc.pojo.IssuePojo;

public class IssueAssembler {

    public static IssueAssembler instance;
    private IssueAssembler() {}

    public static IssueAssembler getInstance() {
        if(instance == null) {
            synchronized (IssueAssembler.class) {
                if(instance == null) {
                    instance = new IssueAssembler();
                }
            }
        }
        return instance;
    }

    public Issue assembleDTO (IssuePojo pojo) {
        Issue issue = new Issue();
        issue.setTitle(pojo.getTitle());
        issue.setDescription(pojo.getDescription());
        issue.setIssueCreator(pojo.getIssueCreator());
        issue.setAssignee(pojo.getAssignee());
        issue.setProjectId(pojo.getProjectId());
        return issue;
    }

    public IssueDTO assembleDetails(Issue issue) {
        IssueDTO dto = new IssueDTO();
        dto.setId(issue.getId());
        dto.setIssueCreator(issue.getIssueCreator());
        dto.setTitle(issue.getTitle());
        dto.setDescription(issue.getDescription());
        dto.setIssueNo(issue.getIssueNo());
        dto.setCreatedAt(issue.getCreatedAt());
        dto.setUpdatedAt(issue.getUpdatedAt());
        dto.setAssignee(issue.getAssignee());
        dto.setProjectId(issue.getProjectId());
        return dto;
    }
}
