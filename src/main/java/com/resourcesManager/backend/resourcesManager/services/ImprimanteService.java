package com.resourcesManager.backend.resourcesManager.services;

import com.resourcesManager.backend.resourcesManager.entities.Imprimante;
import com.resourcesManager.backend.resourcesManager.entities.Ordinateur;

import java.util.List;

public interface ImprimanteService {

    Imprimante addImprimante(Imprimante imprimante);
    List<Imprimante> getAllImprimantes();
    List<Imprimante> getImprimantesByMembreDepartement(String id);
    List<Imprimante> getImprimantesByDepartement(Long id);
    List<Imprimante> getImprimantesByFournisseur(String id);
    Imprimante getImprimante(Long id);
    Imprimante updateImprimante(Imprimante imprimante);
    void deleteImprimante(Long id);

    List<Imprimante> getImprimantesLivrees();
    List<Imprimante> getImprimantesDisponibles();

}
