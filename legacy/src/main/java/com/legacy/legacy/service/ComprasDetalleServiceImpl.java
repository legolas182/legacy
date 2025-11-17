package com.legacy.legacy.service;

import com.legacy.legacy.model.ComprasDetalle;
import com.legacy.legacy.repository.ComprasDetalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ComprasDetalleServiceImpl implements ComprasDetalleService {
    
    @Autowired
    private ComprasDetalleRepository comprasDetalleRepository;
    
    @Override
    public List<ComprasDetalle> findAll() {
        return comprasDetalleRepository.findAll();
    }
    
    @Override
    public Optional<ComprasDetalle> findById(Integer id) {
        return comprasDetalleRepository.findById(id);
    }
    
    @Override
    public ComprasDetalle save(ComprasDetalle comprasDetalle) {
        return comprasDetalleRepository.save(comprasDetalle);
    }
    
    @Override
    public Optional<ComprasDetalle> update(Integer id, ComprasDetalle comprasDetalle) {
        if (!comprasDetalleRepository.existsById(id)) {
            return Optional.empty();
        }
        comprasDetalle.setId(id);
        return Optional.of(comprasDetalleRepository.save(comprasDetalle));
    }
    
    @Override
    public boolean deleteById(Integer id) {
        if (!comprasDetalleRepository.existsById(id)) {
            return false;
        }
        comprasDetalleRepository.deleteById(id);
        return true;
    }
    
    @Override
    public boolean existsById(Integer id) {
        return comprasDetalleRepository.existsById(id);
    }
}

