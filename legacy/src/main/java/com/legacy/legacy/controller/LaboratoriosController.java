package com.legacy.legacy.controller;

import com.legacy.legacy.model.Laboratorios;
import com.legacy.legacy.service.LaboratoriosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/laboratorios")
@CrossOrigin(origins = "*")
public class LaboratoriosController {
    
    @Autowired
    private LaboratoriosService laboratoriosService;
    
    @GetMapping
    public ResponseEntity<List<Laboratorios>> getAll() {
        return ResponseEntity.ok(laboratoriosService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Laboratorios> getById(@PathVariable Integer id) {
        Optional<Laboratorios> laboratorios = laboratoriosService.findById(id);
        return laboratorios.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Laboratorios> create(@RequestBody Laboratorios laboratorios) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(laboratoriosService.save(laboratorios));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Laboratorios> update(@PathVariable Integer id, @RequestBody Laboratorios laboratorios) {
        if (!laboratoriosService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        laboratorios.setId(id);
        return ResponseEntity.ok(laboratoriosService.save(laboratorios));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!laboratoriosService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        laboratoriosService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

