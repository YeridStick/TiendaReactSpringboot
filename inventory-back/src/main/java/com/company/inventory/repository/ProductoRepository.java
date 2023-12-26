package com.company.inventory.repository;

import com.company.inventory.model.ProductoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoRepository extends CrudRepository<ProductoEntity, Long> {
    @Query(value = "SELECT p.* FROM PRODUCTO p WHERE p.CATEGORIA_ID = :idCategory", nativeQuery = true)
    List<ProductoEntity> findByNombreCategory(@Param("idCategory") Long idCategory);

    @Query(value = "SELECT p.* FROM PRODUCTO p\n" +
            "WHERE p.CATEGORIA_ID = :categoryId AND p.NOMBRE = :productName",nativeQuery = true)
    ProductoEntity findByNombreCategoryEndProducto(
            @Param("categoryId") Long categoryId,
            @Param("productName") String productName
    );

}
