package com.config;

import com.entity.User;
import com.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        String adminEmail = "admin@univ-brest.fr";
        log.info(">>>> [SYSTEM RUNNER] Vérification de l'utilisateur admin '{}'", adminEmail);
        try {
            User admin = userRepository.findByEmail(adminEmail).orElse(null);
            if (admin == null) {
                log.info(">>>> [SYSTEM RUNNER] Admin non trouvé, création...");
                admin = User.builder()
                        .email(adminEmail)
                        .username("admin")
                        .nom("Admin")
                        .prenom("Super")
                        .password(passwordEncoder.encode("password123"))
                        .role("ADMIN")
                        .build();
                userRepository.save(admin);
                log.info(">>>> [SYSTEM RUNNER] Admin créé.");
            } else {
                log.info(">>>> [SYSTEM RUNNER] Admin trouvé (ID={}). Mise à jour forcée du mot de passe...",
                        admin.getId());
                admin.setPassword(passwordEncoder.encode("password123"));
                userRepository.save(admin);
                log.info(">>>> [SYSTEM RUNNER] Mot de passe admin mis à jour.");
            }
        } catch (Exception e) {
            log.error(">>>> [SYSTEM RUNNER] Erreur critique: {}", e.getMessage());
        }
    }
}
