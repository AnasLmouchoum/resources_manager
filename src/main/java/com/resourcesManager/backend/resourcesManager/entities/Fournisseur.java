package com.resourcesManager.backend.resourcesManager.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Builder @Data @AllArgsConstructor @NoArgsConstructor
public class Fournisseur extends User {
    private String addresse;
    private String gerant;
    private String nomSociete;

    private boolean isBlackList = false;
    private String motifDeBlockage;
    @OneToMany
    private Collection<Offre> offres = new ArrayList<>();

}