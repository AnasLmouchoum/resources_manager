package com.resourcesManager.backend.resourcesManager.services;

import com.resourcesManager.backend.resourcesManager.entities.Panne;
import com.resourcesManager.backend.resourcesManager.repositories.PanneRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PanneServiceImpl implements  PanneService {

    private final PanneRepository panneRepository;

    public PanneServiceImpl(PanneRepository panneRepository) {
        this.panneRepository = panneRepository;
    }

    @Override
    public Panne addPanne(Panne panne) {
        return panneRepository.save(panne);
    }

    @Override
    public Panne updatePanne(Panne panne) {
        return panneRepository.save(panne);
    }

    @Override
    public void deletePanne(Long id) {
        panneRepository.deleteById(id);
    }

    @Override
    public List<Panne> getAllPannes() {
        return panneRepository.findAll();
    }

    @Override
    public List<Panne> getPannesMembreDepartement(String id) {
        return panneRepository.findPanneByIdMembreDepartement(id);
    }

    @Override
    public List<Panne> getPanneWithConstatNotNullAndDemandeNull() {
        return panneRepository.findByConstatIsNotNullAndDemandeIsNull();
    }

    @Override
    public List<Panne> getPannesNotTreated() {
        return panneRepository.findPanneByIsTreatedFalse();
    }

    @Override
    public List<Panne> getPanneWithDemandeNotNull() {
        return panneRepository.findByDemandeIsNotNull();
    }
}
