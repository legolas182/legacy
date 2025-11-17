package com.legacy.legacy.controller;

import com.legacy.legacy.model.Ventas;
import com.legacy.legacy.service.VentasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
public class VentasController {
    
    @Autowired
    private VentasService ventasService;
    
    @GetMapping
    public ResponseEntity<List<Ventas>> getAll() {
        return ResponseEntity.ok(ventasService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Ventas> getById(@PathVariable Integer id) {
        return ventasService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Ventas> create(@RequestBody Ventas ventas) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ventasService.save(ventas));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Ventas> update(@PathVariable Integer id, @RequestBody Ventas ventas) {
        return ventasService.update(id, ventas)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (ventasService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

