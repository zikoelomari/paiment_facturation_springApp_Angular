package com.example.factureservice.repository;

import com.example.factureservice.model.Facture;
import com.example.factureservice.model.FactureStatut;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FactureRepository extends CrudRepository<Facture, Long> {
    Optional<Facture> findByNumeroFacture(String numeroFacture);
    List<Facture> findByClientId(Long clientId);
    List<Facture> findByStatut(FactureStatut statut);
    List<Facture> findByStatutAndDateEcheanceBefore(FactureStatut statut, LocalDate date);
}

