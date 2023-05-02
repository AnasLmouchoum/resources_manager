package com.resourcesManager.backend.resourcesManager.services;

import com.resourcesManager.backend.resourcesManager.entities.Fournisseur;
import com.resourcesManager.backend.resourcesManager.repositories.FournisseurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FournisseurServiceImpl implements FournisseurService {

    private final FournisseurRepository fournisseurRepository;

    public FournisseurServiceImpl(FournisseurRepository fournisseurRepository) {
        this.fournisseurRepository = fournisseurRepository;
    }

    @Override
    public void blackListFournisseur(String id, String motif) {
        Fournisseur fournisseur = fournisseurRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Fournisseur introuvable")
        );
        fournisseur.setBlackList(true);
        fournisseur.setMotifDeBlockage(motif);
        fournisseurRepository.save(fournisseur);
    }

    @Override
    public Fournisseur updateFournisseur(Fournisseur f) {
        return fournisseurRepository.save(f);
    }

    @Override
    public List<Fournisseur> getFournisseur(){
        return fournisseurRepository.findAll();
    }
}
