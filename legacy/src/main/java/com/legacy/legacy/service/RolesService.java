package com.legacy.legacy.service;

import com.legacy.legacy.model.Roles;
import java.util.List;
import java.util.Optional;

public interface RolesService {
    List<Roles> findAll();
    Optional<Roles> findById(Integer id);
    Roles save(Roles roles);
    Optional<Roles> update(Integer id, Roles roles);
    boolean deleteById(Integer id);
    boolean existsById(Integer id);
}
