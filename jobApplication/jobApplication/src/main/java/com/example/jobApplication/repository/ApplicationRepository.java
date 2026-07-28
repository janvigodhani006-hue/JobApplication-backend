package com.example.jobApplication.repository;

import com.example.jobApplication.Entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, UUID>, JpaSpecificationExecutor<Application> {
    List<Application> findByUserId(UUID userId);
    Optional<Application> findByIdAndUserId(UUID id, UUID userId);
    
    long countByUserId(UUID userId);
    long countByUserIdAndStatusNotIn(UUID userId, Collection<String> statuses);
    long countByUserIdAndStatus(UUID userId, String status);

    @Query("SELECT a.status as status, COUNT(a) as count FROM Application a WHERE a.user.id = :userId GROUP BY a.status")
    List<StatusCountProjection> countByStatusForUser(@Param("userId") UUID userId);

    @Query("SELECT a.source as source, COUNT(a) as count FROM Application a WHERE a.user.id = :userId GROUP BY a.source")
    List<SourceCountProjection> countBySourceForUser(@Param("userId") UUID userId);

    interface StatusCountProjection {
        String getStatus();
        long getCount();
    }

    interface SourceCountProjection {
        String getSource();
        long getCount();
    }
}
