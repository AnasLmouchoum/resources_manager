package com.resourcesManager.backend.resourcesManager.services;

import com.resourcesManager.backend.resourcesManager.entities.Departement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DepartementService {
    List<Departement> getAllDepartements();

    ResponseEntity<Page<Departement>> getMyData(Pageable pageable);

    Departement getDepartementById(Long id);

    Departement addDepartement(Departement departement);

    Departement updateDepartement(Departement departement);

    void deleteDepartement(Long id);
}
