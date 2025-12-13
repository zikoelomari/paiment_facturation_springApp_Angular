package com.example.factureservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FactureRequest {

    @NotNull(message = "Client ID is required")
    private Long clientId;

    @NotNull(message = "Montant is required")
    @DecimalMin(value = "0.01", message = "Montant must be greater than 0")
    private BigDecimal montant;

    @NotNull(message = "Date emission is required")
    private LocalDate dateEmission;

    private LocalDate dateEcheance;

    private String description;
}

