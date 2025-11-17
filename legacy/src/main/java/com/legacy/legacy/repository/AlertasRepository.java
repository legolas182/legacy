package com.legacy.legacy.repository;

import com.legacy.legacy.model.Alertas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AlertasRepository extends JpaRepository<Alertas, Integer> {
    
    @Query("SELECT COUNT(a) FROM Alertas a WHERE a.lote.stockActual < 10 OR a.tipo = 'PROXIMO_VENCER' OR a.tipo = 'VENCIDO'")
    Long countAlertasActivas();
    
    @Query("SELECT a FROM Alertas a ORDER BY a.fecha DESC")
    List<Alertas> findAlertasRecientes();
}

