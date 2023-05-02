package com.resourcesManager.backend.resourcesManager.services;

import com.resourcesManager.backend.resourcesManager.entities.Ordinateur;
import com.resourcesManager.backend.resourcesManager.repositories.OrdinateurRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrdinateurServiceImpl implements OrdinateurService {

    private final OrdinateurRepository ordinateurRepository;

    public OrdinateurServiceImpl(OrdinateurRepository ordinateurRepository) {
        this.ordinateurRepository = ordinateurRepository;
    }

    @Override
    public Ordinateur addOrdinateur(Ordinateur ordinateur) {
        return ordinateurRepository.save(ordinateur);
    }

    @Override
    public List<Ordinateur> getAllOrdinateurs() {
        List<Ordinateur> ordinateurs = ordinateurRepository.findAll();
        List<Ordinateur> ordinateurList = filterOrdinateurs(ordinateurs);
        return ordinateurList;
    }

    @Override
    public List<Ordinateur> getOrdinateursByMembreDepartement(String id) {
        List<Ordinateur> ordinateurs = ordinateurRepository.getOrdinateurByIdMembreDepartement(id);
        List<Ordinateur> ordinateurList = filterOrdinateurs(ordinateurs);
        return ordinateurList;
    }

    @Override
    public List<Ordinateur> getOrdinateursByDepartement(Long id) {
        List<Ordinateur> ordinateurs = ordinateurRepository.getOrdinateurByIdDepartement(id);
        List<Ordinateur> ordinateurList = filterOrdinateurs(ordinateurs);
        return ordinateurList;
    }

    @Override
    public List<Ordinateur> getOrdinateursByFournisseur(String id) {
        List<Ordinateur> ordinateurs = ordinateurRepository.getOrdinateurByIdFournisseur(id);
        List<Ordinateur> ordinateurList = filterOrdinateurs(ordinateurs);
        return ordinateurList;
    }

    @Override
    public Ordinateur getOrdinateur(Long id) {
        Ordinateur ordinateur = ordinateurRepository.findById(id).orElseThrow(() ->
                new RuntimeException("L'ordinateur avec l'id = " + id + " est introuvable")
        );
        if(ordinateur.getIsDeleted() == false)
            return ordinateur;
        throw new RuntimeException("L'ordinateur avec l'id = " + id + " est introuvable");
    }

    @Override
    public Ordinateur updateOrdinateur(Ordinateur ordinateur) {
        return ordinateurRepository.save(ordinateur);
    }

    @Override
    public void deleteOrdinateur(Long id) {
        Ordinateur ordinateur = this.getOrdinateur(id);
        ordinateur.setIsDeleted(true);
        ordinateurRepository.save(ordinateur);
    }

    public List<Ordinateur> filterOrdinateurs(List<Ordinateur> ordinateurs) {
        List<Ordinateur> ordinateurList = new ArrayList<>();
        for(Ordinateur ordinateur: ordinateurs) {
            if(ordinateur.getType().equals("Ordinateur") && ordinateur.getIsDeleted() == false)
                ordinateurList.add(ordinateur);
        }
        return ordinateurList;
    }

    @Override
    public List<Ordinateur> getOrdinateurLivrees() {
        List<Ordinateur> ordinateurNonLivre=filterOrdinateurs(ordinateurRepository.findAllByCodeBarreIsNullAndMarqueIsNotNull());
        return ordinateurNonLivre;
    }

    @Override
    public List<Ordinateur> getOrdinateurDisponibles() {
        List<Ordinateur> ordinateurDisponible=filterOrdinateurs(ordinateurRepository.findAllByCodeBarreIsNotNull());
        return ordinateurDisponible;
    }

}
