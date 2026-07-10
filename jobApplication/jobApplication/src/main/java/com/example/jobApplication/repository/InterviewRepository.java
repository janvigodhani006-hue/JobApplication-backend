package com.example.jobApplication.repository;

import com.example.jobApplication.Entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InterviewRepository extends JpaRepository<Interview, UUID>, JpaSpecificationExecutor<Interview> {
    List<Interview> findByUserId(UUID userId);
    Optional<Interview> findByIdAndUserId(UUID id, UUID userId);
}
