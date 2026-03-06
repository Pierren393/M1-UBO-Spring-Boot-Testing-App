package com.config;

import com.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtre JWT pour intercepter et valider les tokens dans les requêtes HTTP
 * (Microservice Poster).
 */
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.debug(">>>> [JWT FILTER] Pas de header Authorization ou format incorrect");
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        try {
            final String userPseudo = jwtService.extractUsername(jwt);
            log.info(">>>> [JWT FILTER] Token reçu pour l'utilisateur : {}", userPseudo);

            if (userPseudo != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtService.isTokenValid(jwt)) {
                    log.info(">>>> [JWT FILTER] Token valide pour : {}", userPseudo);
                    UserDetails userDetails = User.withUsername(userPseudo)
                            .password("")
                            .authorities("USER", "ADMIN") // On donne les rôles par défaut pour ces tests
                            .build();

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.info(">>>> [JWT FILTER] Authentification établie pour l'utilisateur : {}", userPseudo);
                } else {
                    log.warn(">>>> [JWT FILTER] Token invalide ou expiré pour : {}", userPseudo);
                }
            }
        } catch (Exception e) {
            log.error(">>>> [JWT FILTER] Erreur lors de la validation du token : {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
