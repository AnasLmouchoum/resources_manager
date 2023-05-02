package com.resourcesManager.backend.resourcesManager.repositories;

import com.resourcesManager.backend.resourcesManager.entities.Departement;
import com.resourcesManager.backend.resourcesManager.entities.MembreDepartement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, Long> {

    Page<Departement> findAll(Pageable pageable);

    @Query("""
                    select d FROM Departement d 
                      join MembreDepartement m
                    where d.id = m.idDepartement
                    and m.id =:userId
                    
            """)
    Optional<Departement> getDepartementIdByUserId(@Param("userId") String userId);

}
