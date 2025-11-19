package com.domo.domo.service;

import com.domo.domo.model.Departement;
import com.domo.domo.repository.DepartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class DepartementService {
    @Autowired
    private DepartementRepository departementRepository;
    //////////////////////////   1 : CREATION DEPARTEMENT  //////////////////
    public Departement createDepartement(Departement departement) {
        // Vérifier que l'objet Departement n'est pas null
        if (departement == null) {
            throw new RuntimeException("Le département ne peut pas être null");
        }

        // Vérifier que le nom du département n'est pas null ou vide
        if (departement.getNom() == null || departement.getNom().trim().isEmpty()) {
            throw new RuntimeException("Le nom du département est obligatoire");
        }

        // Vérifier que le nom du département n'existe pas déjà
        Optional<Departement> existingDepartement = departementRepository.findByNom(departement.getNom());
        if (existingDepartement.isPresent()) {
            throw new RuntimeException("Un département avec ce nom existe déjà");
        }

        // Sauvegarder le nouveau département
        return departementRepository.save(departement);
    }

    //////////////////////////   2 : LISTE DES DEPARTEMENTS   //////////////////
    public List<Departement> getAllDepartements() {
        List<Departement> departements = departementRepository.findAll();

        if (departements.isEmpty()) {
            throw new RuntimeException("Aucun département trouvé");
        }

        return departements;
    }

    //////////////////////////   3 : RECHERCHE DEPARTEMENT PAR ID   //////////////////
    public Departement getDepartementById(Long id) {
        Optional<Departement> departement = departementRepository.findById(id);

        if (departement.isEmpty()) {
            throw new RuntimeException("Département non trouvé");
        }

        return departement.get();
    }

    //////////////////////////   4 : SUPPRESSION DEPARTEMENT VIA ID    //////////////////
    public void deleteDepartement(Long id) {
        Optional<Departement> optionalDepartement = departementRepository.findById(id);

        if (optionalDepartement.isEmpty()) {
            throw new RuntimeException("Département non trouvé avec ID: " + id);
        }

        Departement departement = optionalDepartement.get();

        // Vérifier si le département a encore des projets
        if (departement.getProjets() != null && !departement.getProjets().isEmpty()) {
            throw new RuntimeException(
                    "Impossible de supprimer le département (ID: " + id + ") car il lance encore des projets"
            );
        }

        // Supprimer le département
        departementRepository.deleteById(id);
    }


    //////////////////////////   5 : MAJ DEPARTEMENT  //////////////////
    public Departement updateDepartement(Long id, Departement upDepartement) {
            // Vérifier si le département existe par ID
            Optional<Departement> existingDept = departementRepository.findById(id);
            if (existingDept.isEmpty()) {
                throw new RuntimeException("Département non trouvé");
            }
            Departement existingDepartement = existingDept.get();

            // Vérifier si le nom proposé existe déjà pour un autre département
            Optional<Departement> departement = departementRepository.findByNom(upDepartement.getNom());
            if (departement.isPresent() && (!departement.get().getId().equals(id))) {
                throw new RuntimeException("Un département avec ce nom existe déjà");
            }

            // Vérifier si le nom est nul ou vide
            if (upDepartement.getNom() == null || upDepartement.getNom().trim().isEmpty()) {
                throw new RuntimeException("Le nom du département est obligatoire");
            }

            // Mettre à jour les informations
            existingDepartement.setNom(upDepartement.getNom());

            // Sauvegarder le département mis à jour
            return departementRepository.save(existingDepartement);
        }

    //////////////////////////   6  : RECHERCHE DEPARTEMENT PAR MOT CLE  //////////////////
    public List<Departement> searchDepartements(String keyword) {
            List<Departement> resultats = departementRepository.findByNomContainingIgnoreCase(keyword);

            /*if (resultats.isEmpty()) {
                throw new RuntimeException("Aucun département trouvé pour le mot-clé: " + keyword);
            }*/

            return resultats;
        }
}


