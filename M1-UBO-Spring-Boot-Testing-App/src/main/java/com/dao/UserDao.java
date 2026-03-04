package com.dao;

import com.entities.User;
import java.util.List;
import java.util.Optional;

/**
 * Interface DAO pour l'accès aux données des utilisateurs (MariaDB).
 */
public interface UserDao {
    User save(User user);
    Optional<User> findByPseudo(String pseudo);
    boolean existsByPseudo(String pseudo);
    List<User> findAll();
    void delete(User user);
}
