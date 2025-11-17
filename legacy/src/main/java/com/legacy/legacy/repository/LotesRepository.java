package com.legacy.legacy.repository;

import com.legacy.legacy.model.Lotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface LotesRepository extends JpaRepository<Lotes, Integer> {
    
    @Query("SELECT COUNT(l) FROM Lotes l WHERE l.stockActual < 10")
    Long countByStockBajo();
    
    @Query("SELECT l FROM Lotes l WHERE l.stockActual < 10 ORDER BY l.stockActual ASC")
    List<Lotes> findByStockBajo();
    
    @Query("SELECT l FROM Lotes l WHERE l.fechaVencimiento IS NOT NULL AND l.fechaVencimiento BETWEEN :fechaInicio AND :fechaFin ORDER BY l.fechaVencimiento ASC")
    List<Lotes> findProximosVencer(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);
    
    @Query("SELECT l FROM Lotes l WHERE l.fechaVencimiento IS NOT NULL AND l.fechaVencimiento < :fecha ORDER BY l.fechaVencimiento ASC")
    List<Lotes> findVencidos(@Param("fecha") LocalDate fecha);
    
    @Query("SELECT l FROM Lotes l WHERE l.producto.id = :productoId")
    List<Lotes> findByProductoId(@Param("productoId") Integer productoId);
}

