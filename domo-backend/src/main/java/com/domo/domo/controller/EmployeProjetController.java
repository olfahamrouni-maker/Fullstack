package com.domo.domo.controller;

import com.domo.domo.service.EmployeProjetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employe-projet")
public class EmployeProjetController {
    @Autowired
    EmployeProjetService employeProjetService;
    // Affecter un employé à un projet
    @PostMapping("/affecter/{projetId}/a/{employeId}")
    public ResponseEntity<String> affecterEmployeAProjet(
            @PathVariable Long projetId,
            @PathVariable Long employeId) {
        try {
            employeProjetService.affecterEmployeAProjet(projetId, employeId);
            return ResponseEntity.ok("Employé ajouté au projet avec succès");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Retirer un employé d’un projet
    @DeleteMapping("/retirer/{projetId}/de/{employeId}")
    public ResponseEntity<String> retirerEmployeDeProjet(
            @PathVariable Long projetId,
            @PathVariable Long employeId) {
        try {
            employeProjetService.retirerEmployeDeProjet(projetId, employeId);
            return ResponseEntity.ok("Employé retiré du projet avec succès");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Liste des employés d’un projet
    @GetMapping("/projet/{projetId}/employes")
    public ResponseEntity<?> getEmployesByProjet(@PathVariable Long projetId) {
        try {
            return ResponseEntity.ok(employeProjetService.getEmployesByProjet(projetId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Liste des projets d’un employé
    @GetMapping("/employe/{employeId}/projets")
    public ResponseEntity<?> getProjetsByEmploye(@PathVariable Long employeId) {
        try {
            return ResponseEntity.ok(employeProjetService.getProjetsByEmploye(employeId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
