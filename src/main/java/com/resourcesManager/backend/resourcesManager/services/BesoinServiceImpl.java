package com.resourcesManager.backend.resourcesManager.services;

import com.resourcesManager.backend.resourcesManager.entities.Besoin;
import com.resourcesManager.backend.resourcesManager.entities.Imprimante;
import com.resourcesManager.backend.resourcesManager.entities.Ordinateur;
import com.resourcesManager.backend.resourcesManager.entities.Ressource;
import com.resourcesManager.backend.resourcesManager.repositories.BesoinRepository;
import com.resourcesManager.backend.resourcesManager.repositories.MembreDepartementRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BesoinServiceImpl implements BesoinService {

    private final BesoinRepository besoinRepository;
    private final MembreDepartementRepository membreDepartementRepository;
    private final RessourceService ressourceService;
    private final OrdinateurService ordinateurService;
    private final ImprimanteService imprimanteService;

    public BesoinServiceImpl(BesoinRepository besoinRepository, MembreDepartementRepository membreDepartementRepository, RessourceService ressourceService, OrdinateurService ordinateurService, ImprimanteService imprimanteService) {
        this.besoinRepository = besoinRepository;
        this.membreDepartementRepository = membreDepartementRepository;
        this.ressourceService = ressourceService;
        this.ordinateurService = ordinateurService;
        this.imprimanteService = imprimanteService;
    }

    @Override
    public Besoin save(Besoin besoin) {
        List<Ordinateur> ordinateurs = (List<Ordinateur>) besoin.getOrdinateurs();
        List<Imprimante> imprimantes = (List<Imprimante>) besoin.getImprimantes();
        List<Ordinateur> ordinateurList = new ArrayList<>();
        List<Imprimante> imprimanteList = new ArrayList<>();
        for(Ordinateur ordinateur: ordinateurs) {
            Ordinateur ordTemp = ordinateurService.addOrdinateur(ordinateur);
            ordinateurList.add(ordTemp);
        }
        for(Imprimante imprimante: imprimantes) {
            Imprimante impTemp = imprimanteService.addImprimante(imprimante);
            imprimanteList.add(impTemp);
        }
//        ressourceService.addMultipleRessources(ressources);
        besoin.setOrdinateurs(ordinateurList);
        besoin.setImprimantes(imprimanteList);
        return besoinRepository.save(besoin);
    }
    @Override
    public List<Besoin> getAllBesoins() {
        return besoinRepository.findAll();
    }

    @Override
    public List<Besoin> getAllBesoinDepartement(Long id) {
        return besoinRepository.findBesoinByIdDepartement(id);
    }
    @Override
    public List<Besoin> getBesoinsMembreDepartement(String id) {
        return besoinRepository.findBesoinByIdMembreDepartement(id);
    }
    @Override
    public List<Besoin> getBesoinsDepartementNotInAppelOffre(Long id) {
        return besoinRepository.findBesoinByIdDepartementAndIsBesoinInAppelOffreIsFalse(id);
    }
    @Override
    public Besoin updateBesoin(Besoin besoin) {
        return besoinRepository.save(besoin);
    }
    @Override
    public Besoin getBesoinMembreDepartementNotInAppelOffre(String id) {
        return besoinRepository.findBesoinByIdMembreDepartementAndIsBesoinInAppelOffreIsFalse(id);
    }
    @Override
    public void besoinAddedInAppelOffre(Long id) {
        Besoin besoin = besoinRepository.findById(id).orElseThrow(() ->
            new RuntimeException("Le besoin avec l'id = " + id + " est introuvable")
        );
        besoin.setIsBesoinInAppelOffre(true);
        besoinRepository.save(besoin);
    }

    @Override
    public List<Besoin> getBesoinsNotInAppelOffre() {
        return besoinRepository.findAllByIsBesoinInAppelOffreIsFalse();
    }

    @Override
    public void deleteBesoinOfMembre(String id) {
        List<Besoin> besoins=besoinRepository.findBesoinByIdMembreDepartementAndIsAffectedIsFalse(id);
        besoinRepository.deleteAll(besoins);
    }
    @Override
    public void deleteBesoin(Long id) {
        besoinRepository.deleteById(id);
    }

}
