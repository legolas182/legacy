package com.legacy.legacy.controller;

import com.legacy.legacy.model.Lotes;
import com.legacy.legacy.service.LotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lotes")
@CrossOrigin(origins = "*")
public class LotesController {
    
    @Autowired
    private LotesService lotesService;
    
    @GetMapping
    public ResponseEntity<List<Lotes>> getAll() {
        return ResponseEntity.ok(lotesService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Lotes> getById(@PathVariable Integer id) {
        Optional<Lotes> lotes = lotesService.findById(id);
        return lotes.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Lotes> create(@RequestBody Lotes lotes) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(lotesService.save(lotes));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Lotes> update(@PathVariable Integer id, @RequestBody Lotes lotes) {
        if (!lotesService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        lotes.setId(id);
        return ResponseEntity.ok(lotesService.save(lotes));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!lotesService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        lotesService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

