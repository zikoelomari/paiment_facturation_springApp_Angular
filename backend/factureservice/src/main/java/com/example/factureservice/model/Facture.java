package com.example.factureservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "factures")
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "facture_seq")
    @SequenceGenerator(name = "facture_seq", sequenceName = "FACTURES_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "numero_facture", unique = true, nullable = false)
    private String numeroFacture;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "montant", nullable = false)
    private BigDecimal montant;

    @Column(name = "date_emission", nullable = false)
    private LocalDate dateEmission;

    @Column(name = "date_echeance")
    private LocalDate dateEcheance;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private FactureStatut statut;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.statut == null) {
            this.statut = FactureStatut.PENDING;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

