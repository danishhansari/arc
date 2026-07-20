package com.arc.repository;

import com.arc.dto.WorkspaceDTO;
import com.arc.dto.WorkspaceSummaryDTO;
import com.arc.entity.WorkspaceMember;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Query("""
        SELECT new com.arc.dto.WorkspaceDTO(w.id, w.name, w.url, w.workspaceCreator.id)
            FROM WorkspaceMember wm
            JOIN wm.workspaceId w
            JOIN WorkspaceMember m
                ON m.workspaceId = w
            WHERE wm.userId.id = :userId AND
                wm.active = true
        GROUP BY w.id, w.name, w.url, w.workspaceCreator.id
    """)
    WorkspaceDTO findUserActiveWorkspace(UUID userId);

    List<WorkspaceMember> findByInvitedEmail(String email);

    @Modifying
    @Transactional
    @Query("""
        update WorkspaceMember wm
            set wm.active = CASE when wm.workspaceId.id = :workspaceId THEN TRUE
                            ELSE false
                        END
        where wm.workspaceId.id = :workspaceId and wm.userId.id = :userId
    """)
    void updateActive(UUID workspaceId, Boolean active, UUID userId);
}
