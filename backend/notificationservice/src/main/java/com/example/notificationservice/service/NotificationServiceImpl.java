package com.example.notificationservice.service;

import com.example.notificationservice.dto.NotificationResponse;
import com.example.notificationservice.mapper.NotificationMapper;
import com.example.notificationservice.model.Notification;
import com.example.notificationservice.model.NotificationChannel;
import com.example.notificationservice.model.NotificationStatus;
import com.example.notificationservice.model.NotificationType;
import com.example.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getAllNotifications() {
        return StreamSupport.stream(notificationRepository.findAll().spliterator(), false)
                .map(notificationMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotificationsByStatus(NotificationStatus status) {
        return notificationRepository.findByStatus(status)
                .stream()
                .map(notificationMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotificationsByRecipient(Long recipientId) {
        return notificationRepository.findByRecipientId(recipientId)
                .stream()
                .map(notificationMapper::toResponse)
                .toList();
    }

    @Override
    public void sendNotification(Long recipientId, String recipientEmail, NotificationType type,
                                 NotificationChannel channel, String subject, String content) {
        Notification notification = new Notification();
        notification.setRecipientId(recipientId);
        notification.setRecipientEmail(recipientEmail);
        notification.setType(type);
        notification.setChannel(channel);
        notification.setSubject(subject);
        notification.setContent(content);
        notification.setStatus(NotificationStatus.PENDING);
        notification.setCreatedAt(LocalDateTime.now());

        // Simulate sending (in real app, integrate with email/SMS service)
        try {
            log.info("Sending {} notification to {}: {}", channel, recipientEmail, subject);
            // Simulate sending delay
            Thread.sleep(100);
            
            notification.setStatus(NotificationStatus.SENT);
            notification.setSentDate(LocalDateTime.now());
            notification.setDeliveryStatus("delivered");
        } catch (Exception e) {
            log.error("Error sending notification", e);
            notification.setStatus(NotificationStatus.FAILED);
            notification.setErrorMessage(e.getMessage());
        }

        notificationRepository.save(notification);
    }
}

