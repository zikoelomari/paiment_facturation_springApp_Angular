package com.example.factureservice.messaging;

import com.example.factureservice.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendInvoiceOverdueEvent(Long factureId, String numeroFacture, Long clientId) {
        Map<String, Object> event = new HashMap<>();
        event.put("factureId", factureId);
        event.put("numeroFacture", numeroFacture);
        event.put("clientId", clientId);
        event.put("eventType", "invoice.overdue");
        event.put("timestamp", System.currentTimeMillis());

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.BILLING_EXCHANGE,
                RabbitMQConfig.ROUTING_INVOICE_OVERDUE,
                event
        );
        log.info("Sent invoice.overdue event for facture: {}", numeroFacture);
    }
}
