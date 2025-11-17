package com.legacy.legacy.repository;

import com.legacy.legacy.model.VentasDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VentasDetalleRepository extends JpaRepository<VentasDetalle, Integer> {
    
    @Query("SELECT vd.producto.id, vd.producto.nombre, " +
           "SUM(vd.cantidad) as totalCantidad, " +
           "SUM(vd.cantidad * vd.precioUnitario) as totalIngresos " +
           "FROM VentasDetalle vd " +
           "WHERE vd.venta.fecha BETWEEN :fechaInicio AND :fechaFin " +
           "GROUP BY vd.producto.id, vd.producto.nombre " +
           "ORDER BY totalCantidad DESC")
    List<Object[]> findTopProductosVendidos(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
    
    @Query("SELECT vd FROM VentasDetalle vd WHERE vd.venta.id = :ventaId")
    List<VentasDetalle> findByVentaId(@Param("ventaId") Integer ventaId);
}

