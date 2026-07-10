package com.example.jobApplication.repository;

import com.example.jobApplication.Entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ResumeRepository extends JpaRepository<Resume, UUID> {

    /**
     * Finds all resumes belonging to a specific user.
     * @param userId The ID of the user.
     * @return A list of resumes.
     */
    List<Resume> findByUserId(UUID userId);

    /**
     * Finds a specific resume by its ID and the user's ID to ensure ownership.
     * @param id The ID of the resume.
     * @param userId The ID of the user.
     * @return An Optional containing the resume if found.
     */
    Optional<Resume> findByIdAndUserId(UUID id, UUID userId);

    /**
     * Counts how many applications are associated with a specific resume.
     * This is a placeholder for a more complex query if you link resumes to applications.
     * For now, we will simulate this by returning 0.
     * @param resumeId The ID of the resume.
     * @return The number of applications linked to the resume.
     */
    @Query("SELECT COUNT(a) FROM Application a WHERE a.id = :resumeId") // This is a placeholder query
    int countApplicationsByResumeId(@Param("resumeId") UUID resumeId);
}
