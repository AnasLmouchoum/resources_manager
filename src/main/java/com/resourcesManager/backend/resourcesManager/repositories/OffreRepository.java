package com.resourcesManager.backend.resourcesManager.repositories;

import com.resourcesManager.backend.resourcesManager.entities.Offre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OffreRepository extends JpaRepository <Offre,Long> {
    List<Offre> findOffreByIdAppelOffre(Long id);
    List<Offre> findOffreByIdFournisseur(String id);

    List<Offre> findOffreByIdAppelOffreAndIsAffectedIsFalse(Long id);

    Offre findOffreById(Long id);

}