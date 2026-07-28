package com.example.jobApplication.controller;

import com.example.jobApplication.dto.DashboardStatsResponse;
import com.example.jobApplication.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsResponse> getDashboardStats(
            @AuthenticationPrincipal UserDetails userDetails) {
        DashboardStatsResponse stats = dashboardService.getDashboardStats(userDetails.getUsername());
        return ResponseEntity.ok(stats);
    }
}
