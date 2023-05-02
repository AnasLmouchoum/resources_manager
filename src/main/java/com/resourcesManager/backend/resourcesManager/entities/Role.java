package com.resourcesManager.backend.resourcesManager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Builder @NoArgsConstructor @AllArgsConstructor @Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomRole;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Collection<User> users = new ArrayList<>();

}
