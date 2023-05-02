package com.resourcesManager.backend.resourcesManager.controllers;

import com.resourcesManager.backend.resourcesManager.entities.Departement;
import com.resourcesManager.backend.resourcesManager.services.DepartementServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departements")
@CrossOrigin("*")
public class DepartementController {

    private final DepartementServiceImpl departementService;

    public DepartementController(DepartementServiceImpl departementService) {
        this.departementService = departementService;
    }

    @GetMapping()
    public ResponseEntity<List<Departement>> getAllDepartements() {
        return ResponseEntity.ok(departementService.getAllDepartements());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Departement> getDepartementById(@PathVariable Long id) {
        return  ResponseEntity.ok(departementService.getDepartementById(id));
    }

    @PostMapping()
    public ResponseEntity<Departement> addDepartement(@RequestBody Departement departement) {
        return ResponseEntity.ok(departementService.addDepartement(departement));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Departement> updateDepartement(@PathVariable Long id, @RequestBody Departement departement) {
        departement.setId(id);
        return ResponseEntity.ok(departementService.updateDepartement(departement));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartement(@PathVariable Long id) {
        departementService.deleteDepartement(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
