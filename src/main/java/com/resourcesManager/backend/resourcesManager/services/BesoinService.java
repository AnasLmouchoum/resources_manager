package com.resourcesManager.backend.resourcesManager.services;

import com.resourcesManager.backend.resourcesManager.entities.Besoin;

import java.util.List;

public interface BesoinService {

    Besoin  save(Besoin besoin);
    List<Besoin> getAllBesoins();
    List<Besoin> getAllBesoinDepartement(Long id);
    List<Besoin> getBesoinsMembreDepartement(String id);
    void deleteBesoinOfMembre(String id);
    void deleteBesoin(Long id);
    List<Besoin> getBesoinsDepartementNotInAppelOffre(Long id);
    Besoin updateBesoin(Besoin besoin);
    Besoin getBesoinMembreDepartementNotInAppelOffre(String id);
    List<Besoin> getBesoinsNotInAppelOffre();
    void besoinAddedInAppelOffre(Long id);

}
