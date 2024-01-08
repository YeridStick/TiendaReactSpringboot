package com.company.inventory.repository;

import com.company.inventory.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findById(String numeroIdentificacion);
    Optional<UserEntity> findByNumeroTelefono(Long numTelefono);
    Optional<UserEntity> findByCorreo(String correo);

    @Query(value = "SELECT u.* \n" +
            "FROM user u \n" +
            "WHERE u.correo = :correo \n" +
            "\tAND u.password = :password", nativeQuery = true)
    Optional<UserEntity> userLogin(
            @Param("correo") String correo,
            @Param("password") String password
    );
}
