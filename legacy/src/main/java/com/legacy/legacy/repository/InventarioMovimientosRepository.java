package com.legacy.legacy.repository;

import com.legacy.legacy.model.InventarioMovimientos;
import com.legacy.legacy.model.enums.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InventarioMovimientosRepository extends JpaRepository<InventarioMovimientos, Integer> {
    
    @Query("SELECT im FROM InventarioMovimientos im WHERE im.fecha BETWEEN :fechaInicio AND :fechaFin ORDER BY im.fecha DESC")
    List<InventarioMovimientos> findByFechaBetween(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
    
    @Query("SELECT im FROM InventarioMovimientos im WHERE im.fecha BETWEEN :fechaInicio AND :fechaFin AND im.tipo = :tipo ORDER BY im.fecha DESC")
    List<InventarioMovimientos> findByFechaBetweenAndTipo(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin, @Param("tipo") TipoMovimiento tipo);
    
    @Query("SELECT COALESCE(SUM(im.cantidad), 0) FROM InventarioMovimientos im WHERE im.fecha BETWEEN :fechaInicio AND :fechaFin AND im.tipo = 'ENTRADA'")
    Integer sumEntradasByFechaBetween(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
    
    @Query("SELECT COALESCE(SUM(im.cantidad), 0) FROM InventarioMovimientos im WHERE im.fecha BETWEEN :fechaInicio AND :fechaFin AND im.tipo = 'SALIDA'")
    Integer sumSalidasByFechaBetween(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
    
    @Query("SELECT im FROM InventarioMovimientos im WHERE im.sucursal.id = :sucursalId AND im.fecha BETWEEN :fechaInicio AND :fechaFin ORDER BY im.fecha DESC")
    List<InventarioMovimientos> findBySucursalIdAndFechaBetween(@Param("sucursalId") Integer sucursalId, @Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
    
    @Query("SELECT im FROM InventarioMovimientos im WHERE im.sucursal.id = :sucursalId AND im.fecha BETWEEN :fechaInicio AND :fechaFin AND im.tipo = :tipo ORDER BY im.fecha DESC")
    List<InventarioMovimientos> findBySucursalIdAndFechaBetweenAndTipo(@Param("sucursalId") Integer sucursalId, @Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin, @Param("tipo") TipoMovimiento tipo);
    
    @Query("SELECT COALESCE(SUM(im.cantidad), 0) FROM InventarioMovimientos im WHERE im.sucursal.id = :sucursalId AND im.fecha BETWEEN :fechaInicio AND :fechaFin AND im.tipo = 'ENTRADA'")
    Integer sumEntradasBySucursalIdAndFechaBetween(@Param("sucursalId") Integer sucursalId, @Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
    
    @Query("SELECT COALESCE(SUM(im.cantidad), 0) FROM InventarioMovimientos im WHERE im.sucursal.id = :sucursalId AND im.fecha BETWEEN :fechaInicio AND :fechaFin AND im.tipo = 'SALIDA'")
    Integer sumSalidasBySucursalIdAndFechaBetween(@Param("sucursalId") Integer sucursalId, @Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
}

