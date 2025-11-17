package com.legacy.legacy.service;

import com.legacy.legacy.model.Compras;
import java.util.List;
import java.util.Optional;

public interface ComprasService {
    List<Compras> findAll();
    Optional<Compras> findById(Integer id);
    Compras save(Compras compras);
    Optional<Compras> update(Integer id, Compras compras);
    boolean deleteById(Integer id);
    boolean existsById(Integer id);
}
