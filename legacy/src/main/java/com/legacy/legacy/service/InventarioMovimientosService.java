package com.legacy.legacy.service;

import com.legacy.legacy.model.InventarioMovimientos;
import java.util.List;
import java.util.Optional;

public interface InventarioMovimientosService {
    List<InventarioMovimientos> findAll();
    Optional<InventarioMovimientos> findById(Integer id);
    InventarioMovimientos save(InventarioMovimientos inventarioMovimientos);
    Optional<InventarioMovimientos> update(Integer id, InventarioMovimientos inventarioMovimientos);
    boolean deleteById(Integer id);
    boolean existsById(Integer id);
}
