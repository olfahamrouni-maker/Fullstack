package com.domo.domo.repository;

import com.domo.domo.model.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, Long> {

    Optional<Employe> findByEmail(String email);

    List<Employe> findByNomContainingIgnoreCase(String keyword);
}
