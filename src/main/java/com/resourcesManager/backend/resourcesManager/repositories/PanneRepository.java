package com.resourcesManager.backend.resourcesManager.repositories;

import com.resourcesManager.backend.resourcesManager.entities.Panne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PanneRepository extends JpaRepository<Panne, Long> {

    List<Panne> findPanneByIdMembreDepartement(String id);
    List<Panne> findByConstatIsNotNullAndDemandeIsNull();
    List<Panne> findPanneByIsTreatedFalse();
    List<Panne> findByDemandeIsNotNull();

}
