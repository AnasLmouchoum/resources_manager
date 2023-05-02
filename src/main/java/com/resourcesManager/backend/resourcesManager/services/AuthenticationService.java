package com.resourcesManager.backend.resourcesManager.services;

import com.resourcesManager.backend.resourcesManager.entities.Fournisseur;
import com.resourcesManager.backend.resourcesManager.entities.Role;
import com.resourcesManager.backend.resourcesManager.entities.User;
import com.resourcesManager.backend.resourcesManager.entities.authentication.AuthRequest;
import com.resourcesManager.backend.resourcesManager.entities.authentication.AuthResponse;
import com.resourcesManager.backend.resourcesManager.exceptions.InvalidCredentialsException;

import java.util.List;

public interface AuthenticationService {
    User getUserByUserName(String userName);

    User getUserById(String userId);

    User saveUser(User user);

    void deleteUser(String userId);

    AuthResponse authenticate(AuthRequest request) throws InvalidCredentialsException;

    Fournisseur registerFournisseur(Fournisseur fournisseur);

    User updateUser(User user);

    Role saveRole(String roleName);

    Role getRole(String roleName);

    List<User> getAllUsers();

    List<Role> getAllRoles();

}
