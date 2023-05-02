package com.resourcesManager.backend.resourcesManager.services;

import com.resourcesManager.backend.resourcesManager.entities.Demande;

import java.util.List;

public interface DemandeService {

    public void createDemande(Long idDepartement);

    Long getDepartementIdByUserId(String userId);

    public Demande demandeSeen(Long id);
    public List<Demande> getAllDemandesByMembreId(String id);
    public List<Demande> getAllDemandesByIdDepartement(Long id);
    public List<Demande> getAllDemandes();
    public void deleteDemande(Long id);

}
