package com.legacy.legacy.controller;

import com.legacy.legacy.model.Contactos;
import com.legacy.legacy.service.ContactosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contactos")
@CrossOrigin(origins = "*")
public class ContactosController {
    
    @Autowired
    private ContactosService contactosService;
    
    @GetMapping
    public ResponseEntity<List<Contactos>> getAll() {
        return ResponseEntity.ok(contactosService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Contactos> getById(@PathVariable Integer id) {
        Optional<Contactos> contactos = contactosService.findById(id);
        return contactos.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Contactos> create(@RequestBody Contactos contactos) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(contactosService.save(contactos));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Contactos> update(@PathVariable Integer id, @RequestBody Contactos contactos) {
        if (!contactosService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        contactos.setId(id);
        return ResponseEntity.ok(contactosService.save(contactos));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!contactosService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        contactosService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

