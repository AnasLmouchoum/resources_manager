package com.resourcesManager.backend.resourcesManager.services;

import com.resourcesManager.backend.resourcesManager.entities.AppelOffre;
import com.resourcesManager.backend.resourcesManager.entities.Besoin;
import com.resourcesManager.backend.resourcesManager.repositories.AppelOffreRepository;
import com.resourcesManager.backend.resourcesManager.repositories.BesoinRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
@Service
public class AppelOffreImpl implements AppelOffreService{
    private final AppelOffreRepository appelOffreRepository;
    private final BesoinRepository besoinRepository;

    public AppelOffreImpl(AppelOffreRepository appelOffreRepository, BesoinRepository besoinRepository) {
        this.appelOffreRepository = appelOffreRepository;
        this.besoinRepository = besoinRepository;
    }

    @Override
    public AppelOffre getAppelOffre(Long id) {
        return appelOffreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appel d'offre not found with id: " + id));
    }


    @Override
    public List<AppelOffre> getAllAppelOffre() {
        return appelOffreRepository.findAll();
    }

    @Override
    public void publierAppelOffre(Long id) {
        AppelOffre appelOffre=appelOffreRepository.findAppelOffreById(id);
        System.out.println(appelOffre.toString());
        System.out.println(Date.valueOf(LocalDate.now()));
        appelOffre.setDatePub(Date.valueOf(LocalDate.now()));
        appelOffreRepository.save(appelOffre);


    }

    @Override
    public AppelOffre creerAppelOffre() {
        AppelOffre newAppelOffre =new AppelOffre();
        newAppelOffre.setBesoins(besoinRepository.findAllByIsBesoinInAppelOffreIsFalse());
        newAppelOffre.setIsAffected(false);
        newAppelOffre.setDatePub(null);
        for(Besoin besoin:newAppelOffre.getBesoins()){
            besoin.setIsBesoinInAppelOffre(true);
            besoinRepository.save(besoin);
        }
        AppelOffre savedAppelOffre = appelOffreRepository.save(newAppelOffre);
        return savedAppelOffre;
    }

    @Override
    public void deleteAppelOffre(Long id) {
        AppelOffre appelOffre=appelOffreRepository.findAppelOffreById(id);
        for(Besoin besoin:appelOffre.getBesoins()){
            besoin.setIsBesoinInAppelOffre(false);
            besoinRepository.save(besoin);
        }
        System.out.println("Hello");
        appelOffreRepository.deleteById(id);
    }

}