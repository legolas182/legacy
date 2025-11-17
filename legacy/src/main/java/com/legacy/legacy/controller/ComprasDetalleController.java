package com.legacy.legacy.controller;

import com.legacy.legacy.model.ComprasDetalle;
import com.legacy.legacy.service.ComprasDetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/compras-detalle")
public class ComprasDetalleController {
    
    @Autowired
    private ComprasDetalleService comprasDetalleService;
    
    @GetMapping
    public ResponseEntity<List<ComprasDetalle>> getAll() {
        return ResponseEntity.ok(comprasDetalleService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ComprasDetalle> getById(@PathVariable Integer id) {
        return comprasDetalleService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<ComprasDetalle> create(@RequestBody ComprasDetalle comprasDetalle) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(comprasDetalleService.save(comprasDetalle));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ComprasDetalle> update(@PathVariable Integer id, @RequestBody ComprasDetalle comprasDetalle) {
        return comprasDetalleService.update(id, comprasDetalle)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (comprasDetalleService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

