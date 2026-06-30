package com.arc.controller;

import com.arc.dto.WorkspaceMemberDTO;
import com.arc.pojo.WorkspaceMemberPojo;
import com.arc.service.WorkspaceMemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/workspace/member")
@AllArgsConstructor
public class WorkspaceMemberController {

    private final WorkspaceMemberService workspaceMemberService;

    @PostMapping
    public ResponseEntity<WorkspaceMemberDTO> addMembers(@RequestBody WorkspaceMemberPojo workspacePojo,
                                                         HttpServletRequest request) {
        String userId = (String) request.getAttribute("x-user-id");
        WorkspaceMemberDTO dto = workspaceMemberService.create(workspacePojo, UUID.fromString(userId));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dto);
    }
}
