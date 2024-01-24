package com.company.inventory.repository;

import com.company.inventory.model.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {
    @Query(value = "SELECT p.* FROM PRODUCTO p WHERE p.CATEGORIA_ID = :idCategory", nativeQuery = true)
    List<ProductoEntity> findByNombreCategory(@Param("idCategory") Long idCategory);

    @Query(value = "SELECT p.* FROM PRODUCTO p\n" +
            "WHERE p.CATEGORIA_ID = :categoryId AND p.NOMBRE = :productName",nativeQuery = true)
    ProductoEntity findByNombreCategoryEndProducto(
            @Param("categoryId") Long categoryId,
            @Param("productName") String productName
    );
    @Query(value = "SELECT * FROM producto p WHERE p.nombre LIKE %:nameProducto%", nativeQuery = true)
    List<ProductoEntity> findByNombreList(@Param("nameProducto") String nameProducto);



}
