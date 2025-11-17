package com.legacy.legacy.service;

import com.legacy.legacy.model.Categorias;
import com.legacy.legacy.repository.CategoriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriasService {
    
    @Autowired
    private CategoriasRepository categoriasRepository;
    
    public List<Categorias> findAll() {
        return categoriasRepository.findAll();
    }
    
    public Optional<Categorias> findById(Integer id) {
        return categoriasRepository.findById(id);
    }
    
    public Categorias save(Categorias categorias) {
        return categoriasRepository.save(categorias);
    }
    
    public void deleteById(Integer id) {
        categoriasRepository.deleteById(id);
    }
}

