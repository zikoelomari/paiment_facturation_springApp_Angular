package com.example.factureservice.batch;

import com.example.factureservice.model.Facture;
import com.example.factureservice.model.FactureStatut;
import com.example.factureservice.repository.FactureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OverdueInvoiceReader implements ItemReader<Facture> {

    private final FactureRepository factureRepository;
    private Iterator<Facture> factureIterator;

    @Override
    public Facture read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (factureIterator == null || !factureIterator.hasNext()) {
            LocalDate today = LocalDate.now();
            List<Facture> overdueFactures = factureRepository.findByStatutAndDateEcheanceBefore(
                FactureStatut.PENDING, today
            );
            factureIterator = overdueFactures.iterator();
        }

        return factureIterator.hasNext() ? factureIterator.next() : null;
    }
}

