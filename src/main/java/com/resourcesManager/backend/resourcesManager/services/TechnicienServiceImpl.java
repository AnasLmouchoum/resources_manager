package com.resourcesManager.backend.resourcesManager.services;

import com.resourcesManager.backend.resourcesManager.entities.Role;
import com.resourcesManager.backend.resourcesManager.entities.Technicien;
import com.resourcesManager.backend.resourcesManager.entities.User;
import com.resourcesManager.backend.resourcesManager.exceptions.EntityAlreadyExistsException;
import com.resourcesManager.backend.resourcesManager.repositories.RoleRepository;
import com.resourcesManager.backend.resourcesManager.repositories.TechnicienRepository;
import com.resourcesManager.backend.resourcesManager.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TechnicienServiceImpl implements TechnicienService {

    private final TechnicienRepository technicienRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AuthenticationServiceImpl authenticationService;

    public TechnicienServiceImpl(TechnicienRepository technicienRepository,
                                 PasswordEncoder passwordEncoder,
                                 RoleRepository roleRepository,
                                 UserRepository userRepository,
                                 AuthenticationServiceImpl authenticationService) {
        this.technicienRepository = technicienRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }


    @Override
    public Technicien addTechnicien(Technicien technicien) {
        User existuser = userRepository.findUserByUsername(technicien.getUsername());
        if (existuser != null) {
            throw new EntityAlreadyExistsException("Username:" + existuser.getUsername() + " already exists !! Try another username");
        }
        technicien.setPassword(passwordEncoder.encode(technicien.getPassword()));
        Role role = authenticationService.saveRole("technicien");
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role);
        technicien.setRoles(roles);
        return technicienRepository.save(technicien);
    }

    @Override
    public Technicien updateTechnicien(Technicien technicien) {
        return technicienRepository.save(technicien);
    }

    @Override
    public void deleteTechnicien(String id) {
        technicienRepository.deleteById(id);
    }

    @Override
    public List<Technicien> getAllTechniciens() {
        return technicienRepository.findAll();
    }

    @Override
    public Technicien getTechnicienById(String id) {
        return technicienRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Le technicien avec l'id = " + id + " est introuvable")
        );
    }
}