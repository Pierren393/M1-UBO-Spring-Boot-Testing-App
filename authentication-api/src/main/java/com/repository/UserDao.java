package com.repository;

import com.entity.User;
import java.util.List;
import java.util.Optional;

/**
 * Interface DAO pour l'accès aux données des utilisateurs (MariaDB).
 */
public interface UserDao {
    User save(User user);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findById(Long id);
    List<User> findAll();
    void delete(User user);
}
