package com.arc.repository;

import com.arc.entity.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserProjectionRepository extends JpaRepository<UserProjection, UUID> {
}
