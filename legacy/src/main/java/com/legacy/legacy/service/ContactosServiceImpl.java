package com.legacy.legacy.service;

import com.legacy.legacy.model.Contactos;
import com.legacy.legacy.model.Sucursal;
import com.legacy.legacy.repository.ContactosRepository;
import com.legacy.legacy.repository.SucursalRepository;
import com.legacy.legacy.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ContactosServiceImpl implements ContactosService {
    
    @Autowired
    private ContactosRepository contactosRepository;
    
    @Autowired
    private SucursalRepository sucursalRepository;
    
    @Autowired
    private RolesRepository rolesRepository;
    
    @Override
    public List<Contactos> findAll() {
        return contactosRepository.findAll();
    }
    
    @Override
    public Optional<Contactos> findById(Integer id) {
        return contactosRepository.findById(id);
    }
    
    @Override
    public Contactos save(Contactos contactos) {
        return contactosRepository.save(contactos);
    }
    
    @Override
    public Optional<Contactos> update(Integer id, Contactos contactos) {
        if (!contactosRepository.existsById(id)) {
            return Optional.empty();
        }
        contactos.setId(id);
        return Optional.of(contactosRepository.save(contactos));
    }
    
    @Override
    public boolean deleteById(Integer id) {
        if (!contactosRepository.existsById(id)) {
            return false;
        }
        contactosRepository.deleteById(id);
        return true;
    }
    
    @Override
    public boolean existsById(Integer id) {
        return contactosRepository.existsById(id);
    }
    
    /**
     * Crear nuevo usuario - SOLO ADMIN puede usar este método
     * @param contacto El contacto/usuario a crear
     * @param sucursalId ID de la sucursal a asignar
     * @return El contacto creado
     */
    @Override
    @Transactional
    public Contactos crearUsuario(Contactos contacto, Integer sucursalId) {
        // Verificar que el username no exista
        if (contactosRepository.existsByUsername(contacto.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }
        
        // Verificar que el email no exista (si se proporciona)
        if (contacto.getEmail() != null && !contacto.getEmail().isEmpty()) {
            if (contactosRepository.existsByEmail(contacto.getEmail())) {
                throw new RuntimeException("El correo electrónico ya está en uso");
            }
        }
        
        // Asignar sucursal
        Sucursal sucursal = sucursalRepository.findById(sucursalId)
            .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
        contacto.setSucursal(sucursal);
        
        // Establecer fecha de creación
        contacto.setFechaCreacion(LocalDateTime.now());
        if (contacto.getActivo() == null) {
            contacto.setActivo(true);
        }
        
        return contactosRepository.save(contacto);
    }
    
    /**
     * Actualizar usuario - SOLO ADMIN
     */
    @Override
    @Transactional
    public Optional<Contactos> actualizarUsuario(Integer id, Contactos contactoActualizado, Integer sucursalId) {
        Optional<Contactos> contactoOpt = contactosRepository.findById(id);
        
        if (contactoOpt.isEmpty()) {
            return Optional.empty();
        }
        
        Contactos contacto = contactoOpt.get();
        
        // Actualizar campos permitidos
        if (contactoActualizado.getNombre() != null) {
            contacto.setNombre(contactoActualizado.getNombre());
        }
        if (contactoActualizado.getEmail() != null) {
            contacto.setEmail(contactoActualizado.getEmail());
        }
        if (contactoActualizado.getTelefono() != null) {
            contacto.setTelefono(contactoActualizado.getTelefono());
        }
        if (contactoActualizado.getActivo() != null) {
            contacto.setActivo(contactoActualizado.getActivo());
        }
        
        // Actualizar rol si se proporciona
        if (contactoActualizado.getRol() != null) {
            contacto.setRol(contactoActualizado.getRol());
        }
        
        // Actualizar sucursal si se proporciona
        if (sucursalId != null) {
            Sucursal sucursal = sucursalRepository.findById(sucursalId)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
            contacto.setSucursal(sucursal);
        }
        
        return Optional.of(contactosRepository.save(contacto));
    }
    
    /**
     * Listar todos los usuarios (para admin)
     */
    @Override
    public List<Contactos> listarUsuarios() {
        return contactosRepository.findAll();
    }
    
    /**
     * Listar usuarios por sucursal
     */
    @Override
    public List<Contactos> listarUsuariosPorSucursal(Integer sucursalId) {
        return contactosRepository.findBySucursalId(sucursalId);
    }
    
    /**
     * Obtener usuario por ID
     */
    @Override
    public Optional<Contactos> obtenerUsuario(Integer id) {
        return contactosRepository.findById(id);
    }
}

