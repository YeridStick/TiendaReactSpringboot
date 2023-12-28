package com.company.inventory.service;

import com.company.inventory.dto.ProductoDTO;
import com.company.inventory.model.ProductoEntity;
import com.company.inventory.response.CategoryResponseRest;
import org.springframework.http.ResponseEntity;

public interface ProductoService {
    ResponseEntity<CategoryResponseRest> createProduct(ProductoDTO productoDTO);
    ResponseEntity<CategoryResponseRest> listForCategory(String categoryName);
    ResponseEntity<CategoryResponseRest> buscarProductoInCategory(String nameCategory, String nameProducto);
    ResponseEntity<CategoryResponseRest>  editarProducto(ProductoDTO productoActualizadoDTO);
    ResponseEntity<CategoryResponseRest>  eliminarProducto(Long productoId);

    ResponseEntity<CategoryResponseRest> listFindAllProductos();
}
