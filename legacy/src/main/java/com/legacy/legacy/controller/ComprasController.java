package com.legacy.legacy.controller;

import com.legacy.legacy.model.Compras;
import com.legacy.legacy.service.ComprasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/compras")
public class ComprasController {
    
    @Autowired
    private ComprasService comprasService;
    
    @GetMapping
    public ResponseEntity<List<Compras>> getAll() {
        return ResponseEntity.ok(comprasService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Compras> getById(@PathVariable Integer id) {
        return comprasService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Compras> create(@RequestBody Compras compras) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(comprasService.save(compras));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Compras> update(@PathVariable Integer id, @RequestBody Compras compras) {
        return comprasService.update(id, compras)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (comprasService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

