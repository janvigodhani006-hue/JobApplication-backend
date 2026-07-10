package com.example.jobApplication.service;

import com.example.jobApplication.Entity.Application;
import com.example.jobApplication.Entity.Offer;
import com.example.jobApplication.Entity.User;
import com.example.jobApplication.dto.CreateOfferRequest;
import com.example.jobApplication.dto.OfferResponse;
import com.example.jobApplication.dto.UpdateOfferStatusRequest;
import com.example.jobApplication.exception.ResourceNotFoundException;
import com.example.jobApplication.repository.ApplicationRepository;
import com.example.jobApplication.repository.OfferRepository;
import com.example.jobApplication.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;

    public OfferService(OfferRepository offerRepository, UserRepository userRepository, ApplicationRepository applicationRepository) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
        this.applicationRepository = applicationRepository;
    }

    public OfferResponse createOffer(CreateOfferRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Application application = null;
        if (request.applicationId() != null) {
            application = applicationRepository.findByIdAndUserId(request.applicationId(), user.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Application not found for this user"));
        }

        Offer offer = Offer.builder()
                .company(request.company())
                .role(request.role())
                .base(request.base())
                .equity(request.equity())
                .bonus(request.bonus())
                .location(request.location())
                .deadline(request.deadline())
                .matchPercentage(request.matchPercentage())
                .status("pending") // Default status for new offers
                .user(user)
                .application(application)
                .build();

        offer = offerRepository.save(offer);
        return toResponse(offer);
    }

    public List<OfferResponse> getAllOffers(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return offerRepository.findByUserId(user.getId())
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public OfferResponse updateOfferStatus(UUID id, UpdateOfferStatusRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Offer offer = offerRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Offer not found for this user"));

        offer.setStatus(request.status());
        offer = offerRepository.save(offer);
        return toResponse(offer);
    }

    public void deleteOffer(UUID id, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Offer offer = offerRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Offer not found for this user"));
        offerRepository.delete(offer);
    }

    private OfferResponse toResponse(Offer offer) {
        return new OfferResponse(
                offer.getId(),
                offer.getApplication() != null ? offer.getApplication().getId() : null,
                offer.getCompany(),
                offer.getRole(),
                offer.getBase(),
                offer.getEquity(),
                offer.getBonus(),
                offer.getLocation(),
                offer.getDeadline(),
                offer.getMatchPercentage(),
                offer.getStatus()
        );
    }
}
