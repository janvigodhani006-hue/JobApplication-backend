package com.example.jobApplication.controller;

import com.example.jobApplication.dto.CreateOfferRequest;
import com.example.jobApplication.dto.OfferResponse;
import com.example.jobApplication.dto.UpdateOfferStatusRequest;
import com.example.jobApplication.service.OfferService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping
    public ResponseEntity<OfferResponse> createOffer(
            @Valid @RequestBody CreateOfferRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        OfferResponse response = offerService.createOffer(request, userDetails.getUsername());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OfferResponse>> getAllOffers(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<OfferResponse> offers = offerService.getAllOffers(userDetails.getUsername());
        return ResponseEntity.ok(offers);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OfferResponse> updateOfferStatus(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateOfferStatusRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(offerService.updateOfferStatus(id, request, userDetails.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffer(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails) {
        offerService.deleteOffer(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
