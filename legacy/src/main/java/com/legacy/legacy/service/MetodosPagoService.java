package com.legacy.legacy.service;

import com.legacy.legacy.model.MetodosPago;
import java.util.List;
import java.util.Optional;

public interface MetodosPagoService {
    List<MetodosPago> findAll();
    Optional<MetodosPago> findById(Integer id);
    MetodosPago save(MetodosPago metodosPago);
    Optional<MetodosPago> update(Integer id, MetodosPago metodosPago);
    boolean deleteById(Integer id);
    boolean existsById(Integer id);
}
