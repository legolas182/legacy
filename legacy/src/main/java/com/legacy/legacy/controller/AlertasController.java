package com.legacy.legacy.controller;

import com.legacy.legacy.model.Alertas;
import com.legacy.legacy.service.AlertasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alertas")
@CrossOrigin(origins = "*")
public class AlertasController {
    
    @Autowired
    private AlertasService alertasService;
    
    @GetMapping
    public ResponseEntity<List<Alertas>> getAll() {
        return ResponseEntity.ok(alertasService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Alertas> getById(@PathVariable Integer id) {
        Optional<Alertas> alertas = alertasService.findById(id);
        return alertas.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Alertas> create(@RequestBody Alertas alertas) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(alertasService.save(alertas));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Alertas> update(@PathVariable Integer id, @RequestBody Alertas alertas) {
        if (!alertasService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        alertas.setId(id);
        return ResponseEntity.ok(alertasService.save(alertas));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!alertasService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        alertasService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

