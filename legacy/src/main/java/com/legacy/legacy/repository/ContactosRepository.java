package com.legacy.legacy.repository;

import com.legacy.legacy.model.Contactos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactosRepository extends JpaRepository<Contactos, Integer> {
    Optional<Contactos> findByUsername(String username);
    Optional<Contactos> findByEmail(String email);
    Optional<Contactos> findByUsernameOrEmail(String username, String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}

