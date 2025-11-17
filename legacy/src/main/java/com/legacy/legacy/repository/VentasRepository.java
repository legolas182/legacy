package com.legacy.legacy.repository;

import com.legacy.legacy.model.Ventas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VentasRepository extends JpaRepository<Ventas, Integer> {
    
    @Query("SELECT COALESCE(SUM(v.totalGeneral), 0) FROM Ventas v WHERE CAST(v.fecha AS date) = :fecha")
    BigDecimal sumTotalByFecha(@Param("fecha") LocalDate fecha);
    
    @Query("SELECT COALESCE(SUM(v.totalGeneral), 0) FROM Ventas v WHERE YEAR(v.fecha) = :year AND MONTH(v.fecha) = :month")
    BigDecimal sumTotalByMonth(@Param("year") int year, @Param("month") int month);
    
    @Query("SELECT COALESCE(SUM(v.totalGeneral), 0) FROM Ventas v WHERE YEAR(v.fecha) = :year")
    BigDecimal sumTotalByYear(@Param("year") int year);
    
    @Query("SELECT v FROM Ventas v WHERE v.fecha BETWEEN :fechaInicio AND :fechaFin ORDER BY v.fecha DESC")
    List<Ventas> findByFechaBetween(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
    
    @Query("SELECT YEAR(v.fecha) as year, MONTH(v.fecha) as month, COALESCE(SUM(v.totalGeneral), 0) as total " +
           "FROM Ventas v WHERE v.fecha >= :fechaInicio GROUP BY YEAR(v.fecha), MONTH(v.fecha) " +
           "ORDER BY YEAR(v.fecha), MONTH(v.fecha)")
    List<Object[]> findVentasMensuales(@Param("fechaInicio") LocalDateTime fechaInicio);
    
    @Query("SELECT v FROM Ventas v WHERE v.sucursal.id = :sucursalId AND v.fecha BETWEEN :fechaInicio AND :fechaFin ORDER BY v.fecha DESC")
    List<Ventas> findBySucursalIdAndFechaBetween(@Param("sucursalId") Integer sucursalId, @Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
    
    @Query("SELECT COALESCE(SUM(v.totalGeneral), 0) FROM Ventas v WHERE v.sucursal.id = :sucursalId AND CAST(v.fecha AS date) = :fecha")
    BigDecimal sumTotalBySucursalIdAndFecha(@Param("sucursalId") Integer sucursalId, @Param("fecha") LocalDate fecha);
    
    @Query("SELECT COALESCE(SUM(v.totalGeneral), 0) FROM Ventas v WHERE v.sucursal.id = :sucursalId AND YEAR(v.fecha) = :year AND MONTH(v.fecha) = :month")
    BigDecimal sumTotalBySucursalIdAndMonth(@Param("sucursalId") Integer sucursalId, @Param("year") int year, @Param("month") int month);
}

