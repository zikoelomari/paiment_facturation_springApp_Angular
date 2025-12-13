package com.example.factureservice.batch;

import com.example.factureservice.model.Facture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OverdueInvoiceProcessor implements ItemProcessor<Facture, Facture> {

    @Override
    public Facture process(Facture facture) {
        log.info("Processing overdue facture: {}", facture.getNumeroFacture());

        // Mark as overdue; publishing is handled in the writer
        facture.setStatut(com.example.factureservice.model.FactureStatut.OVERDUE);

        return facture;
    }
}
