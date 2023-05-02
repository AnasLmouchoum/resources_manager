package com.resourcesManager.backend.resourcesManager.repositories;

import com.resourcesManager.backend.resourcesManager.entities.Besoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BesoinRepository extends JpaRepository<Besoin, Long> {

    List<Besoin> findBesoinByIdMembreDepartementAndIsAffectedIsFalse(String id);
    List<Besoin> findBesoinByIdDepartement(Long id);
    List<Besoin> findBesoinByIdMembreDepartement(String id);
    Besoin findBesoinByIdMembreDepartementAndIsBesoinInAppelOffreIsFalse(String id);
    List<Besoin> findBesoinByIdDepartementAndIsBesoinInAppelOffreIsFalse(Long id);
    List<Besoin> findAllByIsBesoinInAppelOffreIsFalse();

}
