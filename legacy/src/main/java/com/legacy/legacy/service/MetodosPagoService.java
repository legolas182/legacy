package com.legacy.legacy.service;

import com.legacy.legacy.model.MetodosPago;
import com.legacy.legacy.repository.MetodosPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MetodosPagoService {
    
    @Autowired
    private MetodosPagoRepository metodosPagoRepository;
    
    public List<MetodosPago> findAll() {
        return metodosPagoRepository.findAll();
    }
    
    public Optional<MetodosPago> findById(Integer id) {
        return metodosPagoRepository.findById(id);
    }
    
    public MetodosPago save(MetodosPago metodosPago) {
        return metodosPagoRepository.save(metodosPago);
    }
    
    public void deleteById(Integer id) {
        metodosPagoRepository.deleteById(id);
    }
}

