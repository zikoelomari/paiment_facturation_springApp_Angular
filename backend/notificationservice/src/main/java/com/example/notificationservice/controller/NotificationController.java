package com.example.notificationservice.controller;

import com.example.notificationservice.dto.NotificationResponse;
import com.example.notificationservice.model.NotificationStatus;
import com.example.notificationservice.service.NotificationService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/")
    public String home() {
        return "Notification Service is running!";
    }

    @GetMapping
    @Retry(name = "notificationRetry", fallbackMethod = "fallbackNotificationsCB")
    @CircuitBreaker(name = "notificationCB", fallbackMethod = "fallbackNotificationsCB")
    public ResponseEntity<List<NotificationResponse>> getNotifications() {
        List<NotificationResponse> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<NotificationResponse>> getNotificationsByStatus(@PathVariable("status") final NotificationStatus status) {
        List<NotificationResponse> notifications = notificationService.getNotificationsByStatus(status);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/recipient/{recipientId}")
    public ResponseEntity<List<NotificationResponse>> getNotificationsByRecipient(@PathVariable("recipientId") final Long recipientId) {
        List<NotificationResponse> notifications = notificationService.getNotificationsByRecipient(recipientId);
        return ResponseEntity.ok(notifications);
    }

    public ResponseEntity<List<NotificationResponse>> fallbackNotificationsCB(Exception e) {
        System.err.println("Notification Service Fallback: " + e.getMessage());
        return ResponseEntity.ok(List.of());
    }
}
