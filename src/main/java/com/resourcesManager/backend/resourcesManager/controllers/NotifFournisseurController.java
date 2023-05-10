package com.resourcesManager.backend.resourcesManager.controllers;


import com.resourcesManager.backend.resourcesManager.entities.NotifFournisseur;
import com.resourcesManager.backend.resourcesManager.services.NotifFournisseurService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifFournisseur")
@CrossOrigin("*")
public class NotifFournisseurController {

    private final NotifFournisseurService notifFournisseurService;

    public NotifFournisseurController(NotifFournisseurService notifFournisseurService) {
        this.notifFournisseurService = notifFournisseurService;
    }

    @GetMapping("/fournisseur/{id}")
    public List<NotifFournisseur> getNotifsFournisseur(@PathVariable String id) {
        return notifFournisseurService.getNotifsByIdFournisseurNotSeen(id);
    }

    @PutMapping("/{id}")
    public void setNotifFournisseurSeen(@PathVariable Long id) {
        notifFournisseurService.notifSeen(id);
    }


}
