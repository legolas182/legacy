package com.legacy.legacy.controller;

import com.legacy.legacy.model.Contactos;
import com.legacy.legacy.service.ContactosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/contactos")
public class ContactosController {
    
    @Autowired
    private ContactosService contactosService;
    
    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<Contactos> contactos = contactosService.findAll();
            return ResponseEntity.ok(contactos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener contactos: " + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Contactos> getById(@PathVariable Integer id) {
        return contactosService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Contactos> create(@RequestBody Contactos contactos) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(contactosService.save(contactos));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Contactos> update(@PathVariable Integer id, @RequestBody Contactos contactos) {
        return contactosService.update(id, contactos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (contactosService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

