package com.arc.service.impl;

import com.arc.dto.WorkspaceMemberDTO;
import com.arc.pojo.WorkspaceMemberPojo;
import com.arc.repository.WorkspaceMemberRepository;
import com.arc.service.WorkspaceMemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class WorkspaceMemberServiceImpl implements WorkspaceMemberService {

    private final WorkspaceMemberRepository workspaceMemberRepository;

    @Override
    public WorkspaceMemberDTO create(WorkspaceMemberPojo workspacePojo, UUID userId) {
        WorkspaceMemberDTO dto = new WorkspaceMemberDTO();
        return dto;
    }
}
