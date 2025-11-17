package com.legacy.legacy.service;

import com.legacy.legacy.dto.*;
import com.legacy.legacy.model.Contactos;
import com.legacy.legacy.model.enums.TipoContacto;
import com.legacy.legacy.repository.ContactosRepository;
import com.legacy.legacy.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private ContactosRepository contactosRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        Optional<Contactos> contactoOpt = contactosRepository
                .findByUsernameOrEmail(request.getUsername(), request.getUsername());

        if (contactoOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Contactos contacto = contactoOpt.get();

        // Verificar contraseña (en producción usa BCrypt)
        if (!contacto.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        if (!contacto.getActivo()) {
            throw new RuntimeException("Usuario inactivo");
        }

        // Actualizar último acceso
        contacto.setUltimoAcceso(LocalDateTime.now());
        contactosRepository.save(contacto);

        // Generar token simple (en producción usa JWT)
        String token = generateSimpleToken(contacto);

        // Crear UserDTO
        UserDTO userDTO = new UserDTO();
        userDTO.setId(contacto.getId());
        userDTO.setNombre(contacto.getNombre());
        userDTO.setUsername(contacto.getUsername());
        userDTO.setEmail(contacto.getEmail());
        userDTO.setRolNombre(contacto.getRol() != null ? contacto.getRol().getNombre() : "USER");

        return new LoginResponseDTO(token, userDTO);
    }

    @Override
    public LoginResponseDTO register(RegisterRequestDTO request) {
        // Verificar si el usuario ya existe
        if (contactosRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }

        if (contactosRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El correo electrónico ya está en uso");
        }

        // Crear nuevo contacto
        Contactos nuevoContacto = new Contactos();
        nuevoContacto.setNombre(request.getFullName());
        nuevoContacto.setUsername(request.getUsername());
        nuevoContacto.setEmail(request.getEmail());
        nuevoContacto.setPassword(request.getPassword()); // En producción usar BCrypt
        nuevoContacto.setTipoContacto(TipoContacto.CLIENTE);
        nuevoContacto.setActivo(true);
        nuevoContacto.setFechaCreacion(LocalDateTime.now());

        // Asignar rol por defecto
        rolesRepository.findById(1).ifPresent(nuevoContacto::setRol);

        Contactos contactoGuardado = contactosRepository.save(nuevoContacto);

        // Generar token
        String token = generateSimpleToken(contactoGuardado);

        // Crear UserDTO
        UserDTO userDTO = new UserDTO();
        userDTO.setId(contactoGuardado.getId());
        userDTO.setNombre(contactoGuardado.getNombre());
        userDTO.setUsername(contactoGuardado.getUsername());
        userDTO.setEmail(contactoGuardado.getEmail());
        userDTO.setRolNombre(contactoGuardado.getRol() != null ? contactoGuardado.getRol().getNombre() : "USER");

        return new LoginResponseDTO(token, userDTO);
    }

    private String generateSimpleToken(Contactos contacto) {
        // Token simple para desarrollo (en producción usar JWT)
        String tokenData = contacto.getId() + ":" + contacto.getUsername() + ":" + UUID.randomUUID();
        return Base64.getEncoder().encodeToString(tokenData.getBytes());
    }
}

