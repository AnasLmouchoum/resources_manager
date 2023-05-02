package com.resourcesManager.backend.resourcesManager.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Builder @AllArgsConstructor @NoArgsConstructor @Data
public class AppelOffre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date datePub;
    private Boolean isAffected;
    @OneToMany
    private Collection<Besoin> besoins= new ArrayList<>();


}