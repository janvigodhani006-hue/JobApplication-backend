package com.example.jobApplication.repository;

import com.example.jobApplication.Entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OfferRepository extends JpaRepository<Offer, UUID>, JpaSpecificationExecutor<Offer> {
    List<Offer> findByUserId(UUID userId);
    Optional<Offer> findByIdAndUserId(UUID id, UUID userId);
    long countByUserId(UUID userId);
}
