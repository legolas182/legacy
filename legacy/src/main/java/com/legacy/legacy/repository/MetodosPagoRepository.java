package com.legacy.legacy.repository;

import com.legacy.legacy.model.MetodosPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetodosPagoRepository extends JpaRepository<MetodosPago, Integer> {
}

