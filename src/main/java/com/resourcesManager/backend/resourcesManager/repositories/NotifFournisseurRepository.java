package com.resourcesManager.backend.resourcesManager.repositories;

import com.resourcesManager.backend.resourcesManager.entities.NotifFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotifFournisseurRepository extends JpaRepository<NotifFournisseur, Long> {

    public List<NotifFournisseur> getNotifFournisseurByIdFournisseurAndIsSeenIsFalse(String idFournisseur);

}
