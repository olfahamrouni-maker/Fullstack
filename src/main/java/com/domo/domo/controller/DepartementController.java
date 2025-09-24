package com.domo.domo.controller;

import com.domo.domo.model.Departement;
import com.domo.domo.service.DepartementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/departements")
public class DepartementController {
    @Autowired
    private DepartementService departementService;

    //////////////////////////   1   //////////////////
    @PostMapping
    public ResponseEntity<?> createDepartement(@RequestBody Departement departement) {
        try {
            // Appel du service
            Departement newDept = departementService.createDepartement(departement);

            // Retourne HTTP 201 Created + département créé
            return ResponseEntity.status(HttpStatus.CREATED).body(newDept);

        } catch (RuntimeException e) {
            // Retourne HTTP 400 Bad Request + message de l'exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    } //Fin
    //////////////////////////   2   //////////////////
    @GetMapping
    public ResponseEntity<?> getAllDepartements() {
        try {
            List<Departement> departements = departementService.getAllDepartements();
            // Retourne HTTP 200 OK avec la liste
            return ResponseEntity.ok(departements);

        } catch (RuntimeException e) {
            // Retourne HTTP 404 Not Found avec message d'erreur
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
    //////////////////////////   3   //////////////////
    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartementById(@PathVariable Long id) {
        try {
            Departement dept = departementService.getDepartementById(id);
            // Retourne HTTP 200 OK + département
            return ResponseEntity.ok(dept);

        } catch (RuntimeException e) {
            // Retourne HTTP 404 Not Found + message si département absent
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
    //////////////////////////   4   //////////////////
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartement(@PathVariable Long id) {
        try {
            departementService.deleteDepartement(id);
            // Retourne HTTP 200 OK + message de succès
            return ResponseEntity.ok("Département supprimé avec succès");

        } catch (RuntimeException e) {
            // Retourne HTTP 404 Not Found + message d'erreur
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    //////////////////////////   5   //////////////////
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartement(@PathVariable Long id, @RequestBody Departement upDepartement) {
        try {
            // Appel du service pour mettre à jour le département
            Departement updatedDept = departementService.updateDepartement(id, upDepartement);

            // Retourne HTTP 200 OK + département mis à jour
            return ResponseEntity.ok(updatedDept);

        } catch (RuntimeException e) {
            // Retourne HTTP 400 Bad Request + message d'erreur
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    
    //////////////////////////   6   //////////////////
    @GetMapping("/search")
    public ResponseEntity<?> searchDepartements(@RequestParam String keyword) {
        try {
            List<Departement> resultats = departementService.searchDepartements(keyword);
            // Retourne HTTP 200 OK + liste des départements trouvés
            return ResponseEntity.ok(resultats);

        } catch (RuntimeException e) {
            // Retourne HTTP 404 Not Found + message d'erreur
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}

