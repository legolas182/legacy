package com.legacy.legacy.controller;

import com.legacy.legacy.model.ComprasDetalle;
import com.legacy.legacy.service.ComprasDetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/compras-detalle")
@CrossOrigin(origins = "*")
public class ComprasDetalleController {
    
    @Autowired
    private ComprasDetalleService comprasDetalleService;
    
    @GetMapping
    public ResponseEntity<List<ComprasDetalle>> getAll() {
        return ResponseEntity.ok(comprasDetalleService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ComprasDetalle> getById(@PathVariable Integer id) {
        Optional<ComprasDetalle> comprasDetalle = comprasDetalleService.findById(id);
        return comprasDetalle.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<ComprasDetalle> create(@RequestBody ComprasDetalle comprasDetalle) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(comprasDetalleService.save(comprasDetalle));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ComprasDetalle> update(@PathVariable Integer id, @RequestBody ComprasDetalle comprasDetalle) {
        if (!comprasDetalleService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        comprasDetalle.setId(id);
        return ResponseEntity.ok(comprasDetalleService.save(comprasDetalle));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!comprasDetalleService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        comprasDetalleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

