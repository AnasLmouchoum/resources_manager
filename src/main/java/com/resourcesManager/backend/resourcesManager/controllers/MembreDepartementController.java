package com.resourcesManager.backend.resourcesManager.controllers;

import com.resourcesManager.backend.resourcesManager.entities.MembreDepartement;
import com.resourcesManager.backend.resourcesManager.services.MembreDepartementServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/membresDepartement")
@CrossOrigin("*")
public class MembreDepartementController {

    private final MembreDepartementServiceImpl membreDepartementService;

    public MembreDepartementController(MembreDepartementServiceImpl membreDepartementService) {
        this.membreDepartementService = membreDepartementService;
    }

    @GetMapping()
    public List<MembreDepartement> getAllMembresDepartement() {
        return membreDepartementService.getAllMembresDepartement();
    }

    @GetMapping("/{id}")
    public MembreDepartement getMembreDepartement(@PathVariable String id) {
        return membreDepartementService.getMembreDepartement(id);
    }

    @PostMapping()
    public MembreDepartement addMembreDepartement(@RequestBody MembreDepartement membreDepartement) {
        return membreDepartementService.addMembreDepartement(membreDepartement);
    }

    @PutMapping("/{id}")
    public MembreDepartement updateMembreDepartement(@PathVariable String id, @RequestBody MembreDepartement membreDepartement) {
        membreDepartement.setId(id);
        return membreDepartementService.updateMembreDepartement(membreDepartement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMembreDepartement(@PathVariable String id) {
        membreDepartementService.deleteMembreDepartement(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
