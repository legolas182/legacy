package com.legacy.legacy.service;

import com.legacy.legacy.model.Alertas;
import com.legacy.legacy.repository.AlertasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AlertasService {
    
    @Autowired
    private AlertasRepository alertasRepository;
    
    public List<Alertas> findAll() {
        return alertasRepository.findAll();
    }
    
    public Optional<Alertas> findById(Integer id) {
        return alertasRepository.findById(id);
    }
    
    public Alertas save(Alertas alertas) {
        return alertasRepository.save(alertas);
    }
    
    public void deleteById(Integer id) {
        alertasRepository.deleteById(id);
    }
}

