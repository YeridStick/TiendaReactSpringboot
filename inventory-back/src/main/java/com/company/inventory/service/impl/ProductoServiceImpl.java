package com.company.inventory.service.impl;

import com.company.inventory.dto.ProductoDTO;
import com.company.inventory.exception.NotFoundException;
import com.company.inventory.model.CategoryEntity;
import com.company.inventory.model.ProductoEntity;
import com.company.inventory.repository.CategoryRepository;
import com.company.inventory.repository.ProductoRepository;
import com.company.inventory.response.CategoryResponseRest;
import com.company.inventory.service.ProductoService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductoServiceImpl implements ProductoService {
    private final ProductoRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductoServiceImpl(ProductoRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Crear Producto
     * @param productoDTO
     * @return
     */

    @Override
    public ResponseEntity<CategoryResponseRest> createProduct(ProductoDTO productoDTO) {
        CategoryResponseRest response = new CategoryResponseRest();

        try {
            CategoryEntity categoryEntity = categoryRepository.findByName(productoDTO.getNameCategory())
                    .orElseThrow(() -> new NotFoundException("La categoría '" + productoDTO.getNameCategory() + "' no existe"));

            ProductoEntity existingProducts = productRepository.findByNombreCategoryEndProducto(categoryEntity.getId(), productoDTO.getNombre());

            if (existingProducts != null) {
                String errorMessage = "Ya existe un producto con el nombre '" + productoDTO.getNombre() + "' en la categoría '" + productoDTO.getNameCategory() + "'";
                response.setMetadata(errorMessage, "01", "Error al ingresar: " + productoDTO.getNombre());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            ProductoEntity product = new ProductoEntity();
            product.setNombre(productoDTO.getNombre());
            product.setDescripcion(productoDTO.getDescripcion());
            product.setUrlImg(productoDTO.getUrlImg());
            product.setPrice(productoDTO.getPrice());
            product.setCategory(categoryEntity);

            productRepository.save(product);

            response.setMetadata("Producto creado exitosamente en la categoría: " + productoDTO.getNameCategory(), "00", "Respuesta ok");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (NotFoundException e) {
            response.setMetadata("Error al ingresar: " + productoDTO.getNameCategory(), "01", e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            response.setMetadata("Error al procesar la solicitud", "01", e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Listar por Categoria
     * @param categoryName
     * @return
     */
    @Override
    public ResponseEntity<CategoryResponseRest> listForCategory(String categoryName) {
        CategoryResponseRest response = new CategoryResponseRest();
        try {
            Optional<CategoryEntity> categoryEntity = categoryRepository.findByName(categoryName);

            if (categoryEntity.isPresent()) {
                CategoryEntity category = categoryEntity.get();

                List<ProductoEntity> producto = productRepository.findByNombreCategory(category.getId());

                response.getCategoryResponse().setCategoryName(category.getName());
                response.getCategoryResponse().setProductoEntity(producto);

                response.setMetadata("Respuesta ok", "00", "Productos de la categoría recuperados correctamente");
                return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
            } else {
                response.setMetadata("Repuesta no Exitosa", "01", "Categoría no encontrada");
                return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("Repuesta no Exitosa", "01", "Error en la consulta");
            e.printStackTrace(); // Imprimir la traza de la excepción en la consola
            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Listar Todas las Categorias
     * @return
     */
    @Override
    public  ResponseEntity<CategoryResponseRest> listFindAllProductos() {
        CategoryResponseRest response = new CategoryResponseRest();
        try{
            List<ProductoEntity> productos = (List<ProductoEntity>) productRepository.findAll();

            response.getCategoryResponse().setProductoEntity(productos);
            response.setMetadata("Respuesta ok", "00", "Productos de la categoría recuperados correctamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMetadata("Repuesta no Exitosa", "01", "Error en la consulta");
            e.printStackTrace(); // Imprimir la traza de la excepción en la consola
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public ResponseEntity<CategoryResponseRest> buscarProductoInCategory(String nameCategory, String nameProducto) {
        CategoryResponseRest response = new CategoryResponseRest();
        try {
            CategoryEntity category = categoryRepository.findByName(nameCategory).orElseThrow(() -> new NotFoundException("No existe la categoria: " + nameCategory));

            ProductoEntity producto =  productRepository.findByNombreCategoryEndProducto(category.getId(), nameProducto);

            ProductoDTO productoDTO = new ProductoDTO();
            productoDTO.setNameCategory(nameCategory);
            productoDTO.setPrice(producto.getPrice());
            productoDTO.setId(producto.getId());
            productoDTO.setNombre(producto.getNombre());            productoDTO.setDescripcion(productoDTO.getDescripcion());
            productoDTO.setUrlImg(productoDTO.getUrlImg());

            response.getCategoryResponse().setProductoDTO(productoDTO);
            response.setMetadata("Respuesta ok", "00", "Productos de la categoría recuperados correctamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch ( Exception e) {
            response.setMetadata("Repuesta no Exitosa", "01", "Error en la consulta");
            e.printStackTrace(); // Imprimir la traza de la excepción en la consola
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public ProductoEntity editarProducto(Long productoId, ProductoEntity productoActualizado) {
        ProductoEntity productoExistente = productRepository.findById(productoId)
                .orElseThrow(() -> new NotFoundException("No se encontró el producto con ID: " + productoId));

        // Actualizar los campos del producto existente con los valores del producto actualizado
        productoExistente.setNombre(productoActualizado.getNombre());
        productoExistente.setPrice(productoActualizado.getPrice());
        // Actualizar otros campos según sea necesario

        return productRepository.save(productoExistente);
    }

    @Override
    public void eliminarProducto(Long productoId) {
        ProductoEntity productoExistente = productRepository.findById(productoId)
                .orElseThrow(() -> new NotFoundException("No se encontró el producto con ID: " + productoId));

        productRepository.delete(productoExistente);
    }


}