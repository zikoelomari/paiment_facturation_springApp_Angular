package com.example.factureservice.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FactureUpdate {

    @DecimalMin(value = "0.01", message = "Montant must be greater than 0")
    private BigDecimal montant;

    private LocalDate dateEmission;

    private LocalDate dateEcheance;

    private String statut;

    private String description;
}

