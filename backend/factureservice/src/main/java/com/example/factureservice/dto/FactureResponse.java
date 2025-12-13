package com.example.factureservice.dto;

import com.example.factureservice.model.FactureStatut;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FactureResponse {

    private Long id;
    private String numeroFacture;
    private Long clientId;
    private BigDecimal montant;
    private LocalDate dateEmission;
    private LocalDate dateEcheance;
    private FactureStatut statut;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

