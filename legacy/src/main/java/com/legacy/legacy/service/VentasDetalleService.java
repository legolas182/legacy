package com.legacy.legacy.service;

import com.legacy.legacy.model.VentasDetalle;
import java.util.List;
import java.util.Optional;

public interface VentasDetalleService {
    List<VentasDetalle> findAll();
    Optional<VentasDetalle> findById(Integer id);
    VentasDetalle save(VentasDetalle ventasDetalle);
    Optional<VentasDetalle> update(Integer id, VentasDetalle ventasDetalle);
    boolean deleteById(Integer id);
    boolean existsById(Integer id);
}
