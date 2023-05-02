package com.resourcesManager.backend.resourcesManager.services;

import com.resourcesManager.backend.resourcesManager.entities.RessourceFournisseur;
import com.resourcesManager.backend.resourcesManager.repositories.ResourceFournisseurRepository;
import com.resourcesManager.backend.resourcesManager.entities.Ressource;
import com.resourcesManager.backend.resourcesManager.repositories.RessourceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RessourceServiceImpl implements RessourceService {

    private final RessourceRepository ressourceRepository;
    private  final ResourceFournisseurRepository resourceFournisseurRepository;

    public RessourceServiceImpl(RessourceRepository ressourceRepository, ResourceFournisseurRepository resourceFournisseurRepository) {
        this.ressourceRepository = ressourceRepository;
        this.resourceFournisseurRepository = resourceFournisseurRepository;
    }

    @Override
    public List<Ressource> getAllRessources() {
        return ressourceRepository.findAll();
    }

    @Override
    public List<Ressource> getRessourcesByMembreDepartement(String id) {
        return ressourceRepository.getRessourceByIdMembreDepartement(id);
    }

    @Override
    public Ressource addRessource(Ressource ressource) {
        return ressourceRepository.save(ressource);
    }

    @Override
    public RessourceFournisseur addRessourceFournisseur(RessourceFournisseur ressourceFournisseur) {
        return resourceFournisseurRepository.save(ressourceFournisseur);
    }
    @Override
    public List<Ressource> addMultipleRessources(List<Ressource> ressources) {
        return ressourceRepository.saveAll(ressources);
    }

    @Override
    public Ressource updateRessource(Ressource ressource) {
        return ressourceRepository.save(ressource);
    }

    @Override
    public void deleteRessource(Long id) {
        Ressource ressource = ressourceRepository.findById(id).orElseThrow();
        ressource.setIsDeleted(true);
        ressourceRepository.save(ressource);
    }

    @Override
    public List<Ressource> getRessourcesByDepartement(Long id) {
        return ressourceRepository.getRessourceByIdDepartement(id);
    }

    @Override
    public List<Ressource> listRessourcesLivrees() {
        return ressourceRepository.findAllByCodeBarreIsNull();
    }

    @Override
    public List<Ressource> listRessourcesDisponibles() {
        return ressourceRepository.findAllByCodeBarreIsNullAndMarqueIsNotNull();
    }

}
