package com.domo.domo.service;

import com.domo.domo.repository.EmployeRepository;
import com.domo.domo.repository.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.domo.domo.model.Projet;
import com.domo.domo.model.Employe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeProjetService {

    @Autowired
    EmployeRepository employeRepository;
    @Autowired
    ProjetRepository projetRepository;

    // Affecter un employé à un projet
    public void affecterEmployeAProjet(Long projetId, Long employeId) {
        // Chercher le projet
        Optional<Projet> optionalProjet = projetRepository.findById(projetId);
        if (optionalProjet.isEmpty()) {
            throw new RuntimeException("Projet introuvable");
        }
        Projet projet = optionalProjet.get();

        // Chercher l'employé
        Optional<Employe> optionalEmploye = employeRepository.findById(employeId);
        if (optionalEmploye.isEmpty()) {
            throw new RuntimeException("Employé introuvable");
        }
        Employe employe = optionalEmploye.get();

        // Vérifier si l'employé est déjà affecté
        if (projet.getEmployes().contains(employe)) {
            throw new RuntimeException("Cet employé est déjà affecté à ce projet");
        }

        // Ajouter l'employé au projet
        projet.getEmployes().add(employe);

        // Ajouter le projet à l'employé pour maintenir la cohérence bidirectionnelle
        employe.getProjets().add(projet);

        // Sauvegarder les deux entités
        projetRepository.save(projet);
        employeRepository.save(employe);
    }

    // Retirer un employé d'un projet
    public void retirerEmployeDeProjet(Long projetId, Long employeId) {
        // Chercher le projet
        Optional<Projet> optionalProjet = projetRepository.findById(projetId);
        if (optionalProjet.isEmpty()) {
            throw new RuntimeException("Projet introuvable");
        }
        Projet projet = optionalProjet.get();

        // Chercher l'employé
        Optional<Employe> optionalEmploye = employeRepository.findById(employeId);
        if (optionalEmploye.isEmpty()) {
            throw new RuntimeException("Employé introuvable");
        }
        Employe employe = optionalEmploye.get();

        // Vérifier si l'employé est bien affecté au projet
        if (!projet.getEmployes().contains(employe)) {
            throw new RuntimeException("Cet employé n’est pas affecté à ce projet");
        }

        // Retirer l'employé du projet
        projet.getEmployes().remove(employe);

        // Retirer le projet de la liste de l'employé pour maintenir la cohérence
        employe.getProjets().remove(projet);

        // Sauvegarder les deux entités
        projetRepository.save(projet);
        employeRepository.save(employe);
    }

    //  Lister les employés d’un projet
    public List<Employe> getEmployesByProjet(Long projetId) {
        Optional<Projet> optionalProjet = projetRepository.findById(projetId);
        if (optionalProjet.isEmpty()) {
            throw new RuntimeException("Projet introuvable avec ID: " + projetId);
        }

        Projet projet = optionalProjet.get();
        return new ArrayList<>(projet.getEmployes());
    }

    // Lister les projets d’un employé
    public List<Projet> getProjetsByEmploye(Long employeId) {
        Optional<Employe> optionalEmploye = employeRepository.findById(employeId);
        if (optionalEmploye.isEmpty()) {
            throw new RuntimeException("Employé introuvable avec ID: " + employeId);
        }

        Employe employe = optionalEmploye.get();
        return new ArrayList<>(employe.getProjets());
    }
}
