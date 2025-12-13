package com.example.notificationservice.dto;

import com.example.notificationservice.model.NotificationChannel;
import com.example.notificationservice.model.NotificationStatus;
import com.example.notificationservice.model.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {

    private Long id;
    private Long recipientId;
    private String recipientEmail;
    private NotificationType type;
    private NotificationChannel channel;
    private String subject;
    private String content;
    private NotificationStatus status;
    private LocalDateTime sentDate;
    private String deliveryStatus;
    private String errorMessage;
    private Integer retryCount;
    private LocalDateTime createdAt;
}

