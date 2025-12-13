package com.example.factureservice.mapper;

import com.example.factureservice.dto.FactureRequest;
import com.example.factureservice.dto.FactureResponse;
import com.example.factureservice.dto.FactureUpdate;
import com.example.factureservice.model.Facture;
import com.example.factureservice.model.FactureStatut;
import org.springframework.stereotype.Component;

@Component
public class FactureMapper {

    public Facture toEntity(FactureRequest request) {
        Facture facture = new Facture();
        facture.setClientId(request.getClientId());
        facture.setMontant(request.getMontant());
        facture.setDateEmission(request.getDateEmission());
        facture.setDateEcheance(request.getDateEcheance());
        facture.setDescription(request.getDescription());
        return facture;
    }

    public FactureResponse toResponse(Facture facture) {
        return FactureResponse.builder()
                .id(facture.getId())
                .numeroFacture(facture.getNumeroFacture())
                .clientId(facture.getClientId())
                .montant(facture.getMontant())
                .dateEmission(facture.getDateEmission())
                .dateEcheance(facture.getDateEcheance())
                .statut(facture.getStatut())
                .description(facture.getDescription())
                .createdAt(facture.getCreatedAt())
                .updatedAt(facture.getUpdatedAt())
                .build();
    }

    public void updateEntity(Facture facture, FactureUpdate update) {
        if (update.getMontant() != null) {
            facture.setMontant(update.getMontant());
        }
        if (update.getDateEmission() != null) {
            facture.setDateEmission(update.getDateEmission());
        }
        if (update.getDateEcheance() != null) {
            facture.setDateEcheance(update.getDateEcheance());
        }
        if (update.getStatut() != null) {
            facture.setStatut(FactureStatut.valueOf(update.getStatut()));
        }
        if (update.getDescription() != null) {
            facture.setDescription(update.getDescription());
        }
    }
}

