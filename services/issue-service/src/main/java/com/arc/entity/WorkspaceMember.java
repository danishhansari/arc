package com.arc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
public class WorkspaceMember {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_workspace_member_workspace")
    )
    private Workspace workspaceId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            foreignKey = @ForeignKey(name = "fk_workspace_member_user")
    )
    private UserProjection userId;

    @Column(nullable = false)
    private String invitedEmail;

    @CreationTimestamp
    private Timestamp invitedAt;

    private String name;

    private String title;
}
