package com.domo.domo.service;

import com.domo.domo.model.Employe;
import com.domo.domo.repository.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeService {

    @Autowired
    private EmployeRepository employeRepository;

    // CREATION EMPLOYE
    public Employe createEmploye(Employe employe) {
        if (employe == null) {
            throw new RuntimeException("L'employé ne peut pas être null");
        }

        // Vérification email
        String email = employe.getEmail();
        if (email == null || email.trim().isEmpty()) {
            throw new RuntimeException("L'email de l'employé est obligatoire");
        }
        // Regex pour format email standard
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new RuntimeException("L'email n'est pas valide");
        }

        // Vérifier unicité de l'email
        Optional<Employe> existing = employeRepository.findByEmail(email);
        if (existing.isPresent()) {
            throw new RuntimeException("Un employé avec cet email existe déjà");
        }

        // Vérification téléphone (8 chiffres)
        String tel = employe.getTel();
        if (tel == null || !tel.matches("^\\d{8}$")) {
            throw new RuntimeException("Le numéro de téléphone doit comporter exactement 8 chiffres");
        }

        // Sauvegarde de l'employé
        return employeRepository.save(employe);
    }


    // LECTURE TOUS LES EMPLOYES
    public List<Employe> getAllEmployes() {
        List<Employe> employes = employeRepository.findAll();
        if (employes.isEmpty()) {
            throw new RuntimeException("Aucun employé trouvé");
        }
        return employes;
    }

    // LECTURE D'UN EMPLOYE VIA SON ID
    public Employe getEmployeById(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));
    }

    // MAJ D'UN EMPLOYE
    public Employe updateEmploye(Employe upEmploye) {
        // Vérifier si l'employé existe
        Employe existing = employeRepository.findById(upEmploye.getId())
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));

        // Validation email
        String email = upEmploye.getEmail();
        if (email == null || email.trim().isEmpty()) {
            throw new RuntimeException("L'email de l'employé est obligatoire");
        }
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new RuntimeException("L'email n'est pas valide");
        }

        // Vérifier unicité de l'email
        Optional<Employe> emailExist = employeRepository.findByEmail(email);
        if (emailExist.isPresent() && !emailExist.get().getId().equals(upEmploye.getId())) {
            throw new RuntimeException("Un employé avec cet email existe déjà");
        }

        // Validation téléphone (exactement 8 chiffres)
        String tel = upEmploye.getTel();
        if (tel == null || !tel.matches("^\\d{8}$")) {
            throw new RuntimeException("Le numéro de téléphone doit comporter exactement 8 chiffres");
        }

        // Mise à jour des informations
        existing.setNom(upEmploye.getNom());
        existing.setPrenom(upEmploye.getPrenom());
        existing.setEmail(email);
        existing.setTel(tel);

        // Sauvegarde
        return employeRepository.save(existing);
    }


    // SUPPRESSION D'UN EMPLOYE
    public void deleteEmploye(Long id) {
        Employe existing = employeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé"));
        employeRepository.deleteById(id);
    }

    // RECHERCHE D'UN EMPLOYE VIA UN MOT CLE
    public List<Employe> searchEmployes(String keyword) {
        List<Employe> resultats = employeRepository.findByNomContainingIgnoreCase(keyword);
        if (resultats.isEmpty()) {
            throw new RuntimeException("Aucun employé trouvé pour le mot-clé: " + keyword);
        }
        return resultats;
    }
}