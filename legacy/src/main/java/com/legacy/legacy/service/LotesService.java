package com.legacy.legacy.service;

import com.legacy.legacy.model.Lotes;
import java.util.List;
import java.util.Optional;

public interface LotesService {
    List<Lotes> findAll();
    Optional<Lotes> findById(Integer id);
    Lotes save(Lotes lotes);
    Optional<Lotes> update(Integer id, Lotes lotes);
    boolean deleteById(Integer id);
    boolean existsById(Integer id);
}
