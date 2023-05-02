package com.resourcesManager.backend.resourcesManager.repositories;

import com.resourcesManager.backend.resourcesManager.entities.RessourceFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceFournisseurRepository extends JpaRepository<RessourceFournisseur,Long> {
}