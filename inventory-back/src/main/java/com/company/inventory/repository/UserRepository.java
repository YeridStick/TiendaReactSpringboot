package com.company.inventory.repository;

import com.company.inventory.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findById(String numeroIdentificacion);
    Optional<UserEntity> findByNumeroTelefono(Long numTelefono);
    Optional<UserEntity> findByCorreo(String correo);
}
