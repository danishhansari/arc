package com.arc.controller;

import com.arc.dto.IssueDTO;
import com.arc.pojo.IssuePojo;
import com.arc.service.IssueService;
import org.hibernate.query.SortDirection;
import org.springframework.web.bind.annotation.*;
import com.arc.payload.response.ApiResponse;

import java.util.List;
import java.util.UUID;

@RestController
public class IssueController {

    private final IssueService service;

    public IssueController(IssueService service) {
        this.service = service;
    }

    @PostMapping
    public IssueDTO createIssue(@RequestBody IssuePojo pojo) {
        return service.create(pojo);
    }

    @GetMapping("/{id}")
    public List<IssueDTO> getIssues(@PathVariable UUID id, @RequestParam(defaultValue = "desc") String sortBy) {
        return service.getIssuesByProject(id, sortBy);
    }
}
