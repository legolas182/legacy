package com.legacy.legacy.repository;

import com.legacy.legacy.model.Compras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ComprasRepository extends JpaRepository<Compras, Integer> {
    
    @Query("SELECT COALESCE(SUM(c.total), 0) FROM Compras c WHERE CAST(c.fecha AS date) = :fecha")
    BigDecimal sumTotalByFecha(@Param("fecha") LocalDate fecha);
    
    @Query("SELECT COALESCE(SUM(c.total), 0) FROM Compras c WHERE YEAR(c.fecha) = :year AND MONTH(c.fecha) = :month")
    BigDecimal sumTotalByMonth(@Param("year") int year, @Param("month") int month);
    
    @Query("SELECT COALESCE(SUM(c.total), 0) FROM Compras c WHERE YEAR(c.fecha) = :year")
    BigDecimal sumTotalByYear(@Param("year") int year);
    
    @Query("SELECT c FROM Compras c WHERE c.fecha BETWEEN :fechaInicio AND :fechaFin ORDER BY c.fecha DESC")
    List<Compras> findByFechaBetween(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
}

