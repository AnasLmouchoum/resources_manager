package com.resourcesManager.backend.resourcesManager.services;

import com.resourcesManager.backend.resourcesManager.entities.MembreDepartement;
import com.resourcesManager.backend.resourcesManager.repositories.MembreDepartementRepository;
import com.resourcesManager.backend.resourcesManager.repositories.UserRepository;

import java.util.List;

public interface MembreDepartementService {

    public List<MembreDepartement> getAllMembresDepartement();

    public  MembreDepartement getMembreDepartement(String id);

    public MembreDepartement addMembreDepartement(MembreDepartement membreDepartement);

    public MembreDepartement updateMembreDepartement(MembreDepartement membreDepartement);

    public List<MembreDepartement> getMembresByIdDepartement(Long idDepartement);

    public void deleteMembreDepartement(String id);

}
