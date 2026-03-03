package com.music.music.dao.repository;

import com.music.music.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository pour l'accès aux données des utilisateurs.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Recherche un utilisateur par son pseudo.
     * @param pseudo le pseudo de l'utilisateur
     * @return un Optional contenant l'utilisateur s'il existe
     */
    Optional<User> findByPseudo(String pseudo);

    /**
     * Vérifie si un pseudo existe déjà.
     * @param pseudo le pseudo à vérifier
     * @return true si le pseudo existe
     */
    boolean existsByPseudo(String pseudo);
}
