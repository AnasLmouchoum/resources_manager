package com.resourcesManager.backend.resourcesManager.services;

import com.resourcesManager.backend.resourcesManager.entities.Besoin;
import com.resourcesManager.backend.resourcesManager.entities.Imprimante;
import com.resourcesManager.backend.resourcesManager.entities.Ordinateur;
import com.resourcesManager.backend.resourcesManager.repositories.ImprimanteRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImprimanteServiceImpl implements ImprimanteService {

    private final ImprimanteRepository imprimanteRepository;
    public ImprimanteServiceImpl(ImprimanteRepository imprimanteRepository) {
        this.imprimanteRepository = imprimanteRepository;
    }

    @Override
    public Imprimante addImprimante(Imprimante imprimante) {
        return imprimanteRepository.save(imprimante);
    }

    @Override
    public List<Imprimante> getAllImprimantes() {
        List<Imprimante> imprimantes = imprimanteRepository.findAll();
        List<Imprimante> imprimanteList = filterImprimantes(imprimantes);
        return imprimanteList;
    }

    @Override
    public List<Imprimante> getImprimantesByMembreDepartement(String id) {
        List<Imprimante> imprimantes = imprimanteRepository.getImprimanteByIdMembreDepartement(id);
        List<Imprimante> imprimanteList = filterImprimantes(imprimantes);
        return imprimanteList;
    }

    @Override
    public List<Imprimante> getImprimantesByDepartement(Long id) {
        List<Imprimante> imprimantes = imprimanteRepository.getImprimanteByIdDepartement(id);
        List<Imprimante> imprimanteList = filterImprimantes(imprimantes);
        return imprimanteList;
    }

    @Override
    public List<Imprimante> getImprimantesByFournisseur(String id) {
        List<Imprimante> imprimantes = imprimanteRepository.getImprimanteByIdFournisseur(id);
        List<Imprimante> imprimanteList = filterImprimantes(imprimantes);
        return imprimanteList;
    }

    @Override
    public Imprimante getImprimante(Long id) {
        Imprimante imprimante = imprimanteRepository.findById(id).orElseThrow(() ->
                new RuntimeException("L'imprimante avec l'id = " + id + " est introuvable")
        );
        if(imprimante.getIsDeleted() == false)
            return imprimante;
        throw new RuntimeException("L'imprimante avec l'id = " + id + " est introuvable");
    }

    @Override
    public Imprimante updateImprimante(Imprimante imprimante) {
        return imprimanteRepository.save(imprimante);
    }

    @Override
    public void deleteImprimante(Long id) {
        Imprimante imprimante = this.getImprimante(id);
        imprimante.setIsDeleted(true);
        imprimanteRepository.save(imprimante);
    }

    public List<Imprimante> filterImprimantes(List<Imprimante> imprimantes) {
        List<Imprimante> imprimanteList = new ArrayList<>();
        for(Imprimante imprimante: imprimantes) {
            if(imprimante.getType().equals("Imprimante") && imprimante.getIsDeleted() == false)
                imprimanteList.add(imprimante);
        }
        return imprimanteList;
    }

    @Override
    public List<Imprimante> getImprimantesLivrees() {
        List<Imprimante> imprimanteNonLivre=filterImprimantes(imprimanteRepository.findAllByCodeBarreIsNullAndMarqueIsNotNull());
        return imprimanteNonLivre;
    }

    @Override
    public List<Imprimante> getImprimantesDisponibles() {
        List<Imprimante> imprimanteDisponible=filterImprimantes(imprimanteRepository.findAllByCodeBarreIsNotNull());
        return imprimanteDisponible;
    }

}
