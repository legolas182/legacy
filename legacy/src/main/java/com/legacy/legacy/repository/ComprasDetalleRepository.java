package com.legacy.legacy.repository;

import com.legacy.legacy.model.ComprasDetalle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ComprasDetalleRepository extends JpaRepository<ComprasDetalle, Integer> {
    
    // Buscar el precio de compra más reciente por número de lote y producto
    @Query("SELECT cd.precioUnitario FROM ComprasDetalle cd " +
           "WHERE cd.numeroLote = :numeroLote AND cd.producto.id = :productoId " +
           "ORDER BY cd.compra.fecha DESC")
    List<BigDecimal> findPrecioCompraByLoteAndProducto(
        @Param("numeroLote") String numeroLote,
        @Param("productoId") Integer productoId,
        Pageable pageable
    );
    
    default Optional<BigDecimal> findPrecioCompraByLoteAndProducto(String numeroLote, Integer productoId) {
        List<BigDecimal> resultados = findPrecioCompraByLoteAndProducto(
            numeroLote, 
            productoId, 
            org.springframework.data.domain.PageRequest.of(0, 1)
        );
        return resultados.isEmpty() ? Optional.empty() : Optional.of(resultados.get(0));
    }
    
    // Buscar el precio de compra más reciente por producto (sin número de lote específico)
    @Query("SELECT cd.precioUnitario FROM ComprasDetalle cd " +
           "WHERE cd.producto.id = :productoId " +
           "ORDER BY cd.compra.fecha DESC")
    List<BigDecimal> findUltimoPrecioCompraByProducto(
        @Param("productoId") Integer productoId,
        Pageable pageable
    );
    
    default Optional<BigDecimal> findUltimoPrecioCompraByProducto(Integer productoId) {
        List<BigDecimal> resultados = findUltimoPrecioCompraByProducto(
            productoId, 
            org.springframework.data.domain.PageRequest.of(0, 1)
        );
        return resultados.isEmpty() ? Optional.empty() : Optional.of(resultados.get(0));
    }
    
    @Query("SELECT cd FROM ComprasDetalle cd WHERE cd.compra.id = :compraId")
    List<ComprasDetalle> findByCompraId(@Param("compraId") Integer compraId);
}

