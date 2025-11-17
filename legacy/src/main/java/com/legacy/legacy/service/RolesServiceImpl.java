package com.legacy.legacy.service;

import com.legacy.legacy.model.Roles;
import com.legacy.legacy.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RolesServiceImpl implements RolesService {
    
    @Autowired
    private RolesRepository rolesRepository;
    
    @Override
    public List<Roles> findAll() {
        return rolesRepository.findAll();
    }
    
    @Override
    public Optional<Roles> findById(Integer id) {
        return rolesRepository.findById(id);
    }
    
    @Override
    public Roles save(Roles roles) {
        return rolesRepository.save(roles);
    }
    
    @Override
    public Optional<Roles> update(Integer id, Roles roles) {
        if (!rolesRepository.existsById(id)) {
            return Optional.empty();
        }
        roles.setId(id);
        return Optional.of(rolesRepository.save(roles));
    }
    
    @Override
    public boolean deleteById(Integer id) {
        if (!rolesRepository.existsById(id)) {
            return false;
        }
        rolesRepository.deleteById(id);
        return true;
    }
    
    @Override
    public boolean existsById(Integer id) {
        return rolesRepository.existsById(id);
    }
}

