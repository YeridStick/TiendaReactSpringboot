package com.company.inventory.service;

import com.company.inventory.model.ProductoEntity;

import java.util.List;

public interface ProductoService {
    ProductoEntity createProduct(ProductoEntity product, String categoryName);
    List<ProductoEntity> listForCategory(String categoryName);
    ProductoEntity buscarProductoForCategory(String categoryName, String productoNombre);
    ProductoEntity editarProducto(Long productoId, ProductoEntity productoActualizado);
    void eliminarProducto(Long productoId);
}
