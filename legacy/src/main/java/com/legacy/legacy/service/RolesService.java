package com.legacy.legacy.service;

import com.legacy.legacy.model.Roles;
import com.legacy.legacy.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RolesService {
    
    @Autowired
    private RolesRepository rolesRepository;
    
    public List<Roles> findAll() {
        return rolesRepository.findAll();
    }
    
    public Optional<Roles> findById(Integer id) {
        return rolesRepository.findById(id);
    }
    
    public Roles save(Roles roles) {
        return rolesRepository.save(roles);
    }
    
    public void deleteById(Integer id) {
        rolesRepository.deleteById(id);
    }
}

