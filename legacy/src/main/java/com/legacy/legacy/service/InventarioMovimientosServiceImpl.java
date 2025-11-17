package com.legacy.legacy.service;

import com.legacy.legacy.model.InventarioMovimientos;
import com.legacy.legacy.repository.InventarioMovimientosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InventarioMovimientosServiceImpl implements InventarioMovimientosService {
    
    @Autowired
    private InventarioMovimientosRepository inventarioMovimientosRepository;
    
    @Override
    public List<InventarioMovimientos> findAll() {
        return inventarioMovimientosRepository.findAll();
    }
    
    @Override
    public Optional<InventarioMovimientos> findById(Integer id) {
        return inventarioMovimientosRepository.findById(id);
    }
    
    @Override
    public InventarioMovimientos save(InventarioMovimientos inventarioMovimientos) {
        return inventarioMovimientosRepository.save(inventarioMovimientos);
    }
    
    @Override
    public Optional<InventarioMovimientos> update(Integer id, InventarioMovimientos inventarioMovimientos) {
        if (!inventarioMovimientosRepository.existsById(id)) {
            return Optional.empty();
        }
        inventarioMovimientos.setId(id);
        return Optional.of(inventarioMovimientosRepository.save(inventarioMovimientos));
    }
    
    @Override
    public boolean deleteById(Integer id) {
        if (!inventarioMovimientosRepository.existsById(id)) {
            return false;
        }
        inventarioMovimientosRepository.deleteById(id);
        return true;
    }
    
    @Override
    public boolean existsById(Integer id) {
        return inventarioMovimientosRepository.existsById(id);
    }
}

