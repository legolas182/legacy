package com.legacy.legacy.service;

import com.legacy.legacy.model.Productos;
import java.util.List;
import java.util.Optional;

public interface ProductosService {
    List<Productos> findAll();
    Optional<Productos> findById(Integer id);
    Productos save(Productos productos);
    Optional<Productos> update(Integer id, Productos productos);
    boolean deleteById(Integer id);
    boolean existsById(Integer id);
}
