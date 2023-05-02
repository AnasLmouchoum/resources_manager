package com.resourcesManager.backend.resourcesManager.services;

import com.resourcesManager.backend.resourcesManager.entities.Demande;
import com.resourcesManager.backend.resourcesManager.entities.Departement;
import com.resourcesManager.backend.resourcesManager.entities.MembreDepartement;
import com.resourcesManager.backend.resourcesManager.exceptions.NotFoundException;
import com.resourcesManager.backend.resourcesManager.repositories.DemandeRepository;
import com.resourcesManager.backend.resourcesManager.repositories.DepartementRepository;
import com.resourcesManager.backend.resourcesManager.repositories.MembreDepartementRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DemandeServiceImpl implements DemandeService {

    private final DemandeRepository demandeRepository;
    private final MembreDepartementRepository membreDepartementRepository;

    private final DepartementRepository departementRepository;
    private final MembreDepartementService membreDepartementService;

    public DemandeServiceImpl(DemandeRepository demandeRepository,
                              MembreDepartementRepository membreDepartementRepository,
                              DepartementRepository departementRepository,
                              MembreDepartementService membreDepartementService) {
        this.demandeRepository = demandeRepository;
        this.membreDepartementRepository = membreDepartementRepository;
        this.departementRepository = departementRepository;
        this.membreDepartementService = membreDepartementService;
    }

    @Override
    public void createDemande(Long idDepartement) {
        ArrayList<Demande> demandes = new ArrayList<>();
        List<MembreDepartement> membresDepartement = this.membreDepartementService.getMembresByIdDepartement(idDepartement);
        for (MembreDepartement membre : membresDepartement) {
            System.err.println("Creating demandes");
            if (membre.getRoles().stream().anyMatch(r -> r.getNomRole().equals("CHEF_DEP"))) {
                continue;
            }
            Demande demande = Demande.builder()
                    .message("Envoyez vos besoins")
                    .dateDemande(Date.valueOf(LocalDate.now()))
                    .idDepartement(idDepartement)
                    .idMembreDepartement(membre.getId())
                    .isSeen(false)
                    .build();
            demandes.add(demande);
        }
        System.err.println(demandes.size());
        System.err.println("Saving demandes");
        demandeRepository.saveAll(demandes);
    }

    @Override
    public Long getDepartementIdByUserId(String userId) {

        Departement departement = departementRepository
                .getDepartementIdByUserId(userId).orElseThrow(() -> new NotFoundException("cannot find departement of user: id" + userId + " not found"));

        return departement.getId();
    }

    @Override
    public Demande demandeSeen(Long id) {
        Demande demande = demandeRepository.findById(id).orElseThrow(() ->
                new RuntimeException("La demande avec l'id = " + id + " est introuvables")
        );
        demande.setIsSeen(true);
        return demandeRepository.save(demande);
    }

    @Override
    public List<Demande> getAllDemandesByMembreId(String id) {
        return demandeRepository.findAllByIdMembreDepartement(id);
    }

    @Override
    public List<Demande> getAllDemandesByIdDepartement(Long id) {
        return demandeRepository.findAllByIdDepartement(id);
    }

    @Override
    public List<Demande> getAllDemandes() {
        return demandeRepository.findAll();
    }

    @Override
    public void deleteDemande(Long id) {
        demandeRepository.deleteById(id);
    }
}
