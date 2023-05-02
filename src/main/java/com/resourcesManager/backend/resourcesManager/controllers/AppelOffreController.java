package com.resourcesManager.backend.resourcesManager.controllers;

import com.resourcesManager.backend.resourcesManager.entities.AppelOffre;
import com.resourcesManager.backend.resourcesManager.services.AppelOffreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/appelOffre")
@CrossOrigin("*")
public class AppelOffreController {
    private final AppelOffreService appelOffreService;

    public AppelOffreController(AppelOffreService appelOffreService) {
        this.appelOffreService = appelOffreService;
    }

    @GetMapping
    public List<AppelOffre> getAllAppelOffre() {
        return appelOffreService.getAllAppelOffre();
    }


    @GetMapping("/{id}")
    AppelOffre getAppelOffre(@PathVariable Long id) {
        return appelOffreService.getAppelOffre(id);
    }

    @PostMapping("/creer")
    AppelOffre creerAppelOffre(){
        return appelOffreService.creerAppelOffre();
    }

    @PutMapping("/publier/{id}")
    void publierAppelOffre( @PathVariable Long id){
        System.out.println("Hello im in publier");
        appelOffreService.publierAppelOffre(id);
    }

    @DeleteMapping("/{id}")
    void deleteAppelOffre(@PathVariable Long id){

        System.out.println("Hello");
        appelOffreService.deleteAppelOffre(id);
    }

}