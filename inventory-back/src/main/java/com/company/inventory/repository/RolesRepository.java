package com.company.inventory.repository;

import com.company.inventory.model.RolesUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<RolesUserEntity, String> {
    Optional<RolesUserEntity> findByCargo(String cargo);
    Optional<RolesUserEntity> findByIdRoll(String idRoll);
}
