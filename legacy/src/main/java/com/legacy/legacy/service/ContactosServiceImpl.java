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
    @Transactional(readOnly = true)
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
    @Transactional
    public Optional<Contactos> update(Integer id, Contactos contactos) {
        Optional<Contactos> contactoOpt = contactosRepository.findById(id);
        
        if (contactoOpt.isEmpty()) {
            return Optional.empty();
        }
        
        Contactos contactoExistente = contactoOpt.get();
        
        // IMPORTANTE: NO actualizar tipoContacto - es un campo inmutable después de la creación
        // El tipoContacto se mantiene con el valor original del contacto existente
        
        // Actualizar solo los campos que se proporcionan
        if (contactos.getNombre() != null && !contactos.getNombre().trim().isEmpty()) {
            contactoExistente.setNombre(contactos.getNombre().trim());
        }
        if (contactos.getEmail() != null) {
            contactoExistente.setEmail(contactos.getEmail());
        } else if (contactos.getEmail() == null && contactoExistente.getEmail() != null) {
            // Permitir establecer email a null si se envía explícitamente
            contactoExistente.setEmail(null);
        }
        if (contactos.getTelefono() != null) {
            contactoExistente.setTelefono(contactos.getTelefono());
        } else if (contactos.getTelefono() == null && contactoExistente.getTelefono() != null) {
            contactoExistente.setTelefono(null);
        }
        if (contactos.getDireccion() != null) {
            contactoExistente.setDireccion(contactos.getDireccion());
        } else if (contactos.getDireccion() == null && contactoExistente.getDireccion() != null) {
            contactoExistente.setDireccion(null);
        }
        if (contactos.getEmpresa() != null) {
            contactoExistente.setEmpresa(contactos.getEmpresa());
        } else if (contactos.getEmpresa() == null && contactoExistente.getEmpresa() != null) {
            contactoExistente.setEmpresa(null);
        }
        if (contactos.getNit() != null) {
            contactoExistente.setNit(contactos.getNit());
        } else if (contactos.getNit() == null && contactoExistente.getNit() != null) {
            contactoExistente.setNit(null);
        }
        if (contactos.getActivo() != null) {
            contactoExistente.setActivo(contactos.getActivo());
        }
        
        // Actualizar rol si se proporciona
        if (contactos.getRol() != null && contactos.getRol().getId() != null) {
            rolesRepository.findById(contactos.getRol().getId())
                .ifPresent(contactoExistente::setRol);
        } else if (contactos.getRol() == null) {
            contactoExistente.setRol(null);
        }
        
        // Actualizar sucursal si se proporciona
        if (contactos.getSucursal() != null && contactos.getSucursal().getId() != null) {
            sucursalRepository.findById(contactos.getSucursal().getId())
                .ifPresent(contactoExistente::setSucursal);
        } else if (contactos.getSucursal() == null) {
            contactoExistente.setSucursal(null);
        }
        
        // Actualizar username si se proporciona
        if (contactos.getUsername() != null && !contactos.getUsername().isEmpty()) {
            contactoExistente.setUsername(contactos.getUsername());
        } else if (contactos.getUsername() == null) {
            contactoExistente.setUsername(null);
        }
        
        // Actualizar contraseña SOLO si se proporciona una nueva
        // Si no se envía, mantener la contraseña original
        if (contactos.getPassword() != null && !contactos.getPassword().isEmpty()) {
            contactoExistente.setPassword(contactos.getPassword());
        }
        // Si password es null o vacío, NO actualizamos el campo (mantiene la contraseña original)
        
        // Mantener fechaCreacion y ultimoAcceso originales
        // (no se actualizan desde el frontend)
        
        return Optional.of(contactosRepository.save(contactoExistente));
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

