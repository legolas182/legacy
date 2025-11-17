package com.legacy.legacy.controller;

import com.legacy.legacy.model.Laboratorios;
import com.legacy.legacy.service.LaboratoriosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/laboratorios")
public class LaboratoriosController {
    
    @Autowired
    private LaboratoriosService laboratoriosService;
    
    @GetMapping
    public ResponseEntity<List<Laboratorios>> getAll() {
        return ResponseEntity.ok(laboratoriosService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Laboratorios> getById(@PathVariable Integer id) {
        return laboratoriosService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Laboratorios> create(@RequestBody Laboratorios laboratorios) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(laboratoriosService.save(laboratorios));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Laboratorios> update(@PathVariable Integer id, @RequestBody Laboratorios laboratorios) {
        return laboratoriosService.update(id, laboratorios)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (laboratoriosService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

