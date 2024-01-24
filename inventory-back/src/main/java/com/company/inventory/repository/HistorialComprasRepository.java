package com.company.inventory.repository;

import com.company.inventory.model.HistorialCompras;
import com.company.inventory.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialComprasRepository extends JpaRepository<HistorialCompras, Long> {
    @Query(value = "select hc.* from historial_compras hc where usuario_id = :user", nativeQuery = true)
    List<HistorialCompras> findByUser(@Param("user") String user);
}
