package com.service;

import com.dto.*;
import com.entity.User;
import com.mapper.UserMapper;
import com.repository.UserDao;
import com.service.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

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
    private UserDao userDao;

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
                .id(1L)
                .username("jdupont")
                .email("jdupont@test.com")
                .nom("Dupont")
                .prenom("Jean")
                .age(25)
                .adresse("12 rue de Brest")
                .password("encodedPassword")
                .role("USER")
                .build();

        testUserDto = UserDto.builder()
                .id(1L)
                .username("jdupont")
                .email("jdupont@test.com")
                .nom("Dupont")
                .prenom("Jean")
                .age(25)
                .adresse("12 rue de Brest")
                .role("USER")
                .build();

        registerRequest = RegisterRequestDto.builder()
                .username("jdupont")
                .email("jdupont@test.com")
                .nom("Dupont")
                .prenom("Jean")
                .age(25)
                .adresse("12 rue de Brest")
                .password("password123")
                .build();
    }

    @Test
    void register_shouldCreateUser_whenEmailNotTaken() {
        when(userDao.existsByEmail("jdupont@test.com")).thenReturn(false);
        when(userMapper.toEntity(registerRequest)).thenReturn(testUser);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userDao.save(any(User.class))).thenReturn(testUser);
        when(jwtService.generateToken(testUser)).thenReturn("jwt-token");

        AuthResponseDto response = authenticationService.register(registerRequest);

        assertNotNull(response);
        assertEquals("jwt-token", response.getAccessToken());
        verify(userDao).save(any(User.class));
    }

    @Test
    void register_shouldThrowConflict_whenEmailAlreadyExists() {
        when(userDao.existsByEmail("jdupont@test.com")).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> authenticationService.register(registerRequest));
        verify(userDao, never()).save(any());
    }

    @Test
    void login_shouldReturnToken_whenCredentialsValid() {
        LoginRequestDto loginRequest = LoginRequestDto.builder()
                .email("jdupont@test.com")
                .password("password123")
                .build();

        when(userDao.findByEmail("jdupont@test.com")).thenReturn(Optional.of(testUser));
        when(jwtService.generateToken(testUser)).thenReturn("jwt-token");

        AuthResponseDto response = authenticationService.login(loginRequest);

        assertNotNull(response);
        assertEquals("jwt-token", response.getAccessToken());
        verify(authenticationManager).authenticate(any());
    }

    @Test
    void getCurrentUser_shouldReturnUser_whenExists() {
        when(userDao.findByEmail("jdupont@test.com")).thenReturn(Optional.of(testUser));
        when(userMapper.toDto(testUser)).thenReturn(testUserDto);

        UserDto result = authenticationService.getCurrentUser("jdupont@test.com");

        assertEquals("jdupont", result.getUsername());
        assertEquals("Dupont", result.getNom());
    }

    @Test
    void getCurrentUser_shouldThrowNotFound_whenNotExists() {
        when(userDao.findByEmail("inconnu@test.com")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> authenticationService.getCurrentUser("inconnu@test.com"));
    }

    @Test
    void getUserById_shouldReturnUser_whenExists() {
        when(userDao.findById(1L)).thenReturn(Optional.of(testUser));
        when(userMapper.toDto(testUser)).thenReturn(testUserDto);

        UserDto result = authenticationService.getUserById(1L);

        assertEquals("jdupont", result.getUsername());
        assertEquals("Dupont", result.getNom());
    }

    @Test
    void getUserById_shouldThrowNotFound_whenNotExists() {
        when(userDao.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> authenticationService.getUserById(99L));
    }

    @Test
    void updateUser_shouldUpdate_whenUserIsSelf() {
        UpdateUserRequestDto updateRequest = UpdateUserRequestDto.builder()
                .nom("NouveauNom")
                .build();

        when(userDao.findById(1L)).thenReturn(Optional.of(testUser));
        when(userDao.findByEmail("jdupont@test.com")).thenReturn(Optional.of(testUser));
        when(userDao.save(any(User.class))).thenReturn(testUser);
        when(userMapper.toDto(testUser)).thenReturn(testUserDto);

        UserDto result = authenticationService.updateUser(1L, updateRequest, "jdupont@test.com");

        assertNotNull(result);
        verify(userMapper).updateEntity(updateRequest, testUser);
        verify(userDao).save(testUser);
    }

    @Test
    void updateUser_shouldThrowForbidden_whenNotSelfAndNotAdmin() {
        UpdateUserRequestDto updateRequest = UpdateUserRequestDto.builder().nom("Hack").build();

        when(userDao.findById(1L)).thenReturn(Optional.of(testUser));
        // autreUser n'est pas admin
        User autreUser = User.builder().id(2L).username("autre").email("autre@test.com").role("USER").build();
        when(userDao.findByEmail("autre@test.com")).thenReturn(Optional.of(autreUser));

        assertThrows(ResponseStatusException.class,
                () -> authenticationService.updateUser(1L, updateRequest, "autre@test.com"));
    }

    @Test
    void deleteUser_shouldDelete_whenUserIsSelf() {
        when(userDao.findById(1L)).thenReturn(Optional.of(testUser));
        when(userDao.findByEmail("jdupont@test.com")).thenReturn(Optional.of(testUser));

        authenticationService.deleteUser(1L, "jdupont@test.com");

        verify(userDao).delete(testUser);
    }

    @Test
    void deleteUser_shouldThrowNotFound_whenUserNotExists() {
        when(userDao.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> authenticationService.deleteUser(99L, "admin@test.com"));
    }
}
