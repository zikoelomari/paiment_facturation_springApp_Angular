
package com.example.paiementservice.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.paiementservice.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendPaymentCompletedEvent(Long paiementId, Long factureId, BigDecimal montant) {
        Map<String, Object> event = new HashMap<>();
        event.put("paiementId", paiementId);
        event.put("factureId", factureId);
        event.put("montant", montant);
        event.put("eventType", "payment.completed");
        event.put("timestamp", System.currentTimeMillis());

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.BILLING_EXCHANGE,
                RabbitMQConfig.ROUTING_PAYMENT_COMPLETED,
                event
        );
        log.info("Sent payment.completed event for paiement: {}", paiementId);
    }
}
