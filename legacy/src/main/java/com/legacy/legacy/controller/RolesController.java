package com.legacy.legacy.controller;

import com.legacy.legacy.model.Roles;
import com.legacy.legacy.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RolesController {
    
    @Autowired
    private RolesService rolesService;
    
    @GetMapping
    public ResponseEntity<List<Roles>> getAll() {
        return ResponseEntity.ok(rolesService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Roles> getById(@PathVariable Integer id) {
        Optional<Roles> roles = rolesService.findById(id);
        return roles.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Roles> create(@RequestBody Roles roles) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(rolesService.save(roles));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Roles> update(@PathVariable Integer id, @RequestBody Roles roles) {
        if (!rolesService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        roles.setId(id);
        return ResponseEntity.ok(rolesService.save(roles));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!rolesService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        rolesService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

