package com.legacy.legacy.service;

import com.legacy.legacy.model.Contactos;
import java.util.List;
import java.util.Optional;

public interface ContactosService {
    List<Contactos> findAll();
    Optional<Contactos> findById(Integer id);
    Contactos save(Contactos contactos);
    Optional<Contactos> update(Integer id, Contactos contactos);
    boolean deleteById(Integer id);
    boolean existsById(Integer id);
}
