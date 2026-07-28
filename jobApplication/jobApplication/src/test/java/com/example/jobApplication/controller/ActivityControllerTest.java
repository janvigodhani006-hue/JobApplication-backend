package com.example.jobApplication.controller;

import com.example.jobApplication.dto.ActivityResponse;
import com.example.jobApplication.service.ActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ActivityControllerTest {

    @Mock
    private ActivityService activityService;

    @InjectMocks
    private ActivityController activityController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetActivities() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("alex@university.edu");

        Pageable pageable = PageRequest.of(0, 10);
        ActivityResponse item = new ActivityResponse(
                UUID.randomUUID(),
                "applied",
                "Applied to Stripe",
                "Software Engineer",
                OffsetDateTime.now()
        );
        Page<ActivityResponse> page = new PageImpl<>(List.of(item), pageable, 1);

        when(activityService.getUserActivities("alex@university.edu", pageable)).thenReturn(page);

        Page<ActivityResponse> result = activityController.getActivities(userDetails, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("applied", result.getContent().get(0).type());
        assertEquals("Applied to Stripe", result.getContent().get(0).message());
    }
}
