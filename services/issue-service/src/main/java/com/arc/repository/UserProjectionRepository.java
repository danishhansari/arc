package com.arc.repository;

import com.arc.entity.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public interface UserProjectionRepository extends JpaRepository<UserProjection, UUID> {
    Optional<UserProjection> findByEmail(String email);

    List<UserProjection> findByEmailIn(List<String> email);
}
