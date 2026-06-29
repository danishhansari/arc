package com.arc.controller;

import com.arc.dto.UrlExistsDTO;
import com.arc.dto.WorkspaceDTO;
import com.arc.entity.Workspace;
import com.arc.payload.response.ApiResponse;
import com.arc.pojo.UrlExistsPojo;
import com.arc.pojo.WorkspacePojo;
import com.arc.repository.WorkspaceRepository;
import com.arc.service.WorkspaceService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/workspace")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService workspaceService;
    private final WorkspaceRepository workspaceRepository;

    @GetMapping
    public List<Workspace> getReponse(HttpServletRequest request) {
        String userId = request.getAttribute("x-user-id").toString();
        return workspaceRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<WorkspaceDTO> createWorkspace(@RequestBody WorkspacePojo pojo, HttpServletRequest request) {
        UUID userId = UUID.fromString(request.getAttribute("x-user-id").toString());
        WorkspaceDTO dto = workspaceService.create(pojo, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PostMapping("/exists")
    public ResponseEntity<UrlExistsDTO> workspaceExists(@RequestBody UrlExistsPojo pojo) {
        boolean exists = workspaceService.exists(pojo.getUrl());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new UrlExistsDTO(exists));
    }
}
