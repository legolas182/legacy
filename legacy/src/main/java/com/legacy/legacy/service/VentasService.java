package com.legacy.legacy.service;

import com.legacy.legacy.model.Ventas;
import java.util.List;
import java.util.Optional;

public interface VentasService {
    List<Ventas> findAll();
    Optional<Ventas> findById(Integer id);
    Ventas save(Ventas ventas);
    Optional<Ventas> update(Integer id, Ventas ventas);
    boolean deleteById(Integer id);
    boolean existsById(Integer id);
}
