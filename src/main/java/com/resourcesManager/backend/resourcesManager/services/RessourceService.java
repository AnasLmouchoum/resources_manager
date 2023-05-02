package com.resourcesManager.backend.resourcesManager.services;

import com.resourcesManager.backend.resourcesManager.entities.Ressource;
import com.resourcesManager.backend.resourcesManager.entities.RessourceFournisseur;

import java.util.List;

public interface RessourceService {

    List<Ressource> getAllRessources();
    List<Ressource> getRessourcesByMembreDepartement(String id);
    Ressource addRessource(Ressource ressource);

    RessourceFournisseur addRessourceFournisseur(RessourceFournisseur ressourceFournisseur);
    List<Ressource> addMultipleRessources(List<Ressource> ressources);
    Ressource updateRessource(Ressource ressource);
    void deleteRessource(Long id);
    List<Ressource> getRessourcesByDepartement(Long id);

    public List<Ressource> listRessourcesLivrees();

    public List<Ressource> listRessourcesDisponibles();

}
