package com.example.notificationservice.messaging;

import com.example.notificationservice.client.ClientClient;
import com.example.notificationservice.client.FactureClient;
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
public class PaymentCompletedListener {

    private final NotificationService notificationService;
    private final FactureClient factureClient;
    private final ClientClient clientClient;

    @RabbitListener(queues = RabbitMQConfig.PAYMENT_QUEUE)
    public void handlePaymentCompleted(Map<String, Object> event) {
        log.info("Received payment.completed event: {}", event);

        try {
            Long factureId = Long.valueOf(event.get("factureId").toString());
            Long paiementId = Long.valueOf(event.get("paiementId").toString());
            Object montantObj = event.get("montant");

            Map<String, Object> facture = factureClient.getFactureById(factureId);
            Long clientId = Long.valueOf(facture.get("clientId").toString());
            String numeroFacture = facture.get("numeroFacture") != null
                    ? facture.get("numeroFacture").toString()
                    : factureId.toString();

            ClientDto client = clientClient.getClientById(clientId);
            String clientEmail = client.getEmail();
            String clientName = ((client.getFirstName() != null ? client.getFirstName() : "") + " " +
                    (client.getLastName() != null ? client.getLastName() : "")).trim();

            String subject = "Paiement confirmé";
            String content = String.format(
                    "Bonjour %s,%n%nVotre paiement pour la facture %s a été confirmé.%nMontant: %s%n%nMerci pour votre règlement.",
                    clientName.isEmpty() ? "client" : clientName,
                    numeroFacture,
                    montantObj
            );

            notificationService.sendNotification(
                    clientId,
                    clientEmail,
                    NotificationType.SYSTEM_ALERT,
                    NotificationChannel.EMAIL,
                    subject,
                    content
            );

            log.info("Notification sent for payment completed: paiementId={}, clientId={}", paiementId, clientId);
        } catch (Exception e) {
            log.error("Error processing payment.completed event", e);
        }
    }
}
