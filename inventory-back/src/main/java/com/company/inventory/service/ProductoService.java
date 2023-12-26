package com.company.inventory.service;

import com.company.inventory.dto.ProductoDTO;
import com.company.inventory.model.ProductoEntity;
import com.company.inventory.response.CategoryResponseRest;
import org.springframework.http.ResponseEntity;

public interface ProductoService {
    ResponseEntity<CategoryResponseRest> createProduct(ProductoDTO productoDTO);
    ResponseEntity<CategoryResponseRest> listForCategory(String categoryName);
    ProductoEntity buscarProductoForCategory(String categoryName, String productoNombre);
    ProductoEntity editarProducto(Long productoId, ProductoEntity productoActualizado);
    void eliminarProducto(Long productoId);

    ResponseEntity<CategoryResponseRest> listFindAllProductos();
}
