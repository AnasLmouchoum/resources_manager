package com.resourcesManager.backend.resourcesManager.repositories;
import com.resourcesManager.backend.resourcesManager.entities.Technicien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnicienRepository extends JpaRepository<Technicien, String> {
    Technicien findByUsername(String userName);
}