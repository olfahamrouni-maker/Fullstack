package com.domo.domo.service;

import com.domo.domo.model.Projet;
import com.domo.domo.repository.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjetService {

    @Autowired
    private ProjetRepository projetRepository;

    // CREATION PROJET
    public Projet createProjet(Projet projet) {
        if (projet == null) {
            throw new RuntimeException("Le projet ne peut pas être null");
        }

        // Vérification du nom
        if (projet.getNom() == null || projet.getNom().trim().isEmpty()) {
            throw new RuntimeException("Le nom du projet est obligatoire");
        }

        // Vérifier unicité du nom
        Optional<Projet> existing = projetRepository.findByNom(projet.getNom());
        if (existing.isPresent()) {
            throw new RuntimeException("Un projet avec ce nom existe déjà");
        }

        // Vérification des dates
        if (projet.getDateDebut() == null || projet.getDateFin() == null) {
            throw new RuntimeException("Les dates de début et de fin sont obligatoires");
        }
        if (projet.getDateDebut().isAfter(projet.getDateFin())) {
            throw new RuntimeException("La date de début doit être antérieure à la date de fin");
        }

        // Sauvegarde
        return projetRepository.save(projet);
    }


    //LISTE PROJETS
    public List<Projet> getAllProjets() {
        List<Projet> projets = projetRepository.findAll();
        if (projets.isEmpty()) {
            throw new RuntimeException("Aucun projet trouvé");
        }
        return projets;
    }

   //LECTURE PROJET PAR ID
   public Projet getProjetById(Long id) {
       Optional<Projet> existing = projetRepository.findById(id);

       if (existing.isEmpty()) {
           throw new RuntimeException("Projet non trouvé");
       }

       return existing.get();
   }


    //MAJ PROJET
    public Projet updateProjet(Long id, Projet upProjet) {
        // Vérifier si le projet existe
        Optional<Projet> existingOpt = projetRepository.findById(id);
        if (existingOpt.isEmpty()) {
            throw new RuntimeException("Projet non trouvé");
        }
        Projet existing = existingOpt.get();

        // Vérification du nom
        if (upProjet.getNom() == null || upProjet.getNom().trim().isEmpty()) {
            throw new RuntimeException("Le nom du projet est obligatoire");
        }

        // Vérifier unicité du nom
        Optional<Projet> nomExist = projetRepository.findByNom(upProjet.getNom());
        if (nomExist.isPresent() && !nomExist.get().getId().equals(id)) {
            throw new RuntimeException("Un projet avec ce nom existe déjà");
        }

        // Vérification des dates
        if (upProjet.getDateDebut() == null || upProjet.getDateFin() == null) {
            throw new RuntimeException("Les dates de début et de fin sont obligatoires");
        }
        if (upProjet.getDateDebut().isAfter(upProjet.getDateFin())) {
            throw new RuntimeException("La date de début doit être antérieure à la date de fin");
        }

        // Mise à jour des informations
        existing.setNom(upProjet.getNom());
        existing.setDateDebut(upProjet.getDateDebut());
        existing.setDateFin(upProjet.getDateFin());

        // Sauvegarde
        return projetRepository.save(existing);
    }


    //  SUPPRESSION PROJET
    public void deleteProjet(Long id) {
        Optional<Projet> existing = projetRepository.findById(id);

        if (existing.isEmpty()) {
            throw new RuntimeException("Projet non trouvé");
        }

        projetRepository.deleteById(id);
    }

    // RECHERCHE PROJET PAR MOT CLE
    public List<Projet> searchProjets(String keyword) {
        List<Projet> resultats = projetRepository.findByNomContainingIgnoreCase(keyword);
        if (resultats.isEmpty()) {
            throw new RuntimeException("Aucun projet trouvé pour le mot-clé: " + keyword);
        }
        return resultats;
    }
}
