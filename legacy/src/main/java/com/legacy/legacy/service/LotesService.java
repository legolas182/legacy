package com.legacy.legacy.service;

import com.legacy.legacy.model.Lotes;
import com.legacy.legacy.repository.LotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LotesService {
    
    @Autowired
    private LotesRepository lotesRepository;
    
    public List<Lotes> findAll() {
        return lotesRepository.findAll();
    }
    
    public Optional<Lotes> findById(Integer id) {
        return lotesRepository.findById(id);
    }
    
    public Lotes save(Lotes lotes) {
        return lotesRepository.save(lotes);
    }
    
    public void deleteById(Integer id) {
        lotesRepository.deleteById(id);
    }
}

