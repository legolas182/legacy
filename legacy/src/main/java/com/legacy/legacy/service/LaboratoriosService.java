package com.legacy.legacy.service;

import com.legacy.legacy.model.Laboratorios;
import com.legacy.legacy.repository.LaboratoriosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LaboratoriosService {
    
    @Autowired
    private LaboratoriosRepository laboratoriosRepository;
    
    public List<Laboratorios> findAll() {
        return laboratoriosRepository.findAll();
    }
    
    public Optional<Laboratorios> findById(Integer id) {
        return laboratoriosRepository.findById(id);
    }
    
    public Laboratorios save(Laboratorios laboratorios) {
        return laboratoriosRepository.save(laboratorios);
    }
    
    public void deleteById(Integer id) {
        laboratoriosRepository.deleteById(id);
    }
}

