package com.example.jobApplication.repository;

import com.example.jobApplication.Entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, UUID>, JpaSpecificationExecutor<Interview> {
    List<Interview> findByUserId(UUID userId);
    Optional<Interview> findByIdAndUserId(UUID id, UUID userId);
    long countByUserId(UUID userId);
}
