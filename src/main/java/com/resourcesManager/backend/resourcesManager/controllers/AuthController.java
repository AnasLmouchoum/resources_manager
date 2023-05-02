package com.resourcesManager.backend.resourcesManager.controllers;


import com.resourcesManager.backend.resourcesManager.entities.Fournisseur;
import com.resourcesManager.backend.resourcesManager.entities.Role;
import com.resourcesManager.backend.resourcesManager.entities.User;
import com.resourcesManager.backend.resourcesManager.entities.authentication.AuthRequest;
import com.resourcesManager.backend.resourcesManager.entities.authentication.AuthResponse;
import com.resourcesManager.backend.resourcesManager.exceptions.InvalidCredentialsException;
import com.resourcesManager.backend.resourcesManager.services.AuthenticationServiceImpl;
import com.resourcesManager.backend.resourcesManager.services.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationServiceImpl authenticationService;
    private final LogoutService logoutService;

    @GetMapping
    public List<User> getUsers() {
        return authenticationService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable String userId) {
        return authenticationService.getUserById(userId);
    }

    @PostMapping
    public User saveUser(@RequestBody User user) {
        return authenticationService.saveUser(user);
    }

    @PostMapping("/roles")
    public Role saveRole(@RequestBody Role role) {
        return authenticationService.saveRole(role.getNomRole());
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return authenticationService.getAllRoles();
    }


    @PutMapping("/{userId}")
    public User updateUserById(@PathVariable String userId, @RequestBody User user) {
        user.setId(userId);
        return authenticationService.updateUser(user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId) {
        authenticationService.deleteUser(userId);
    }

    @PostMapping("/register")
    public Fournisseur register( @RequestBody Fournisseur fournisseur) {
        return authenticationService.registerFournisseur(fournisseur);
    }

    @PostMapping("/authenticate")
    public AuthResponse authenticate(@RequestBody AuthRequest request) throws InvalidCredentialsException {
        return authenticationService.authenticate(request);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        logoutService.logout(request, response, authentication);
    }

    @PostMapping("/refreshToken")
    public AuthResponse refreshToken(HttpServletRequest request) {
        return authenticationService.refreshToken(request);
    }


}
