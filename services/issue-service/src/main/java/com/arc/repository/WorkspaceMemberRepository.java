package com.arc.repository;

import com.arc.dto.WorkspaceSummaryDTO;
import com.arc.entity.WorkspaceMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember, UUID> {

    @Query("""
        SELECT new com.arc.dto.WorkspaceSummaryDTO(w.id, w.name, COUNT(m.id))
            FROM WorkspaceMember wm
            JOIN wm.workspaceId w
            JOIN WorkspaceMember m
                ON m.workspaceId = w
            WHERE wm.userId.id = :userId
        GROUP BY w.id, w.name
    """)
    List<WorkspaceSummaryDTO> findUserWorkspaces(UUID userId);

    List<WorkspaceMember> findByInvitedEmail(String email);
}
