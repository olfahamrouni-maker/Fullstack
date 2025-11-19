package com.domo.domo.repository;

import com.domo.domo.model.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {
    Optional<Projet> findByNom(String nom);
    List<Projet> findByNomContainingIgnoreCase(String keyword);
    List<Projet> findByDateDebutBetween(LocalDate debut, LocalDate fin);
}
