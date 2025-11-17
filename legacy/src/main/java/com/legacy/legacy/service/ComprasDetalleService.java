package com.legacy.legacy.service;

import com.legacy.legacy.model.ComprasDetalle;
import java.util.List;
import java.util.Optional;

public interface ComprasDetalleService {
    List<ComprasDetalle> findAll();
    Optional<ComprasDetalle> findById(Integer id);
    ComprasDetalle save(ComprasDetalle comprasDetalle);
    Optional<ComprasDetalle> update(Integer id, ComprasDetalle comprasDetalle);
    boolean deleteById(Integer id);
    boolean existsById(Integer id);
}
