package com.example.jobApplication.controller;

import com.example.jobApplication.dto.DashboardStatsResponse;
import com.example.jobApplication.dto.MonthlyTrendDTO;
import com.example.jobApplication.dto.SourceBreakdownDTO;
import com.example.jobApplication.dto.StatusBreakdownDTO;
import com.example.jobApplication.service.DashboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DashboardControllerTest {

    @Mock
    private DashboardService dashboardService;

    @InjectMocks
    private DashboardController dashboardController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDashboardStats() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("alex@university.edu");

        DashboardStatsResponse mockStats = new DashboardStatsResponse(
                142,
                12,
                4,
                2,
                28,
                14.2,
                List.of(new MonthlyTrendDTO("Jun", 11, 2)),
                List.of(new StatusBreakdownDTO("Applied", 64, "var(--color-chart-2)")),
                List.of(new SourceBreakdownDTO("LinkedIn", 54))
        );

        when(dashboardService.getDashboardStats("alex@university.edu")).thenReturn(mockStats);

        ResponseEntity<DashboardStatsResponse> response = dashboardController.getDashboardStats(userDetails);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(142, response.getBody().totalApps());
        assertEquals(12, response.getBody().activeApps());
        assertEquals(4, response.getBody().interviewsCount());
        assertEquals(2, response.getBody().offersCount());
        assertEquals(28, response.getBody().rejectionsCount());
        assertEquals(14.2, response.getBody().successRate());
        assertEquals(1, response.getBody().monthlyTrends().size());
        assertEquals(1, response.getBody().statusBreakdowns().size());
        assertEquals(1, response.getBody().sourceBreakdowns().size());
    }
}
