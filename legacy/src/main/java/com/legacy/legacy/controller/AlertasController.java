package com.legacy.legacy.controller;

import com.legacy.legacy.model.Alertas;
import com.legacy.legacy.service.AlertasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/alertas")
public class AlertasController {
    
    @Autowired
    private AlertasService alertasService;
    
    @GetMapping
    public ResponseEntity<List<Alertas>> getAll() {
        return ResponseEntity.ok(alertasService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Alertas> getById(@PathVariable Integer id) {
        return alertasService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Alertas> create(@RequestBody Alertas alertas) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(alertasService.save(alertas));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Alertas> update(@PathVariable Integer id, @RequestBody Alertas alertas) {
        return alertasService.update(id, alertas)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (alertasService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

