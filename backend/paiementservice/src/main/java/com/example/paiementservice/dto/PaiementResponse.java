package com.example.paiementservice.dto;

import com.example.paiementservice.model.PaiementStatut;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaiementResponse {

    private Long id;
    private Long factureId;
    private BigDecimal montant;
    private String methodePaiement;
    private PaiementStatut statut;
    private LocalDateTime datePaiement;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

