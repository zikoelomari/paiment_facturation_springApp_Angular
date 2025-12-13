package com.example.paiementservice.repository;

import com.example.paiementservice.model.Paiement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaiementRepository extends CrudRepository<Paiement, Long> {
    List<Paiement> findByFactureId(Long factureId);
}

