package com.legacy.legacy.service;

import com.legacy.legacy.model.Laboratorios;
import java.util.List;
import java.util.Optional;

public interface LaboratoriosService {
    List<Laboratorios> findAll();
    Optional<Laboratorios> findById(Integer id);
    Laboratorios save(Laboratorios laboratorios);
    Optional<Laboratorios> update(Integer id, Laboratorios laboratorios);
    boolean deleteById(Integer id);
    boolean existsById(Integer id);
}
