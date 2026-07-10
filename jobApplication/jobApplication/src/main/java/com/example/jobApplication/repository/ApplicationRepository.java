package com.example.jobApplication.repository;

import com.example.jobApplication.Entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<Application, UUID>, JpaSpecificationExecutor<Application> {
    List<Application> findByUserId(UUID userId);
    Optional<Application> findByIdAndUserId(UUID id, UUID userId);
}
