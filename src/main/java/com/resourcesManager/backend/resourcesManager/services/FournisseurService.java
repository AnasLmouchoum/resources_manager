package com.resourcesManager.backend.resourcesManager.services;

import com.resourcesManager.backend.resourcesManager.entities.Fournisseur;

import java.util.List;

public interface FournisseurService {

    public void blackListFournisseur(String id, String motif);

    public Fournisseur updateFournisseur(Fournisseur f);
    public List<Fournisseur> getFournisseur();

}
