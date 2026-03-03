package com.music.music.dao.service;

import com.music.music.dao.dto.*;
import com.music.music.dao.entity.User;
import com.music.music.dao.mapper.UserMapper;
import com.music.music.dao.repository.UserRepository;
import com.music.music.dao.service.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests unitaires pour le service d'authentification.
 */
@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private User testUser;
    private UserDto testUserDto;
    private RegisterRequestDto registerRequest;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .pseudo("jdupont")
                .nom("Dupont")
                .prenom("Jean")
                .age(25)
                .adresse("12 rue de Brest")
                .motDePasse("encodedPassword")
                .role("USER")
                .build();

        testUserDto = UserDto.builder()
                .pseudo("jdupont")
                .nom("Dupont")
                .prenom("Jean")
                .age(25)
                .adresse("12 rue de Brest")
                .role("USER")
                .build();

        registerRequest = RegisterRequestDto.builder()
                .pseudo("jdupont")
                .nom("Dupont")
                .prenom("Jean")
                .age(25)
                .adresse("12 rue de Brest")
                .motDePasse("password123")
                .build();
    }

    @Test
    void register_shouldCreateUser_whenPseudoNotTaken() {
        when(userRepository.existsByPseudo("jdupont")).thenReturn(false);
        when(userMapper.toEntity(registerRequest)).thenReturn(testUser);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(jwtService.generateToken(testUser)).thenReturn("jwt-token");
        when(userMapper.toDto(testUser)).thenReturn(testUserDto);

        AuthResponseDto response = authenticationService.register(registerRequest);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals("jdupont", response.getUser().getPseudo());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_shouldThrowConflict_whenPseudoAlreadyExists() {
        when(userRepository.existsByPseudo("jdupont")).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> authenticationService.register(registerRequest));
        verify(userRepository, never()).save(any());
    }

    @Test
    void login_shouldReturnToken_whenCredentialsValid() {
        LoginRequestDto loginRequest = LoginRequestDto.builder()
                .pseudo("jdupont")
                .motDePasse("password123")
                .build();

        when(userRepository.findByPseudo("jdupont")).thenReturn(Optional.of(testUser));
        when(jwtService.generateToken(testUser)).thenReturn("jwt-token");
        when(userMapper.toDto(testUser)).thenReturn(testUserDto);

        AuthResponseDto response = authenticationService.login(loginRequest);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        verify(authenticationManager).authenticate(any());
    }

    @Test
    void getAllUsers_shouldReturnListOfUsers() {
        when(userRepository.findAll()).thenReturn(List.of(testUser));
        when(userMapper.toDto(testUser)).thenReturn(testUserDto);

        List<UserDto> users = authenticationService.getAllUsers();

        assertEquals(1, users.size());
        assertEquals("jdupont", users.get(0).getPseudo());
    }

    @Test
    void getUserByPseudo_shouldReturnUser_whenExists() {
        when(userRepository.findByPseudo("jdupont")).thenReturn(Optional.of(testUser));
        when(userMapper.toDto(testUser)).thenReturn(testUserDto);

        UserDto result = authenticationService.getUserByPseudo("jdupont");

        assertEquals("jdupont", result.getPseudo());
        assertEquals("Dupont", result.getNom());
    }

    @Test
    void getUserByPseudo_shouldThrowNotFound_whenNotExists() {
        when(userRepository.findByPseudo("inconnu")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> authenticationService.getUserByPseudo("inconnu"));
    }

    @Test
    void updateUser_shouldUpdate_whenUserIsSelf() {
        UpdateUserRequestDto updateRequest = UpdateUserRequestDto.builder()
                .nom("NouveauNom")
                .build();

        when(userRepository.findByPseudo("jdupont")).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userMapper.toDto(testUser)).thenReturn(testUserDto);

        UserDto result = authenticationService.updateUser("jdupont", updateRequest, "jdupont");

        assertNotNull(result);
        verify(userMapper).updateEntity(updateRequest, testUser);
        verify(userRepository).save(testUser);
    }

    @Test
    void updateUser_shouldThrowForbidden_whenNotSelfAndNotAdmin() {
        UpdateUserRequestDto updateRequest = UpdateUserRequestDto.builder().nom("Hack").build();

        when(userRepository.findByPseudo("jdupont")).thenReturn(Optional.of(testUser));
        // autreUser n'est pas admin
        User autreUser = User.builder().pseudo("autre").role("USER").build();
        when(userRepository.findByPseudo("autre")).thenReturn(Optional.of(autreUser));

        assertThrows(ResponseStatusException.class,
                () -> authenticationService.updateUser("jdupont", updateRequest, "autre"));
    }

    @Test
    void deleteUser_shouldDelete_whenUserIsSelf() {
        when(userRepository.findByPseudo("jdupont")).thenReturn(Optional.of(testUser));

        authenticationService.deleteUser("jdupont", "jdupont");

        verify(userRepository).delete(testUser);
    }

    @Test
    void deleteUser_shouldThrowNotFound_whenUserNotExists() {
        when(userRepository.findByPseudo("inconnu")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> authenticationService.deleteUser("inconnu", "admin"));
    }
}
