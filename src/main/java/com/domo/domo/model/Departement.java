package com.domo.domo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
//@Data
public class Departement {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String nom;

    // Relation OneToMany avec Projet
    @OneToMany(mappedBy = "departement")
    @JsonIgnore
    private List<Projet> projets = new ArrayList<>();

    //les getters et les setters
    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public List<Projet> getProjets() {
        return projets;
    }

    public void setProjets(ArrayList<Projet> projets) {
        this.projets = projets;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
