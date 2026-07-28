package com.example.jobApplication.repository;

import com.example.jobApplication.Entity.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, UUID>, JpaSpecificationExecutor<Activity> {
    Page<Activity> findByUserIdOrderByCreatedAtDesc(UUID userId, Pageable pageable);
    List<Activity> findByUserIdOrderByCreatedAtDesc(UUID userId);
}
