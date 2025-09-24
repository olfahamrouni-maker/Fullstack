package com.domo.domo.controller;

import com.domo.domo.model.Employe;
import com.domo.domo.service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employes")
public class EmployeController {

    @Autowired
    private EmployeService employeService;

    // CREATION EMPLOYE
    @PostMapping
    public ResponseEntity<?> createEmploye(@RequestBody Employe employe) {
        try {
            Employe e = employeService.createEmploye(employe);
            return ResponseEntity.status(HttpStatus.CREATED).body(e); // 201 si succès
        } catch (RuntimeException ex) {
            // Renvoie le message de l'exception avec un statut BAD_REQUEST
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // LECTURE TOUS LES EMPLOYES
    @GetMapping
    public ResponseEntity<?> getAllEmployes() {
        try {
            List<Employe> list = employeService.getAllEmployes();
            return ResponseEntity.ok(list);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // LECTURE D'UN EMPLOYE VIA SON ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeById(@PathVariable Long id) {
        try {
            Employe e = employeService.getEmployeById(id);
            return ResponseEntity.ok(e);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // MAJ D'UN EMPLOYE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmploye(@PathVariable Long id, @RequestBody Employe employe) {
        try {
            Employe updated = employeService.updateEmploye(id, employe);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // SUPPRESSION D'UN EMPLOYE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmploye(@PathVariable Long id) {
        try {
            employeService.deleteEmploye(id);
            return ResponseEntity.ok("Employé supprimé avec succès");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // RECHERCHE D'UN EMPLOYE VIA UN MOT CLE
    @GetMapping("/search")
    public ResponseEntity<?> searchEmployes(@RequestParam String keyword) {
        try {
            List<Employe> resultats = employeService.searchEmployes(keyword);
            return ResponseEntity.ok(resultats);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
