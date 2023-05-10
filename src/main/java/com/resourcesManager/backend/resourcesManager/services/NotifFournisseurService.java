package com.resourcesManager.backend.resourcesManager.services;

import com.resourcesManager.backend.resourcesManager.entities.NotifFournisseur;

import java.util.List;

public interface NotifFournisseurService {

    public List<NotifFournisseur> getNotifsByIdFournisseurNotSeen(String id);

    public void addNotifFournisseur(NotifFournisseur notifFournisseur);

    public void notifSeen(Long id);

}
