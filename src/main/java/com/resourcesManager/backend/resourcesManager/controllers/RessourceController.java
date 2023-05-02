package com.resourcesManager.backend.resourcesManager.controllers;

import com.resourcesManager.backend.resourcesManager.entities.Ressource;
import com.resourcesManager.backend.resourcesManager.entities.RessourceFournisseur;
import com.resourcesManager.backend.resourcesManager.services.RessourceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ressources")
@CrossOrigin("*")
public class RessourceController {

    private final RessourceService ressourceService;

    public RessourceController(RessourceService ressourceService) {
        this.ressourceService = ressourceService;
    }

    @GetMapping()
    List<Ressource> getAllRessources() {
        return ressourceService.getAllRessources();
    }

    @GetMapping("/membreDepartement/{id}")
    List<Ressource> getRessourcesByMembreDepartement(@PathVariable String id) {
        return ressourceService.getRessourcesByMembreDepartement(id);
    }

    @PostMapping()
    Ressource addRessource(@RequestBody Ressource ressource) {
        return ressourceService.addRessource(ressource);
    }

    @PostMapping("/Fournisseur")
    RessourceFournisseur addRessourceFournisseur(@RequestBody RessourceFournisseur ressourceFournisseur) {
        return ressourceService.addRessourceFournisseur(ressourceFournisseur);
    }

    @PostMapping("/addMultiple")
    List<Ressource> addMultipleRessources(@RequestBody List<Ressource> ressources) {
        return ressourceService.addMultipleRessources(ressources);
    }

    @PutMapping("/{id}")
    Ressource updateRessource(@PathVariable Long id, @RequestBody Ressource ressource) {
        ressource.setId(id);
        return ressourceService.updateRessource(ressource);
    }

    @DeleteMapping("/{id}")
    void deleteRessource(@PathVariable Long id) {
        ressourceService.deleteRessource(id);
    }

    @GetMapping("/departement/{id}")
    List<Ressource> getRessourcesByDepartement(@PathVariable Long id) {
        return ressourceService.getRessourcesByDepartement(id);
    }

    @GetMapping("/ressourcesLivrees")
    List<Ressource> getRessourcesLivrees(){
        return ressourceService.listRessourcesLivrees();
    }


}
