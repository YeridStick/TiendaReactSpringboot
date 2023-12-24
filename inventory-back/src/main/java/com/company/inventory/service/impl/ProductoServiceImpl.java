package com.company.inventory.service.impl;

import com.company.inventory.exception.NotFoundException;
import com.company.inventory.model.CategoryEntity;
import com.company.inventory.model.ProductoEntity;
import com.company.inventory.repository.CategoryRepository;
import com.company.inventory.repository.ProductoRepository;
import com.company.inventory.service.ProductoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {
    private final ProductoRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductoServiceImpl(ProductoRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductoEntity createProduct(ProductoEntity product, String categoryName) {
        CategoryEntity category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new NotFoundException("No existe la categoria: " + categoryName));

        // Verificar si ya existe un producto con el mismo nombre en la categoría
        List<ProductoEntity> existingProducts = productRepository.findByNombreCategory(categoryName);
        for (ProductoEntity existingProduct : existingProducts) {
            if (existingProduct.getCategory().equals(category) && existingProduct.getNombre().equals(product.getNombre())) {
                throw new NotFoundException("Ya existe un producto con el nombre:  " + product.getNombre() + " en la Categoria: " + categoryName);
            }
        }

        product.setCategory(category);
        return productRepository.save(product);
    }

    public List<ProductoEntity> listForCategory(String categoryName) {
        categoryRepository.findByName(categoryName).orElseThrow(() -> new NotFoundException("No existe la categoria: " + categoryName));
        return productRepository.findByNombreCategory(categoryName);
    }

    public ProductoEntity buscarProductoForCategory(String categoryName, String productoNombre) {
        categoryRepository.findByName(categoryName).orElseThrow(() -> new NotFoundException("No existe la categoria: " + categoryName));
        return productRepository.findByNombreCategoryEndProducto(categoryName, productoNombre);
    }

    public ProductoEntity editarProducto(Long productoId, ProductoEntity productoActualizado) {
        ProductoEntity productoExistente = productRepository.findById(productoId)
                .orElseThrow(() -> new NotFoundException("No se encontró el producto con ID: " + productoId));

        // Actualizar los campos del producto existente con los valores del producto actualizado
        productoExistente.setNombre(productoActualizado.getNombre());
        productoExistente.setPrice(productoActualizado.getPrice());
        // Actualizar otros campos según sea necesario

        return productRepository.save(productoExistente);
    }

    public void eliminarProducto(Long productoId) {
        ProductoEntity productoExistente = productRepository.findById(productoId)
                .orElseThrow(() -> new NotFoundException("No se encontró el producto con ID: " + productoId));

        productRepository.delete(productoExistente);
    }


}