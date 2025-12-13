package com.example.paiementservice.mapper;

import com.example.paiementservice.dto.PaiementRequest;
import com.example.paiementservice.dto.PaiementResponse;
import com.example.paiementservice.model.Paiement;
import org.springframework.stereotype.Component;

@Component
public class PaiementMapper {

    public Paiement toEntity(PaiementRequest request) {
        Paiement paiement = new Paiement();
        paiement.setFactureId(request.getFactureId());
        paiement.setMontant(request.getMontant());
        paiement.setMethodePaiement(request.getMethodePaiement());
        return paiement;
    }

    public PaiementResponse toResponse(Paiement paiement) {
        return PaiementResponse.builder()
                .id(paiement.getId())
                .factureId(paiement.getFactureId())
                .montant(paiement.getMontant())
                .methodePaiement(paiement.getMethodePaiement())
                .statut(paiement.getStatut())
                .datePaiement(paiement.getDatePaiement())
                .createdAt(paiement.getCreatedAt())
                .updatedAt(paiement.getUpdatedAt())
                .build();
    }
}

