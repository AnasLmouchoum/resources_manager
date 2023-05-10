package com.resourcesManager.backend.resourcesManager.services;

import com.resourcesManager.backend.resourcesManager.entities.Fournisseur;
import com.resourcesManager.backend.resourcesManager.entities.Role;
import com.resourcesManager.backend.resourcesManager.entities.User;
import com.resourcesManager.backend.resourcesManager.entities.authentication.AuthRequest;
import com.resourcesManager.backend.resourcesManager.entities.authentication.AuthResponse;
import com.resourcesManager.backend.resourcesManager.entities.token.Token;
import com.resourcesManager.backend.resourcesManager.entities.token.TokenType;
import com.resourcesManager.backend.resourcesManager.exceptions.AccountBannedException;
import com.resourcesManager.backend.resourcesManager.exceptions.EntityAlreadyExistsException;
import com.resourcesManager.backend.resourcesManager.exceptions.InvalidCredentialsException;
import com.resourcesManager.backend.resourcesManager.exceptions.NotFoundException;
import com.resourcesManager.backend.resourcesManager.repositories.FournisseurRepository;
import com.resourcesManager.backend.resourcesManager.repositories.RoleRepository;
import com.resourcesManager.backend.resourcesManager.repositories.TokenRepository;
import com.resourcesManager.backend.resourcesManager.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final FournisseurRepository fournisseurRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public User getUserByUserName(String userName) {
        User user = userRepository.findUserByUsername(userName);
        if (user == null)
            throw new NotFoundException("user " + userName + " NOT FOUND !!! ");
        return user;
    }

    @Override
    public User getUserById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user with id: " + userId + " NOT FOUND !!! "));
        return user;
    }

    @Override
    public User saveUser(User user) {
        User existUser = userRepository.findUserByUsername(user.getUsername());
        if (existUser != null)
            throw new EntityAlreadyExistsException("Usename:" + user.getUsername() + " already exists !! Try another username");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findRoleByNomRole("USER");
        user.setRoles(new ArrayList<>());
        user.getRoles().add(role);
        return userRepository.save(user);
    }

    @Override
    public Fournisseur registerFournisseur(Fournisseur fournisseur) {
        User existFournisseur = fournisseurRepository.findByUsername(fournisseur.getUsername());
        if (existFournisseur != null)
            throw new EntityAlreadyExistsException("Username:" + existFournisseur.getUsername() + " already exists !! Try another username");
        fournisseur.setPassword(passwordEncoder.encode(fournisseur.getPassword()));
        Role role = saveRole("fournisseur");
        fournisseur.setRoles(new ArrayList<>());
        fournisseur.getRoles().add(role);
        return fournisseurRepository.save(fournisseur);
    }

    @Override
    public User updateUser(User user) {
        getUserByUserName(user.getUsername());
        return userRepository.save(user);

    }

    @Override
    public Role saveRole(String roleName) {
        Role role = roleRepository.findRoleByNomRole(roleName);
        if (role == null){
            Role savedRole = Role.builder()
                    .nomRole(roleName)
                    .build();
            roleRepository.save(savedRole);
        }
        return roleRepository.findRoleByNomRole(roleName);
    }

    @Override
    public Role getRole(String roleName) {
        Role role = roleRepository.findRoleByNomRole(roleName);
        if (role == null)
            throw new NotFoundException("Role: " + roleName + " not found");
        return role;
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user with id: " + userId + " NOT FOUND !!"));
        userRepository.delete(user);
    }


    @Override

    public AuthResponse authenticate(AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            if (hasRole(request.getUsername(), "fournisseur") && isAccountBlocked(request.getUsername())) {
                return null;
            } else {
                UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
                User user = userRepository.findUserByUsername(userDetails.getUsername());
                String accessToken = jwtService.generateAccessToken(userDetails, user.getId());
                String refreshToken = jwtService.generateRefreshToken(userDetails);

                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                saveUserToken(user, refreshToken);

                return AuthResponse
                        .builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
            }
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException("WRONG CREDENTIALS");
        }
    }


    public boolean hasRole(String userName, String nomRole) {
        User user = userRepository.findUserByUsername(userName);
        Collection<Role> roles = user.getRoles();
        return roles.stream()
                .map(Role::getNomRole)
                .anyMatch(nomRole::equals);
    }

    public AuthResponse refreshToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String refreshToken = authHeader.substring(7);
            String userName = jwtService.extractUsername(refreshToken);
            UserDetails user = userDetailsService.loadUserByUsername(userName);
            String accessToken = jwtService.generateAccessToken(user, request.getRequestId());
            return AuthResponse.builder()
                    .refreshToken(refreshToken)
                    .accessToken(accessToken)
                    .build();
        }
        return null;
    }

    public boolean isAccountBlocked(String userName) {
        Fournisseur fournisseur = fournisseurRepository.findByUsername(userName);
        if (!fournisseur.isBlackList())
            return false;
        else {
            throw new AccountBannedException("Account: Blocked, Motif de blockage: " + fournisseur.getMotifDeBlockage());
        }
    }

    public void revokeAllUserTokens(User user) {
        List<Token> validUserToken = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserToken.isEmpty())
            return;
        validUserToken.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserToken);
    }

    private void saveUserToken(User savedUser, String jwtToken) {
        Token savedAccessToken = Token.builder()
                .user(savedUser)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(savedAccessToken);
    }
}



