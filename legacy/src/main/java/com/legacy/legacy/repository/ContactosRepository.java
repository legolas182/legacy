package com.legacy.legacy.repository;

import com.legacy.legacy.model.Contactos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactosRepository extends JpaRepository<Contactos, Integer> {
}

