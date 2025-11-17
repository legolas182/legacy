package com.legacy.legacy.service;

import com.legacy.legacy.model.ComprasDetalle;
import com.legacy.legacy.repository.ComprasDetalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ComprasDetalleService {
    
    @Autowired
    private ComprasDetalleRepository comprasDetalleRepository;
    
    public List<ComprasDetalle> findAll() {
        return comprasDetalleRepository.findAll();
    }
    
    public Optional<ComprasDetalle> findById(Integer id) {
        return comprasDetalleRepository.findById(id);
    }
    
    public ComprasDetalle save(ComprasDetalle comprasDetalle) {
        return comprasDetalleRepository.save(comprasDetalle);
    }
    
    public void deleteById(Integer id) {
        comprasDetalleRepository.deleteById(id);
    }
}

