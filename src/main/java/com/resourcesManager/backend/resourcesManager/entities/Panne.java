package com.resourcesManager.backend.resourcesManager.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Builder @NoArgsConstructor @AllArgsConstructor @Data

public class Panne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String explication;
    private Date dateApparition;
    private String constat;
    private Date dateConstat;
    private OrdrePanne ordre;
    private PanneFrequence frequence;
    private Boolean isTreated;
    private String idMembreDepartement;
    private String idTechnicien;
    private Long idRessource;
    private PanneAction demande;
}
