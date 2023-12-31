package com.company.inventory.repository;

import com.company.inventory.model.CategoryEntity;
import com.company.inventory.model.TipoEntidadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoEntidadRepository extends JpaRepository<TipoEntidadEntity, Long> {
    Optional<TipoEntidadEntity> findBySigla(String name);
}
