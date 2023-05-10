package com.resourcesManager.backend.resourcesManager.services;

import com.resourcesManager.backend.resourcesManager.entities.*;
import com.resourcesManager.backend.resourcesManager.repositories.AppelOffreRepository;
import com.resourcesManager.backend.resourcesManager.repositories.OffreRepository;
import com.resourcesManager.backend.resourcesManager.repositories.RessourceRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service

public class OffreServiceImpl implements   OffreService{
    private  final OffreRepository offreRepository;
    private final AppelOffreRepository appelOffreRepository;
    private final RessourceRepository ressourceRepository;

    private final NotifFournisseurService notifFournisseurService;

    public OffreServiceImpl(OffreRepository offreRepository, AppelOffreRepository appelOffreRepository, RessourceRepository ressourceRepository, NotifFournisseurService notifFournisseurService) {
        this.offreRepository = offreRepository;
        this.appelOffreRepository = appelOffreRepository;
        this.ressourceRepository = ressourceRepository;
        this.notifFournisseurService = notifFournisseurService;
    }


    @Override
    public Offre getOffre(Long id) {
        return offreRepository.findOffreById(id);
    }

    @Override
    public List<Offre> getOffreByAppelOffre(Long idAppelOffre) {
        return  offreRepository.findOffreByIdAppelOffre(idAppelOffre);
    }

    @Override
    public List<Offre> getOffreByFournisseur(String idFournisseur) {
        return offreRepository.findOffreByIdFournisseur(idFournisseur);

    }

    @Override
    public Offre creerOffre(Offre offre) {
        System.out.println(offre.toString());
        return offreRepository.save(offre);
    }

    @Override
    public List<Offre> getAllOffres() {
        return offreRepository.findAll();
    }

    @Override
    public Offre updateOffre(Offre offre) {
        return offreRepository.save(offre);
    }

    @Override
    public void deleteOffre(Long id) {
        offreRepository.deleteById(id);
    }


    @Override
    public void accepterOffre(Offre offre) {
        offre.setIsAffected(true);
        offre.setIsWaiting(false);
        offre.setIsRejected(false);
        AppelOffre appelOffre=this.appelOffreRepository.findAppelOffreById(offre.getIdAppelOffre());
        appelOffre.setIsAffected(true);
        offreRepository.save(offre);
        appelOffreRepository.save(appelOffre);
        for(RessourceFournisseur rf: offre.getRessources()) {
            Ressource ressource = ressourceRepository.findById(rf.getIdRessource()).orElseThrow();
            ressource.setPrix(rf.getPrix());
            ressource.setMarque(rf.getMarque());
            ressource.setIdFournisseur(offre.getIdFournisseur());
            ressourceRepository.save(ressource);
        }
        for (Besoin b: appelOffre.getBesoins()) {
            b.setDateAffectation(Date.valueOf(LocalDate.now()));
        }
        List<Offre> offresRejected=offreRepository.findOffreByIdAppelOffreAndIsAffectedIsFalse(offre.getIdAppelOffre());
        for (Offre o:offresRejected) {
            o.setIsRejected(true);
            o.setIsWaiting(false);
            offreRepository.save(o);
        }

        createNotificationFournisseur(appelOffre, offre.getId());

    }

    public void createNotificationFournisseur(AppelOffre appelOffre, Long offreId) {
        ArrayList<Offre> offres = (ArrayList<Offre>) offreRepository.findOffreByIdAppelOffre(appelOffre.getId());

        for(Offre offre: offres) {
            String idFour = offre.getIdFournisseur();
            NotifFournisseur notifFournisseur = new NotifFournisseur();
            notifFournisseur.setIsSeen(false); notifFournisseur.setDateOffre(offre.getDateDebut());
            notifFournisseur.setIdFournisseur(idFour);
            if(offreId == offre.getId())
                notifFournisseur.setIsAccepted(true);
            else
                notifFournisseur.setIsAccepted(false);
            notifFournisseurService.addNotifFournisseur(notifFournisseur);
        }

    }

}