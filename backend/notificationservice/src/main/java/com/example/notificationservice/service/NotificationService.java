package com.example.notificationservice.service;

import com.example.notificationservice.dto.NotificationResponse;
import com.example.notificationservice.model.NotificationChannel;
import com.example.notificationservice.model.NotificationStatus;
import com.example.notificationservice.model.NotificationType;

import java.util.List;

public interface NotificationService {
    List<NotificationResponse> getAllNotifications();
    List<NotificationResponse> getNotificationsByStatus(NotificationStatus status);
    List<NotificationResponse> getNotificationsByRecipient(Long recipientId);
    void sendNotification(Long recipientId, String recipientEmail, NotificationType type, 
                          NotificationChannel channel, String subject, String content);
}
