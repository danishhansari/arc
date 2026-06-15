package com.arc.repository;

import com.arc.entity.Issue;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IssueRepository extends JpaRepository<Issue, UUID> {

    @Query("""
        SELECT COALESCE(MAX(issueNo), 0) from Issue where projectId = :projectId""")
    Long findTopByProjectIdOrderByIssueNoDesc(@Param("projectId") UUID projectId);

    List<Issue> findAllByProjectId (UUID projectId, Sort sort);
}
