package com.domo.domo.controller;

import com.domo.domo.service.DepartementProjetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/departement-projet")
public class DepartementProjetController {
    @Autowired
    DepartementProjetService departementProjetService;

    // Affecter projet à département
    @PostMapping("/affecter/{projetId}/departement/{departementId}")
    public ResponseEntity<?> affecterProjetAuDepartement(@PathVariable Long projetId,
                                                         @PathVariable Long departementId) {
        try {
            return ResponseEntity.ok(departementProjetService.affecterProjetAuDepartement(projetId, departementId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Retirer projet d’un département
    @PutMapping("/retirer/{projetId}")
    public ResponseEntity<?> retirerProjetDuDepartement(@PathVariable Long projetId) {
        try {
            return ResponseEntity.ok(departementProjetService.retirerProjetDuDepartement(projetId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Lister projets par département
    @GetMapping("/departement/{departementId}/projets")
    public ResponseEntity<?> getProjetsByDepartement(@PathVariable Long departementId) {
        try {
            return ResponseEntity.ok(departementProjetService.getProjetsByDepartement(departementId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Chercher département d’un projet
    @GetMapping("/projet/{projetId}/departement")
    public ResponseEntity<?> getDepartementByProjet(@PathVariable Long projetId) {
        try {
            return ResponseEntity.ok(departementProjetService.getDepartementByProjet(projetId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
