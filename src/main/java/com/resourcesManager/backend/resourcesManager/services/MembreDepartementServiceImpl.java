package com.resourcesManager.backend.resourcesManager.services;

import com.resourcesManager.backend.resourcesManager.entities.MembreDepartement;
import com.resourcesManager.backend.resourcesManager.entities.Role;
import com.resourcesManager.backend.resourcesManager.repositories.MembreDepartementRepository;
import com.resourcesManager.backend.resourcesManager.repositories.RoleRepository;
import com.resourcesManager.backend.resourcesManager.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MembreDepartementServiceImpl implements MembreDepartementService {

    private final MembreDepartementRepository membreDepartementRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public MembreDepartementServiceImpl(MembreDepartementRepository membreDepartementRepository, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.membreDepartementRepository = membreDepartementRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<MembreDepartement> getAllMembresDepartement() {
        return membreDepartementRepository.findAll();
    }

    public  MembreDepartement getMembreDepartement(String id) {
        return membreDepartementRepository.findById(id).orElseThrow(() ->
            new RuntimeException("Le membre departement avec l'id = " + id + " est introuvable")
        );
    }

    @Override
    public MembreDepartement addMembreDepartement(MembreDepartement membreDepartement) {
        ArrayList<Role> roles = (ArrayList<Role>) membreDepartement.getRoles();
        System.err.println(roles.size());
        List<Role> rolesMembre = new ArrayList<>();

        if (roles.size() != 0) {
            Role role = roles.get(0);
            if (role.getNomRole().equals("PROF"))
                rolesMembre.add(roleRepository.findRoleByNomRole("PROF"));
            else if (role.getNomRole().equals("CHEF_DEP")) {
                rolesMembre.add(roleRepository.findRoleByNomRole("PROF"));
                rolesMembre.add(roleRepository.findRoleByNomRole("CHEF_DEP"));
            }
        }
        membreDepartement.setPassword(passwordEncoder.encode(membreDepartement.getPassword()));
        membreDepartement.setRoles(rolesMembre);
        return membreDepartementRepository.save(membreDepartement);
    }

    @Override
    public MembreDepartement updateMembreDepartement(MembreDepartement membreDepartement) {
        return membreDepartementRepository.save(membreDepartement);
    }

    @Override
    public List<MembreDepartement> getMembresByIdDepartement(Long idDepartement) {
        return this.membreDepartementRepository.getMembreDepartementByIdDepartement(idDepartement);
    }

    @Override
    public void deleteMembreDepartement(String id) {
        membreDepartementRepository.deleteById(id);
        userRepository.deleteById(id);
    }

}
