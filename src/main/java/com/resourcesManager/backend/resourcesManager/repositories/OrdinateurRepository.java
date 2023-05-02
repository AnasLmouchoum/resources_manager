package com.resourcesManager.backend.resourcesManager.repositories;

import com.resourcesManager.backend.resourcesManager.entities.Ordinateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdinateurRepository extends JpaRepository<Ordinateur, Long> {

    public List<Ordinateur> getOrdinateurByIdMembreDepartement(String id);
    public List<Ordinateur> getOrdinateurByIdDepartement(Long id);
    public List<Ordinateur> getOrdinateurByIdFournisseur(String id);
    public List<Ordinateur> findAllByCodeBarreIsNotNull();
    public List<Ordinateur> findAllByCodeBarreIsNullAndMarqueIsNotNull();

}
