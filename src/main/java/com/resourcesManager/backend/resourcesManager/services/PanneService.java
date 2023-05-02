package com.resourcesManager.backend.resourcesManager.services;


import com.resourcesManager.backend.resourcesManager.entities.Panne;

import java.util.List;

public interface PanneService {
    Panne addPanne(Panne panne);
    Panne updatePanne(Panne panne);
    void deletePanne(Long id);
    List<Panne> getAllPannes();
    List<Panne> getPannesMembreDepartement(String id);
    List<Panne> getPanneWithConstatNotNullAndDemandeNull();
    List<Panne> getPannesNotTreated();
    List<Panne> getPanneWithDemandeNotNull();

}
