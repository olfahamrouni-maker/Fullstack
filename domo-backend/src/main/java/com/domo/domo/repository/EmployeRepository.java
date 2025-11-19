package com.domo.domo.repository;

import com.domo.domo.model.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, Long> {

    List<Employe> findByNomContainingIgnoreCase(String keyword);
    Optional<Employe> findByEmail(String email);

    @Query("SELECT e FROM Employe e WHERE " +
            "LOWER(e.nom) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.prenom) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.tel) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Employe> searchByKeyword(@Param("keyword") String keyword);


}
