package com.legacy.legacy.service;

import com.legacy.legacy.model.Alertas;
import com.legacy.legacy.repository.AlertasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AlertasServiceImpl implements AlertasService {
    
    @Autowired
    private AlertasRepository alertasRepository;
    
    @Override
    public List<Alertas> findAll() {
        return alertasRepository.findAll();
    }
    
    @Override
    public Optional<Alertas> findById(Integer id) {
        return alertasRepository.findById(id);
    }
    
    @Override
    public Alertas save(Alertas alertas) {
        return alertasRepository.save(alertas);
    }
    
    @Override
    public Optional<Alertas> update(Integer id, Alertas alertas) {
        if (!alertasRepository.existsById(id)) {
            return Optional.empty();
        }
        alertas.setId(id);
        return Optional.of(alertasRepository.save(alertas));
    }
    
    @Override
    public boolean deleteById(Integer id) {
        if (!alertasRepository.existsById(id)) {
            return false;
        }
        alertasRepository.deleteById(id);
        return true;
    }
    
    @Override
    public boolean existsById(Integer id) {
        return alertasRepository.existsById(id);
    }
}

