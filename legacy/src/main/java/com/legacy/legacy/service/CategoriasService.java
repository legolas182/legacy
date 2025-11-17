package com.legacy.legacy.service;

import com.legacy.legacy.model.Categorias;
import java.util.List;
import java.util.Optional;

public interface CategoriasService {
    List<Categorias> findAll();
    Optional<Categorias> findById(Integer id);
    Categorias save(Categorias categorias);
    Optional<Categorias> update(Integer id, Categorias categorias);
    boolean deleteById(Integer id);
    boolean existsById(Integer id);
}
