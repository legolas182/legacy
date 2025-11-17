package com.legacy.legacy.service;

import com.legacy.legacy.model.Contactos;
import com.legacy.legacy.repository.ContactosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ContactosService {
    
    @Autowired
    private ContactosRepository contactosRepository;
    
    public List<Contactos> findAll() {
        return contactosRepository.findAll();
    }
    
    public Optional<Contactos> findById(Integer id) {
        return contactosRepository.findById(id);
    }
    
    public Contactos save(Contactos contactos) {
        return contactosRepository.save(contactos);
    }
    
    public void deleteById(Integer id) {
        contactosRepository.deleteById(id);
    }
}

