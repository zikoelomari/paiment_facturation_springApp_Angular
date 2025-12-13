package com.example.notificationservice.messaging;

import com.example.notificationservice.client.ClientClient;
import com.example.notificationservice.config.RabbitMQConfig;
import com.example.notificationservice.dto.ClientDto;
import com.example.notificationservice.model.NotificationChannel;
import com.example.notificationservice.model.NotificationType;
import com.example.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class InvoiceOverdueListener {

    private final NotificationService notificationService;
    private final ClientClient clientClient;

    @RabbitListener(queues = RabbitMQConfig.INVOICE_OVERDUE_QUEUE)
    public void handleInvoiceOverdue(Map<String, Object> event) {
        log.info("Received invoice.overdue event: {}", event);

        try {
            Long factureId = Long.valueOf(event.get("factureId").toString());
            String numeroFacture = event.get("numeroFacture").toString();
            Long clientId = Long.valueOf(event.get("clientId").toString());

            ClientDto client = clientClient.getClientById(clientId);
            String clientEmail = client.getEmail();
            String clientName = ((client.getFirstName() != null ? client.getFirstName() : "") + " " +
                    (client.getLastName() != null ? client.getLastName() : "")).trim();

            String subject = "Facture impayée - Action requise";
            String content = String.format(
                    "Bonjour %s,%n%nVotre facture %s est impayée. Merci de régulariser votre paiement dès que possible.%n%nCordialement.",
                    clientName.isEmpty() ? "client" : clientName,
                    numeroFacture
            );

            notificationService.sendNotification(
                    clientId,
                    clientEmail,
                    NotificationType.SYSTEM_ALERT,
                    NotificationChannel.EMAIL,
                    subject,
                    content
            );

            log.info("Notification sent for overdue invoice: {} (id={})", numeroFacture, factureId);
        } catch (Exception e) {
            log.error("Error processing invoice.overdue event", e);
        }
    }
}
