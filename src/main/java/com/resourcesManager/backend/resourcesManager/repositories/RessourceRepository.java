package com.resourcesManager.backend.resourcesManager.repositories;

import com.resourcesManager.backend.resourcesManager.entities.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RessourceRepository extends JpaRepository<Ressource, Long> {

    public List<Ressource> getRessourceByIdDepartement(Long id);
    public List<Ressource> getRessourceByIdMembreDepartement(String id);
    public List<Ressource> findAllByCodeBarreIsNullAndMarqueIsNotNull();
    public List<Ressource> findAllByCodeBarreIsNull();

}
