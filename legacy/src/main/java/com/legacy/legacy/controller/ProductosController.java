package com.legacy.legacy.controller;

import com.legacy.legacy.model.Productos;
import com.legacy.legacy.service.ProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductosController {
    
    @Autowired
    private ProductosService productosService;
    
    @GetMapping
    public ResponseEntity<List<Productos>> getAll() {
        return ResponseEntity.ok(productosService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Productos> getById(@PathVariable Integer id) {
        Optional<Productos> productos = productosService.findById(id);
        return productos.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Productos> create(@RequestBody Productos productos) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productosService.save(productos));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Productos> update(@PathVariable Integer id, @RequestBody Productos productos) {
        if (!productosService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        productos.setId(id);
        return ResponseEntity.ok(productosService.save(productos));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!productosService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        productosService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

