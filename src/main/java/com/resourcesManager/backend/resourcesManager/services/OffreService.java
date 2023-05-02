package com.resourcesManager.backend.resourcesManager.services;

import com.resourcesManager.backend.resourcesManager.entities.AppelOffre;
import com.resourcesManager.backend.resourcesManager.entities.Offre;

import java.util.List;

public interface OffreService {

    Offre getOffre(Long id);
    List<Offre> getOffreByAppelOffre(Long idAppelOffre);
    List<Offre> getOffreByFournisseur(String idFournisseur);
    Offre creerOffre(Offre offre);
    List<Offre> getAllOffres();
    Offre updateOffre(Offre offre);
    void deleteOffre(Long id);
    void accepterOffre(Offre offre);

}