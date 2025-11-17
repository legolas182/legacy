package com.legacy.legacy.service;

import com.legacy.legacy.model.Alertas;
import java.util.List;
import java.util.Optional;

public interface AlertasService {
    List<Alertas> findAll();
    Optional<Alertas> findById(Integer id);
    Alertas save(Alertas alertas);
    Optional<Alertas> update(Integer id, Alertas alertas);
    boolean deleteById(Integer id);
    boolean existsById(Integer id);
}
