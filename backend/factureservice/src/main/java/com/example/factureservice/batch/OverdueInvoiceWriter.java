package com.example.factureservice.batch;

import com.example.factureservice.messaging.RabbitMQProducer;
import com.example.factureservice.model.Facture;
import com.example.factureservice.repository.FactureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OverdueInvoiceWriter implements ItemWriter<Facture> {

    private final FactureRepository factureRepository;
    private final RabbitMQProducer rabbitMQProducer;

    @Override
    public void write(Chunk<? extends Facture> chunk) {
        log.info("Writing {} overdue factures", chunk.size());
        factureRepository.saveAll(chunk.getItems());

        // Publish an event per facture overdue
        chunk.forEach(facture -> rabbitMQProducer.sendInvoiceOverdueEvent(
                facture.getId(),
                facture.getNumeroFacture(),
                facture.getClientId()
        ));
    }
}
