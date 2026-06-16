package com.arc.controller;

import com.arc.dto.IssueDTO;
import com.arc.entity.Issue;
import com.arc.pojo.IssuePojo;
import com.arc.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.SortDirection;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.arc.payload.response.ApiResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/issue")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService service;

    @PostMapping
    public ResponseEntity<IssueDTO> createIssue(@RequestBody IssuePojo pojo) {
        IssueDTO dto = service.create(pojo);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssueDTO> getIssue(@PathVariable UUID id) {
        IssueDTO dto = service.getIssue(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<List<IssueDTO>> getProjectIssues(@PathVariable UUID id, @RequestParam(defaultValue = "desc") String sortBy) {
        List<IssueDTO> dtos = service.getIssuesByProject(id, sortBy);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IssueDTO> updateIssue(@RequestBody IssuePojo pojo, @PathVariable UUID id) {
        IssueDTO dto =service.update(pojo, id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}
