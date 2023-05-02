package com.resourcesManager.backend.resourcesManager.repositories;

import com.resourcesManager.backend.resourcesManager.entities.Imprimante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImprimanteRepository extends JpaRepository<Imprimante, Long> {

    public List<Imprimante> getImprimanteByIdMembreDepartement(String id);
    public List<Imprimante> getImprimanteByIdDepartement(Long id);
    public List<Imprimante> getImprimanteByIdFournisseur(String id);
    public List<Imprimante> findAllByCodeBarreIsNullAndMarqueIsNotNull();
    public List<Imprimante> findAllByCodeBarreIsNotNull();
    void deleteImprimanteById(Long id);
}
