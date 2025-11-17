package com.legacy.legacy.repository;

import com.legacy.legacy.model.Contactos;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactosRepository extends JpaRepository<Contactos, Integer> {
    
    @EntityGraph(attributePaths = {"rol", "sucursal"})
    @Override
    List<Contactos> findAll();
    
    @EntityGraph(attributePaths = {"rol", "sucursal"})
    @Override
    Optional<Contactos> findById(Integer id);
    
    @EntityGraph(attributePaths = {"rol", "sucursal"})
    Optional<Contactos> findByUsername(String username);
    
    @EntityGraph(attributePaths = {"rol", "sucursal"})
    Optional<Contactos> findByEmail(String email);
    
    @EntityGraph(attributePaths = {"rol", "sucursal"})
    Optional<Contactos> findByUsernameOrEmail(String username, String email);
    
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    
    @EntityGraph(attributePaths = {"rol", "sucursal"})
    @Query("SELECT c FROM Contactos c WHERE c.sucursal.id = :sucursalId")
    List<Contactos> findBySucursalId(@Param("sucursalId") Integer sucursalId);
    
    @EntityGraph(attributePaths = {"rol", "sucursal"})
    @Query("SELECT c FROM Contactos c WHERE c.sucursal.id = :sucursalId AND c.activo = true")
    List<Contactos> findActivosBySucursalId(@Param("sucursalId") Integer sucursalId);
}

