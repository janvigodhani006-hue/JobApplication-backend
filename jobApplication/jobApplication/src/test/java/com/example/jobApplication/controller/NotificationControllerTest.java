package com.example.jobApplication.controller;

import com.example.jobApplication.dto.NotificationResponse;
import com.example.jobApplication.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetNotifications() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("alex@university.edu");

        NotificationResponse item = new NotificationResponse(
                UUID.randomUUID(),
                "Interview Tomorrow",
                "Stripe Technical Screen at 2:30 PM",
                true,
                "interview",
                OffsetDateTime.now()
        );

        when(notificationService.getUserNotifications("alex@university.edu")).thenReturn(List.of(item));

        ResponseEntity<List<NotificationResponse>> response = notificationController.getNotifications(userDetails);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Interview Tomorrow", response.getBody().get(0).title());
    }

    @Test
    void testMarkAsRead() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("alex@university.edu");
        UUID notifId = UUID.randomUUID();

        doNothing().when(notificationService).markAsRead(notifId, "alex@university.edu");

        ResponseEntity<Void> response = notificationController.markAsRead(notifId, userDetails);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(notificationService, times(1)).markAsRead(notifId, "alex@university.edu");
    }

    @Test
    void testMarkAllAsRead() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("alex@university.edu");

        doNothing().when(notificationService).markAllAsRead("alex@university.edu");

        ResponseEntity<Void> response = notificationController.markAllAsRead(userDetails);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(notificationService, times(1)).markAllAsRead("alex@university.edu");
    }
}
