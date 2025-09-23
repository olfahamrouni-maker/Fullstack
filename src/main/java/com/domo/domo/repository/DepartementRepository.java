package com.domo.domo.repository;

import com.domo.domo.model.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface DepartementRepository extends JpaRepository<Departement,Long> {
    Optional<Departement> findByNom(String nom);
    //List<Departement> findByNomContainingIgnoreCase(String keyword);
    //@Query("from Departement d where upper(d.nom) like upper(concat('%', :keyword, '%'))")
    ArrayList<Departement> findByNomContainingIgnoreCase(@Param("keyword") String keyword);

}
