package com.arc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Timestamp;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_workspace_email",
        columnNames = {"workspace_id", "invited_email"}
        ),
        @UniqueConstraint(name = "uk_workspace_user_id",
        columnNames = {"workspace_id", "user_id"})
})
public class WorkspaceMember {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workspace_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_workspace_member_workspace")
    )
    private Workspace workspaceId;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(
            name = "user_id",
            foreignKey = @ForeignKey(name = "fk_workspace_member_user")
    )
    private UserProjection userId;

    @Column(name = "invited_email")
    private String invitedEmail;

    @CreationTimestamp
    private Timestamp invitedAt;

    private Boolean active;

    private String name;

    private String title;
}
