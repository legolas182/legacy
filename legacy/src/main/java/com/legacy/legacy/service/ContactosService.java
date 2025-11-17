package com.legacy.legacy.service;

import com.legacy.legacy.model.Contactos;
import java.util.List;
import java.util.Optional;

public interface ContactosService {
    // Métodos básicos de CRUD
    List<Contactos> findAll();
    Optional<Contactos> findById(Integer id);
    Contactos save(Contactos contactos);
    Optional<Contactos> update(Integer id, Contactos contactos);
    boolean deleteById(Integer id);
    boolean existsById(Integer id);
    
    // Métodos específicos para gestión de usuarios
    Contactos crearUsuario(Contactos contacto, Integer sucursalId);
    Optional<Contactos> actualizarUsuario(Integer id, Contactos contactoActualizado, Integer sucursalId);
    List<Contactos> listarUsuarios();
    List<Contactos> listarUsuariosPorSucursal(Integer sucursalId);
    Optional<Contactos> obtenerUsuario(Integer id);
}
