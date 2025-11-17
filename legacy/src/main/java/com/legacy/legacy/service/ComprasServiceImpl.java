package com.legacy.legacy.service;

import com.legacy.legacy.model.Compras;
import com.legacy.legacy.repository.ComprasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ComprasServiceImpl implements ComprasService {
    
    @Autowired
    private ComprasRepository comprasRepository;
    
    @Override
    public List<Compras> findAll() {
        return comprasRepository.findAll();
    }
    
    @Override
    public Optional<Compras> findById(Integer id) {
        return comprasRepository.findById(id);
    }
    
    @Override
    public Compras save(Compras compras) {
        return comprasRepository.save(compras);
    }
    
    @Override
    public Optional<Compras> update(Integer id, Compras compras) {
        if (!comprasRepository.existsById(id)) {
            return Optional.empty();
        }
        compras.setId(id);
        return Optional.of(comprasRepository.save(compras));
    }
    
    @Override
    public boolean deleteById(Integer id) {
        if (!comprasRepository.existsById(id)) {
            return false;
        }
        comprasRepository.deleteById(id);
        return true;
    }
    
    @Override
    public boolean existsById(Integer id) {
        return comprasRepository.existsById(id);
    }
}

