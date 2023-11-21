package com.company.inventory.repository;

import com.company.inventory.model.ProductoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoRepository extends CrudRepository<ProductoEntity, Long> {
    @Query("SELECT p FROM ProductoEntity p WHERE p.category.name = :nameCategory")
    List<ProductoEntity> findByNombreCategory(@Param("nameCategory") String nameCategory);

    @Query("SELECT p FROM ProductoEntity p WHERE p.category.name = :categoryName AND p.nombre = :productName")
    ProductoEntity findByNombreCategoryEndProducto(
            @Param("categoryName") String categoryName,
            @Param("productName") String productName
    );
}
