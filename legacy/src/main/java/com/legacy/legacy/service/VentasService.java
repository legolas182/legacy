package com.legacy.legacy.service;

import com.legacy.legacy.model.Ventas;
import com.legacy.legacy.repository.VentasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VentasService {
    
    @Autowired
    private VentasRepository ventasRepository;
    
    public List<Ventas> findAll() {
        return ventasRepository.findAll();
    }
    
    public Optional<Ventas> findById(Integer id) {
        return ventasRepository.findById(id);
    }
    
    public Ventas save(Ventas ventas) {
        return ventasRepository.save(ventas);
    }
    
    public void deleteById(Integer id) {
        ventasRepository.deleteById(id);
    }
}

