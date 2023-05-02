package com.resourcesManager.backend.resourcesManager.repositories;

import com.resourcesManager.backend.resourcesManager.entities.AppelOffre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppelOffreRepository extends JpaRepository<AppelOffre,Long> {
    AppelOffre findAppelOffreById(Long id);
    List<AppelOffre> findAppelOffreByDatePubIsNotNull();
}