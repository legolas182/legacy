package com.legacy.legacy.service;

import com.legacy.legacy.model.Ventas;
import com.legacy.legacy.repository.VentasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VentasServiceImpl implements VentasService {
    
    @Autowired
    private VentasRepository ventasRepository;
    
    @Override
    public List<Ventas> findAll() {
        return ventasRepository.findAll();
    }
    
    @Override
    public Optional<Ventas> findById(Integer id) {
        return ventasRepository.findById(id);
    }
    
    @Override
    public Ventas save(Ventas ventas) {
        return ventasRepository.save(ventas);
    }
    
    @Override
    public Optional<Ventas> update(Integer id, Ventas ventas) {
        if (!ventasRepository.existsById(id)) {
            return Optional.empty();
        }
        ventas.setId(id);
        return Optional.of(ventasRepository.save(ventas));
    }
    
    @Override
    public boolean deleteById(Integer id) {
        if (!ventasRepository.existsById(id)) {
            return false;
        }
        ventasRepository.deleteById(id);
        return true;
    }
    
    @Override
    public boolean existsById(Integer id) {
        return ventasRepository.existsById(id);
    }
}

