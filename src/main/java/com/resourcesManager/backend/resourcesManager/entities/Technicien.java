package com.resourcesManager.backend.resourcesManager.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder @AllArgsConstructor @NoArgsConstructor @Data
public class Technicien extends User {
    private String specialite;

}
