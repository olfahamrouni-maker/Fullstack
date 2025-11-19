package com.domo.domo.service;

import com.domo.domo.model.Departement;
import com.domo.domo.model.Projet;
import com.domo.domo.repository.DepartementRepository;
import com.domo.domo.repository.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartementProjetService {

    @Autowired
    DepartementRepository departementRepository;
    @Autowired
    ProjetRepository projetRepository;

    // Affecter un projet à un département
    public Projet affecterProjetAuDepartement(Long projetId, Long departementId) {
        Optional<Projet> optionalProjet = projetRepository.findById(projetId);
        if (optionalProjet.isEmpty()) {
            throw new RuntimeException("Projet introuvable avec ID: " + projetId);
        }

        Optional<Departement> optionalDepartement = departementRepository.findById(departementId);
        if (optionalDepartement.isEmpty()) {
            throw new RuntimeException("Département introuvable avec ID: " + departementId);
        }

        Projet projet = optionalProjet.get();
        Departement departement = optionalDepartement.get();

        // Vérifier si le projet est déjà affecté à ce département
        if (projet.getDepartement() != null && projet.getDepartement().getId().equals(departementId)) {
            throw new RuntimeException("Le projet (ID: " + projetId + ") est déjà affecté au département (ID: " + departementId + ")");
        }

        // Mettre à jour les deux côtés de la relation
        projet.setDepartement(departement);
        departement.getProjets().add(projet);

        // Sauvegarde (côté projet, mais grâce au mapping JPA, côté département aussi sera cohérent)
        return projetRepository.save(projet);
    }

    // Retirer un projet de son département
    public Projet retirerProjetDuDepartement(Long projetId) {
        Optional<Projet> optionalProjet = projetRepository.findById(projetId);
        if (optionalProjet.isEmpty()) {
            throw new RuntimeException("Projet introuvable avec ID: " + projetId);
        }

        Projet projet = optionalProjet.get();
        Departement departement = projet.getDepartement();

        if (departement == null) {
            throw new RuntimeException("Ce projet n'est affecté à aucun département");
        }

        // Retirer le projet de la liste du département
        departement.getProjets().remove(projet);

        // Détacher le département du projet
        projet.setDepartement(null);

        // Sauvegarder les deux pour garder la cohérence
        departementRepository.save(departement);
        return projetRepository.save(projet);
    }

    // Lister tous les projets d’un département
    public List<Projet> getProjetsByDepartement(Long departementId) {
        Optional<Departement> optionalDepartement = departementRepository.findById(departementId);
        if (optionalDepartement.isEmpty()) {
            throw new RuntimeException("Département introuvable avec ID: " + departementId);
        }

        Departement departement = optionalDepartement.get();
        if (departement.getProjets() == null || departement.getProjets().isEmpty()) {
            throw new RuntimeException("Aucun projet trouvé pour ce département");
        }

        return new ArrayList<>(departement.getProjets());
    }

    // Chercher le département d’un projet
    public Departement getDepartementByProjet(Long projetId) {
        Optional<Projet> optionalProjet = projetRepository.findById(projetId);
        if (optionalProjet.isEmpty()) {
            throw new RuntimeException("Projet introuvable avec ID: " + projetId);
        }

        Projet projet = optionalProjet.get();
        if (projet.getDepartement() == null) {
            throw new RuntimeException("Ce projet n'est associé à aucun département");
        }

        return projet.getDepartement();
    }
}

