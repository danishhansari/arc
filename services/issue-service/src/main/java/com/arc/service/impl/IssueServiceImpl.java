package com.arc.service.impl;

import com.arc.assembler.IssueAssembler;
import com.arc.dto.IssueDTO;
import com.arc.dto.UserDTO;
import com.arc.entity.Issue;
import com.arc.entity.UserProjection;
import com.arc.pojo.IssuePojo;
import com.arc.repository.IssueRepository;
import com.arc.repository.UserProjectionRepository;
import com.arc.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class IssueServiceImpl implements IssueService {

    private final IssueRepository repository;

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

    @Override
    public IssueDTO update(IssuePojo pojo, UUID id) {
        Issue issue = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue not found"));
        issue.setTitle(pojo.getTitle());
        issue.setDescription(pojo.getDescription());
        issue.setAssignee(pojo.getAssignee());
        Issue updated = repository.save(issue);
        return IssueAssembler.getInstance().assembleDetails(updated);
    }

    @Override
    public IssueDTO getIssue(UUID id) {
        Issue issue = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue not found"));
        return IssueAssembler.getInstance().assembleDetails(issue);
    }
}
