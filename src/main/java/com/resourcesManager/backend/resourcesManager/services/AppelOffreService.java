package com.resourcesManager.backend.resourcesManager.services;

import com.resourcesManager.backend.resourcesManager.entities.AppelOffre;
import com.resourcesManager.backend.resourcesManager.entities.Besoin;
import com.resourcesManager.backend.resourcesManager.repositories.AppelOffreRepository;

import java.util.List;

public interface AppelOffreService  {

    AppelOffre getAppelOffre(Long id);
    List<AppelOffre> getAllAppelOffre();
    void publierAppelOffre(Long id);
    AppelOffre creerAppelOffre();
    void deleteAppelOffre(Long id);

}