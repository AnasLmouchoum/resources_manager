package com.resourcesManager.backend.resourcesManager.controllers;

import com.resourcesManager.backend.resourcesManager.entities.Besoin;
import com.resourcesManager.backend.resourcesManager.services.BesoinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/besoins")
@CrossOrigin("*")
public class BesoinController {

    private final BesoinService besoinService;

    public BesoinController(BesoinService besoinService) {
        this.besoinService = besoinService;
    }

    @GetMapping("")
    public ResponseEntity<List<Besoin>> getAllBesoins() {
        return ResponseEntity.ok(besoinService.getAllBesoins());
    }

    @PostMapping("")
    ResponseEntity<Besoin> save(@RequestBody Besoin besoin) {
        return ResponseEntity.ok(besoinService.save(besoin));
    }

    @GetMapping("/departement/{id}")
    ResponseEntity<List<Besoin>> getAllBesoinDepartement(@PathVariable Long id) {
        return ResponseEntity.ok(besoinService.getAllBesoinDepartement(id));
    }

    @DeleteMapping("/membreDepartement/{id}")
    ResponseEntity<?> deleteBesoinOfMembre(String id) {
        besoinService.deleteBesoinOfMembre(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/membreDepartement/{id}")
    ResponseEntity<List<Besoin>> getBesoinsMembreDepartement(@PathVariable String id) {
        return ResponseEntity.ok(besoinService.getBesoinsMembreDepartement(id));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteBesoin(@PathVariable Long id) {
        besoinService.deleteBesoin(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/membreDepartement/notInOffre/{id}")
    ResponseEntity<List<Besoin>> getBesoinsDepartementNotInAppelOffre(@PathVariable Long id) {
        return ResponseEntity.ok(besoinService.getBesoinsDepartementNotInAppelOffre(id));
    }

    @PutMapping("/{id}")
    ResponseEntity<Besoin> updateBesoin(@PathVariable Long id, @RequestBody Besoin besoin) {
        besoin.setId(id);
        return ResponseEntity.ok(besoinService.updateBesoin(besoin));
    }

    @GetMapping("/departement/notInOffre/{id}")
    ResponseEntity<Besoin> getBesoinMembreDepartementNotInAppelOffre(String id) {
        return ResponseEntity.ok(besoinService.getBesoinMembreDepartementNotInAppelOffre(id));
    }

    @PutMapping("/addedInoffre/{id}")
    ResponseEntity<?> besoinAddedInAppelOffre(@PathVariable Long id) {
        besoinService.besoinAddedInAppelOffre(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/NotInAppelOffre")
    List<Besoin> getBesoinsNotInAppelOffre(){
        return besoinService.getBesoinsNotInAppelOffre();
    }

}
