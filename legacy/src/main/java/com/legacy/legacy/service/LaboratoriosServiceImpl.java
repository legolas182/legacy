package com.legacy.legacy.service;

import com.legacy.legacy.model.Laboratorios;
import com.legacy.legacy.repository.LaboratoriosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LaboratoriosServiceImpl implements LaboratoriosService {
    
    @Autowired
    private LaboratoriosRepository laboratoriosRepository;
    
    @Override
    public List<Laboratorios> findAll() {
        return laboratoriosRepository.findAll();
    }
    
    @Override
    public Optional<Laboratorios> findById(Integer id) {
        return laboratoriosRepository.findById(id);
    }
    
    @Override
    public Laboratorios save(Laboratorios laboratorios) {
        return laboratoriosRepository.save(laboratorios);
    }
    
    @Override
    public Optional<Laboratorios> update(Integer id, Laboratorios laboratorios) {
        if (!laboratoriosRepository.existsById(id)) {
            return Optional.empty();
        }
        laboratorios.setId(id);
        return Optional.of(laboratoriosRepository.save(laboratorios));
    }
    
    @Override
    public boolean deleteById(Integer id) {
        if (!laboratoriosRepository.existsById(id)) {
            return false;
        }
        laboratoriosRepository.deleteById(id);
        return true;
    }
    
    @Override
    public boolean existsById(Integer id) {
        return laboratoriosRepository.existsById(id);
    }
}

