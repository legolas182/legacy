package com.legacy.legacy.repository;

import com.legacy.legacy.model.Laboratorios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaboratoriosRepository extends JpaRepository<Laboratorios, Integer> {
}

