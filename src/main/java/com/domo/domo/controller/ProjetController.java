package com.domo.domo.controller;

import com.domo.domo.model.Projet;
import com.domo.domo.service.ProjetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projets")
public class ProjetController {

    @Autowired
    private ProjetService projetService;

    // CREATION PROJET
    @PostMapping
    public ResponseEntity<?> createProjet(@RequestBody Projet projet) {
        try {
            Projet p = projetService.createProjet(projet);
            return ResponseEntity.status(HttpStatus.CREATED).body(p);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //LISTE PROJETS
    @GetMapping
    public ResponseEntity<?> getAllProjets() {
        try {
            List<Projet> list = projetService.getAllProjets();
            return ResponseEntity.ok(list);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //LECTURE PROJET PAR ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getProjetById(@PathVariable Long id) {
        try {
            Projet p = projetService.getProjetById(id);
            return ResponseEntity.ok(p);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //MAJ PROJET
    @PutMapping
    public ResponseEntity<?> updateProjet(@RequestBody Projet projet) {
        try {
            Projet updated = projetService.updateProjet(projet);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // SUPPRESSION PROJET
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProjet(@PathVariable Long id) {
        try {
            projetService.deleteProjet(id);
            return ResponseEntity.ok("Projet supprimé avec succès");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // RECHERCHE PROJET PAR MOT CLE
    @GetMapping("/search")
    public ResponseEntity<?> searchProjets(@RequestParam String keyword) {
        try {
            List<Projet> resultats = projetService.searchProjets(keyword);
            return ResponseEntity.ok(resultats);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}