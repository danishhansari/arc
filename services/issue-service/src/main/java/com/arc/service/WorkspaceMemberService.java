package com.arc.service;

import com.arc.dto.WorkspaceMemberDTO;
import com.arc.pojo.WorkspaceMemberPojo;

import java.util.UUID;

public interface WorkspaceMemberService {
    WorkspaceMemberDTO create(WorkspaceMemberPojo workspacePojo, UUID userId);
}
