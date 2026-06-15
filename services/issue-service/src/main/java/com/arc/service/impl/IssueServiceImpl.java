package com.arc.service.impl;

import com.arc.assembler.IssueAssembler;
import com.arc.dto.IssueDTO;
import com.arc.entity.Issue;
import com.arc.pojo.IssuePojo;
import com.arc.repository.IssueRepository;
import com.arc.service.IssueService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class IssueServiceImpl implements IssueService {

    private final IssueRepository repository;

    public IssueServiceImpl(IssueRepository repository) {
        this.repository = repository;
    }

    @Override
    public IssueDTO create(IssuePojo pojo) {
        Issue issue = IssueAssembler.getInstance().assembleDTO(pojo);
        issue.setIssueNo(repository.findTopByProjectIdOrderByIssueNoDesc(issue.getProjectId()) + 1);
        repository.save(issue);
        return IssueAssembler.getInstance().assembleDetails(issue);
    }

    @Override
    public List<IssueDTO> getIssuesByProject(UUID projectId, String sortDirection) {
        Sort.Direction direction = "ASC".equalsIgnoreCase(sortDirection)
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;
        List<Issue> issues = repository.findAllByProjectId(projectId, Sort.by(direction, "createdAt"));
        return issues.stream().map(k -> IssueAssembler.getInstance().assembleDetails(k)).toList();
    }
}
