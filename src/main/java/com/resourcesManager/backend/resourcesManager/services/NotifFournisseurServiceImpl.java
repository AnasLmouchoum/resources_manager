package com.resourcesManager.backend.resourcesManager.services;


import com.resourcesManager.backend.resourcesManager.entities.NotifFournisseur;
import com.resourcesManager.backend.resourcesManager.repositories.NotifFournisseurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NotifFournisseurServiceImpl implements NotifFournisseurService {

    private final NotifFournisseurRepository notifFournisseurRepository;

    public NotifFournisseurServiceImpl(NotifFournisseurRepository notifFournisseurRepository) {
        this.notifFournisseurRepository = notifFournisseurRepository;
    }

    @Override
    public List<NotifFournisseur> getNotifsByIdFournisseurNotSeen(String id) {
        return notifFournisseurRepository.getNotifFournisseurByIdFournisseurAndIsSeenIsFalse(id);
    }

    @Override
    public void addNotifFournisseur(NotifFournisseur notifFournisseur) {
        notifFournisseurRepository.save(notifFournisseur);
    }

    @Override
    public void notifSeen(Long id) {
        NotifFournisseur notifFournisseur = notifFournisseurRepository.findById(id).orElseThrow();
        notifFournisseur.setIsSeen(true);
        notifFournisseurRepository.save(notifFournisseur);
    }
}
