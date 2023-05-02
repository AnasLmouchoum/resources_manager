package com.resourcesManager.backend.resourcesManager.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Builder @AllArgsConstructor @NoArgsConstructor @Data
public class MembreDepartement  extends User{

    private String domaineExpertise;
    private String laboratoire;
    private Long idDepartement;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
//    @OnDelete(action = OnDeleteAction.CASCADE)
    private Departement departement;

}
