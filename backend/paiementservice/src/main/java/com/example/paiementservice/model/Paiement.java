package com.example.paiementservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "paiements")
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paiement_seq")
    @SequenceGenerator(name = "paiement_seq", sequenceName = "PAIEMENTS_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "facture_id", nullable = false)
    private Long factureId;

    @Column(name = "montant", nullable = false)
    private BigDecimal montant;

    @Column(name = "methode_paiement", nullable = false)
    private String methodePaiement;

    @Column(name = "statut", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaiementStatut statut;

    @Column(name = "date_paiement")
    private LocalDateTime datePaiement;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.statut == null) {
            this.statut = PaiementStatut.PENDING;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

