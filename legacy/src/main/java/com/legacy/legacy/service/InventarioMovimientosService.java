package com.legacy.legacy.service;

import com.legacy.legacy.model.InventarioMovimientos;
import com.legacy.legacy.repository.InventarioMovimientosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InventarioMovimientosService {
    
    @Autowired
    private InventarioMovimientosRepository inventarioMovimientosRepository;
    
    public List<InventarioMovimientos> findAll() {
        return inventarioMovimientosRepository.findAll();
    }
    
    public Optional<InventarioMovimientos> findById(Integer id) {
        return inventarioMovimientosRepository.findById(id);
    }
    
    public InventarioMovimientos save(InventarioMovimientos inventarioMovimientos) {
        return inventarioMovimientosRepository.save(inventarioMovimientos);
    }
    
    public void deleteById(Integer id) {
        inventarioMovimientosRepository.deleteById(id);
    }
}

