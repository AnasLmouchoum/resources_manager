package com.resourcesManager.backend.resourcesManager.services;

import com.resourcesManager.backend.resourcesManager.entities.Fournisseur;
import com.resourcesManager.backend.resourcesManager.entities.Role;
import com.resourcesManager.backend.resourcesManager.entities.User;
import com.resourcesManager.backend.resourcesManager.entities.authentication.AuthRequest;
import com.resourcesManager.backend.resourcesManager.entities.authentication.AuthResponse;
import com.resourcesManager.backend.resourcesManager.entities.token.Token;
import com.resourcesManager.backend.resourcesManager.exceptions.AccountBannedException;
import com.resourcesManager.backend.resourcesManager.exceptions.EntityAlreadyExistsException;
import com.resourcesManager.backend.resourcesManager.exceptions.NotFoundException;
import com.resourcesManager.backend.resourcesManager.repositories.FournisseurRepository;
import com.resourcesManager.backend.resourcesManager.repositories.RoleRepository;
import com.resourcesManager.backend.resourcesManager.repositories.TokenRepository;
import com.resourcesManager.backend.resourcesManager.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AuthenticationServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private FournisseurRepository fournisseurRepository;
    @InjectMocks
    private AuthenticationServiceImpl authenticationService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private JwtService jwtService;
    @Mock
    private CustomUserDetailsService customUserDetailsService;
    @Mock
    private TokenRepository tokenRepository;


    @Test
    void getAllUsers() {
//        Given
        User user1 = new User();
        user1.setNom("mossaab");
        user1.setPassword("admin123");
        user1.setUsername("moudariga");

        User user2 = new User();
        user2.setNom("anas");
        user2.setPassword("user66");
        user2.setUsername("moudariga");
        List<User> users = Arrays.asList(user1, user2);
//        When
        when(userRepository.findAll()).thenReturn(users);
        List<User> expectedUsers = authenticationService.getAllUsers();
//        Then
        assertEquals(expectedUsers.size(), users.size());
        assertEquals(expectedUsers, users);
    }

    @Test
    void getAllRoles() {
//        Given
        Role admin = Role.builder()
                .id(1L)
                .nomRole("ADMIN")
                .build();
        Role user = Role.builder()
                .id(2L)
                .nomRole("USER")
                .build();
        List<Role> expectedRoles = Arrays.asList(user, admin);
//        When
        when(roleRepository.findAll()).thenReturn(expectedRoles);
        List<Role> savedRoles = authenticationService.getAllRoles();
//        Then
        assertNotNull(savedRoles);
        assertEquals(expectedRoles.size(), savedRoles.size());
        assertEquals(expectedRoles, savedRoles);
    }

    @Test
    void getUserByUserName() {
//        Given
        String userName = "nagato157";
        User exptectedUser = new User();
        exptectedUser.setNom("mossaab");
        exptectedUser.setPassword("admin123");
        exptectedUser.setUsername("moudariga");

//        When
        when(userRepository.findUserByUsername(userName)).thenReturn(exptectedUser);
        User savedUser = authenticationService.getUserByUserName(userName);
//        Then
        assertNotNull(savedUser);
        assertEquals(exptectedUser, savedUser);
    }

    @Test
    void getUserByUserName_ShouldThrowException_WhenUserNotFound() {
        String userName = "nagato157";
        when(userRepository.findUserByUsername(userName)).thenReturn(null);
        assertThrows(NotFoundException.class, () -> {
            authenticationService.getUserByUserName(userName);
        });
    }

    @Test
    void getUserById() {
//        Given
        String userId = "1";
        User exptectedUser = new User();
        exptectedUser.setId(userId);
        exptectedUser.setNom("enseignant");
        exptectedUser.setPassword("enseignant");
        exptectedUser.setEmail("enseignant@gmail.com");
        exptectedUser.setUsername("moudariga");

//        When
        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(exptectedUser));
        User user = authenticationService.getUserById(userId);
//        Then
        assertNotNull(user);
        assertEquals(exptectedUser, user);
    }

    @Test
    void saveUser() {
        String userName = "elbotix";
        String password = "ghost";
        String nomRole = "USER";
        String encodedPassword = "encode_ghost";
//
        Role expectedRole = Role.builder()
                .id(1L)
                .nomRole(nomRole)
                .build();

        ArrayList<Role> roles = new ArrayList<>();
        roles.add(expectedRole);

        User exptectedUser = new User();
        exptectedUser.setUsername(userName);
        exptectedUser.setEmail("mossaab@gmail.com");
        exptectedUser.setPassword(encodedPassword);
        exptectedUser.setRoles(roles);
        User user = new User();
        user.setUsername(userName);
        user.setEmail("mossaab@gmail.com");
        user.setPassword(password);


//
        when(userRepository.findUserByUsername(userName)).thenReturn((null));
        when(roleRepository.findRoleByNomRole(nomRole)).thenReturn(expectedRole);
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(userRepository.save(user)).thenReturn(exptectedUser);

        User savedUser = authenticationService.saveUser(user);
//
        Assertions.assertThat(savedUser).isNotNull();
        assertEquals(exptectedUser, savedUser);
        assertEquals(1, savedUser.getRoles().size());
        assertEquals(roles, savedUser.getRoles());
        assertEquals(encodedPassword, savedUser.getPassword());

    }

    @Test
    public void testSaveUser_userAlreadyExists() {
        // Given
        String userName = "elbotix";
        String password = "ghost";
        User exptectedUser = new User();

        exptectedUser.setUsername(userName);
        exptectedUser.setEmail("mossaab@gmail.com");
        exptectedUser.setPassword(password);

//        When
        when(userRepository.findUserByUsername(userName)).thenReturn(exptectedUser);
//        Then
        EntityAlreadyExistsException exception = assertThrows(EntityAlreadyExistsException.class, () -> {
            authenticationService.saveUser(exptectedUser);
        });
        assertEquals("Usename:" + exptectedUser.getUsername() + " already exists !! Try another username", exception.getMessage());

    }

    @Test
    void registerFournisseur() {
//
        String password = "password_123";
        String encodedPassword = "encoded_password_123";
        String nomRole = "FOURNISSEUR";
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setNomSociete("AL ADARISSA");
        fournisseur.setNom("anass");
        fournisseur.setPassword(password);

        Role expectedRole = Role.builder()
                .id(1L)
                .nomRole(nomRole)
                .build();
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(expectedRole);
        Fournisseur expectedFournisseur = new Fournisseur();
        fournisseur.setNomSociete("AL ADARISSA");
        fournisseur.setNom("anass");
        fournisseur.setPassword(encodedPassword);
        fournisseur.setRoles(roles);
//
        when(fournisseurRepository.findByUsername("anass")).thenReturn(null);
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(roleRepository.findRoleByNomRole(nomRole)).thenReturn(expectedRole);
        when(fournisseurRepository.save(fournisseur)).thenReturn(expectedFournisseur);
        Fournisseur savedFournisseur = authenticationService.registerFournisseur(fournisseur);

//
        assertNotNull(savedFournisseur);
        assertEquals(expectedFournisseur, savedFournisseur);


    }

    @Test
    void updateUser() {
//
        String userName = "enseignant";
        User exptectedUser = new User();

        exptectedUser.setUsername(userName);
        exptectedUser.setEmail("mossaab@gmail.com");
        exptectedUser.setPassword("password_123");


//
        when(userRepository.findUserByUsername(userName)).thenReturn(exptectedUser);
        when(userRepository.save(exptectedUser)).thenReturn(exptectedUser);
        User savedUser = authenticationService.updateUser(exptectedUser);
//
        assertNotNull(savedUser);
        assertEquals(exptectedUser, savedUser);

    }

    @Test
    public void testHasRole_withMatchingRole() {
        // Arrange
        String userName = "testUser";
        String nomRole = "ROLE_ADMIN";
        Role matchingRole = Role.builder()
                .nomRole(nomRole)
                .build();
        User user = new User();
        user.setUsername(userName);
        user.setRoles(Arrays.asList(Role.builder().nomRole("ROLE_USER").build(), matchingRole));

        when(userRepository.findUserByUsername(userName)).thenReturn(user);

        // Act
        boolean result = authenticationService.hasRole(userName, nomRole);

        // Assert

        assertTrue(result);
    }

    @Test
    public void testHasRole_withNonMatchingRole() {
        // Arrange
        String userName = "testUser";
        String nomRole = "ROLE_ADMIN";
        User user = new User();
        user.setUsername(userName);
        user.setRoles(Arrays.asList(Role.builder().nomRole("ROLE_USER").build()));

        when(userRepository.findUserByUsername(userName)).thenReturn(user);

        // Act
        boolean result = authenticationService.hasRole(userName, nomRole);

        // Assert
        assertFalse(result);
        verify(userRepository, times(1)).findUserByUsername(userName);
    }

    @Test
    void saveRole() {
        // Arrange
        String roleName = "ROLE_ADMIN";
        Role existingRole = Role.builder()
                .nomRole(roleName)
                .build();
        when(roleRepository.findRoleByNomRole(roleName)).thenReturn(existingRole);
        // Act
        Role result = authenticationService.saveRole(roleName);
        // Assert
        assertNotNull(result);
        assertEquals(existingRole.getNomRole(), result.getNomRole());
    }

    @Test
    public void testIsAccountBlocked_withNotBlockedAccount() {
        // Arrange
        String userName = "testUser";
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setUsername(userName);
        fournisseur.setBlackList(false);
        when(fournisseurRepository.findByUsername(userName)).thenReturn(fournisseur);

        // Act
        boolean result = authenticationService.isAccountBlocked(userName);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsAccountBlocked_withBlockedAccount(){
        // Arrange
        String userName = "testUser";
        String motifDeBlockage = "Motif de blockage";
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setUsername(userName);
        fournisseur.setBlackList(true);
        fournisseur.setMotifDeBlockage(motifDeBlockage);

        when(fournisseurRepository.findByUsername(userName)).thenReturn(fournisseur);

        // Act
        AccountBannedException exception = assertThrows(AccountBannedException.class, () -> {
            authenticationService.isAccountBlocked(userName);
        });

        // Assert
        assertEquals("Account: Blocked, Motif de blockage: " + motifDeBlockage, exception.getMessage());
    }

    @Test
    void getRole() {
        String nomRole = "enseignant";
        Role expectedRole = Role.builder()
                .nomRole(nomRole)
                .build();
//
        when(roleRepository.findRoleByNomRole(nomRole)).thenReturn(expectedRole);
        Role savedRole = authenticationService.getRole(nomRole);
//
        assertNotNull(savedRole);
        assertEquals(expectedRole, savedRole);
    }


    @Test
    void refreshToken() {
//
        String authHeader = "Bearer refreshToken";
        String userName = "testUser";
        String accessToken = "accessToken";
        String refreshToken = authHeader.substring(7);
        User user = new User();

        user.setUsername(userName);
        user.setEmail("mossaab@gmail.com");
        user.setPassword("password_123");
        AuthResponse expectedResponse = AuthResponse.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .build();
//
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
        when(request.getHeader("Authorization")).thenReturn(authHeader);
        when(jwtService.extractUsername(refreshToken)).thenReturn(userName);
        when(customUserDetailsService.loadUserByUsername(userName)).thenReturn(userDetails);
        when(jwtService.generateAccessToken(userDetails, request.getRequestId())).thenReturn(accessToken);

        AuthResponse result = authenticationService.refreshToken(request);
//
        verify(jwtService, times(1)).generateAccessToken(userDetails, user.getId());
        assertNotNull(result);
        assertEquals("refreshToken", result.getRefreshToken());

    }

    @Test
    void revokeAllUserTokens() {
        User user = new User();
        user.setUsername("enseignant");
        user.setPassword("password_123");

        Token token1 = new Token();
        token1.setUser(user);
        token1.setToken("token1");
        token1.setExpired(false);
        token1.setRevoked(false);
        Token token2 = new Token();
        token2.setUser(user);
        token2.setToken("token2");
        token2.setExpired(false);
        token2.setRevoked(false);
        List<Token> tokens = Arrays.asList(token1, token2);

        when(tokenRepository.findAllValidTokensByUser(user.getId())).thenReturn(tokens);
        when(tokenRepository.saveAll(tokens)).thenReturn(tokens);

        // When
        authenticationService.revokeAllUserTokens(user);

        // Then
        verify(tokenRepository, times(1)).findAllValidTokensByUser(user.getId());
        verify(tokenRepository, times(1)).saveAll(tokens);

        assertTrue(token1.isExpired());
        token1.isRevoked();
        token2.isExpired();
        assertTrue(token2.isRevoked());
    }
}