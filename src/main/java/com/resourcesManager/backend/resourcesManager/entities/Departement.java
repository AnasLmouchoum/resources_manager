package com.resourcesManager.backend.resourcesManager.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Builder @NoArgsConstructor @AllArgsConstructor @Data
public class Departement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomDepartement;
    @OneToMany(mappedBy = "departement")
    private Collection<MembreDepartement> membreDepartements = new ArrayList<>();

}
