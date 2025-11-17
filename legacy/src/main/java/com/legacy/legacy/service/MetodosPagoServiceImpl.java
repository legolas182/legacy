package com.legacy.legacy.service;

import com.legacy.legacy.model.MetodosPago;
import com.legacy.legacy.repository.MetodosPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MetodosPagoServiceImpl implements MetodosPagoService {
    
    @Autowired
    private MetodosPagoRepository metodosPagoRepository;
    
    @Override
    public List<MetodosPago> findAll() {
        return metodosPagoRepository.findAll();
    }
    
    @Override
    public Optional<MetodosPago> findById(Integer id) {
        return metodosPagoRepository.findById(id);
    }
    
    @Override
    public MetodosPago save(MetodosPago metodosPago) {
        return metodosPagoRepository.save(metodosPago);
    }
    
    @Override
    public Optional<MetodosPago> update(Integer id, MetodosPago metodosPago) {
        if (!metodosPagoRepository.existsById(id)) {
            return Optional.empty();
        }
        metodosPago.setId(id);
        return Optional.of(metodosPagoRepository.save(metodosPago));
    }
    
    @Override
    public boolean deleteById(Integer id) {
        if (!metodosPagoRepository.existsById(id)) {
            return false;
        }
        metodosPagoRepository.deleteById(id);
        return true;
    }
    
    @Override
    public boolean existsById(Integer id) {
        return metodosPagoRepository.existsById(id);
    }
}

