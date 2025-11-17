package com.legacy.legacy.controller;

import com.legacy.legacy.model.Productos;
import com.legacy.legacy.service.ProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductosController {
    
    @Autowired
    private ProductosService productosService;
    
    @GetMapping
    public ResponseEntity<List<Productos>> getAll() {
        return ResponseEntity.ok(productosService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Productos> getById(@PathVariable Integer id) {
        return productosService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Productos> create(@RequestBody Productos productos) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productosService.save(productos));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Productos> update(@PathVariable Integer id, @RequestBody Productos productos) {
        return productosService.update(id, productos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (productosService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

