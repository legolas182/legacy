package com.legacy.legacy.service;

import com.legacy.legacy.model.Compras;
import com.legacy.legacy.repository.ComprasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ComprasService {
    
    @Autowired
    private ComprasRepository comprasRepository;
    
    public List<Compras> findAll() {
        return comprasRepository.findAll();
    }
    
    public Optional<Compras> findById(Integer id) {
        return comprasRepository.findById(id);
    }
    
    public Compras save(Compras compras) {
        return comprasRepository.save(compras);
    }
    
    public void deleteById(Integer id) {
        comprasRepository.deleteById(id);
    }
}

