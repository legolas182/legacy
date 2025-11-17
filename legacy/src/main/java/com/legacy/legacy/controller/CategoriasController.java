package com.legacy.legacy.controller;

import com.legacy.legacy.model.Categorias;
import com.legacy.legacy.service.CategoriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriasController {
    
    @Autowired
    private CategoriasService categoriasService;
    
    @GetMapping
    public ResponseEntity<List<Categorias>> getAll() {
        return ResponseEntity.ok(categoriasService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Categorias> getById(@PathVariable Integer id) {
        return categoriasService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Categorias> create(@RequestBody Categorias categorias) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoriasService.save(categorias));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Categorias> update(@PathVariable Integer id, @RequestBody Categorias categorias) {
        return categoriasService.update(id, categorias)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (categoriasService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

