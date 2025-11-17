package com.legacy.legacy.controller;

import com.legacy.legacy.model.Contactos;
import com.legacy.legacy.model.Roles;
import com.legacy.legacy.repository.RolesRepository;
import com.legacy.legacy.service.ContactosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuariosController {
    
    @Autowired
    private ContactosService contactosService;
    
    @Autowired
    private RolesRepository rolesRepository;
    
    /**
     * Crear nuevo usuario - SOLO ADMIN
     * POST /api/usuarios
     * Body: { "nombre": "...", "username": "...", "password": "...", "email": "...", "rolId": 1, "sucursalId": 1 }
     */
    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody Map<String, Object> request) {
        try {
            // TODO: Verificar que el usuario actual sea ADMIN
            
            Contactos nuevoUsuario = new Contactos();
            nuevoUsuario.setNombre((String) request.get("nombre"));
            nuevoUsuario.setUsername((String) request.get("username"));
            nuevoUsuario.setPassword((String) request.get("password")); // En producción usar BCrypt
            nuevoUsuario.setEmail((String) request.get("email"));
            nuevoUsuario.setTelefono((String) request.get("telefono"));
            
            // Asignar rol si se proporciona
            if (request.get("rolId") != null) {
                Integer rolId = ((Number) request.get("rolId")).intValue();
                rolesRepository.findById(rolId).ifPresent(nuevoUsuario::setRol);
            }
            
            Integer sucursalId = null;
            if (request.get("sucursalId") != null) {
                sucursalId = ((Number) request.get("sucursalId")).intValue();
            }
            
            if (sucursalId == null) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Debe especificar una sucursal"));
            }
            
            Contactos usuarioCreado = contactosService.crearUsuario(nuevoUsuario, sucursalId);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    /**
     * Listar todos los usuarios - SOLO ADMIN
     */
    @GetMapping
    public ResponseEntity<List<Contactos>> listarUsuarios() {
        // TODO: Verificar que el usuario actual sea ADMIN
        List<Contactos> usuarios = contactosService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }
    
    /**
     * Listar usuarios por sucursal - SOLO ADMIN
     */
    @GetMapping("/sucursal/{sucursalId}")
    public ResponseEntity<List<Contactos>> listarUsuariosPorSucursal(
            @PathVariable Integer sucursalId) {
        // TODO: Verificar que el usuario actual sea ADMIN
        List<Contactos> usuarios = contactosService.listarUsuariosPorSucursal(sucursalId);
        return ResponseEntity.ok(usuarios);
    }
    
    /**
     * Obtener usuario por ID - SOLO ADMIN
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable Integer id) {
        // TODO: Verificar que el usuario actual sea ADMIN
        Optional<Contactos> usuario = contactosService.obtenerUsuario(id);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Actualizar usuario - SOLO ADMIN
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> request) {
        try {
            // TODO: Verificar que el usuario actual sea ADMIN
            
            Contactos usuarioActualizado = new Contactos();
            if (request.get("nombre") != null) {
                usuarioActualizado.setNombre((String) request.get("nombre"));
            }
            if (request.get("email") != null) {
                usuarioActualizado.setEmail((String) request.get("email"));
            }
            if (request.get("telefono") != null) {
                usuarioActualizado.setTelefono((String) request.get("telefono"));
            }
            if (request.get("activo") != null) {
                usuarioActualizado.setActivo((Boolean) request.get("activo"));
            }
            
            // Actualizar rol si se proporciona
            if (request.get("rolId") != null) {
                Integer rolId = ((Number) request.get("rolId")).intValue();
                Optional<Roles> rol = rolesRepository.findById(rolId);
                rol.ifPresent(usuarioActualizado::setRol);
            }
            
            Integer sucursalId = null;
            if (request.get("sucursalId") != null) {
                sucursalId = ((Number) request.get("sucursalId")).intValue();
            }
            
            Optional<Contactos> resultado = contactosService.actualizarUsuario(
                id, usuarioActualizado, sucursalId);
            
            if (resultado.isPresent()) {
                return ResponseEntity.ok(resultado.get());
            } else {
                return ResponseEntity.notFound().build();
            }
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}

