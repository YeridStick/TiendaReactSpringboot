package com.company.inventory.service.impl;

import com.company.inventory.constantes.Constantes;
import com.company.inventory.dto.CategoryDTO;
import com.company.inventory.dto.ProductoDTO;
import com.company.inventory.exception.ExcepcionPersonalizada;
import com.company.inventory.model.CategoryEntity;
import com.company.inventory.model.ProductoEntity;
import com.company.inventory.repository.CategoryRepository;
import com.company.inventory.repository.ProductoRepository;
import com.company.inventory.response.CategoryResponse;
import com.company.inventory.response.CategoryResponseRest;
import com.company.inventory.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductoRepository productoRepository;
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductoRepository productoRepository) {
        this.categoryRepository = categoryRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public CategoryResponseRest getAllCategories() {
        CategoryResponseRest response = new CategoryResponseRest();
        try {
            List<CategoryEntity> categories = categoryRepository.findAll();

            ModelMapper modelMapper = new ModelMapper();

            List<CategoryDTO> categoryDTOs = categories.stream()
                    .map(categoryEntity -> {
                        CategoryDTO categoryDTO = modelMapper.map(categoryEntity, CategoryDTO.class);

                        List<ProductoEntity> productos = productoRepository.findByNombreCategory(categoryEntity.getId());
                        List<ProductoDTO> productoDTOs = productos.stream()
                                .map(productoEntity -> modelMapper.map(productoEntity, ProductoDTO.class))
                                .collect(Collectors.toList());

                        categoryDTO.setProducts(productoDTOs);
                        return categoryDTO;
                    })
                    .collect(Collectors.toList());

            response.getCategoryResponse().setCategoryDTOS(categoryDTOs);
            response.setMetadata(Constantes.TextRespuesta, "Datos recuperados Correctamenete");
            return response;
        } catch (Exception e) {
            throw new ExcepcionPersonalizada("Error en la consulta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public CategoryResponseRest getCategoryByNameCategory(String nameCategory) {
        CategoryResponseRest response = new CategoryResponseRest();
        try {
            CategoryEntity category =
                    categoryRepository.findByName(nameCategory)
                    .orElseThrow(() -> new ExcepcionPersonalizada("Categoria no encontrada: " + nameCategory, HttpStatus.NOT_FOUND));

            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());
            categoryDTO.setDescription(category.getDescription());

            ModelMapper modelMapper = new ModelMapper();

            List<ProductoEntity> productos = productoRepository.findByNombreCategory(category.getId());

            List<ProductoDTO> productoDTOs = productos.stream()
                            .map(productoEntity -> modelMapper.map(productoEntity, ProductoDTO.class))
                                    .collect(Collectors.toList());

            categoryDTO.setProducts(productoDTOs);

            response.getCategoryResponse().setCategoryDTOS(Arrays.asList(categoryDTO));
            response.setMetadata(Constantes.TextRespuesta,"Respuesta exitosa");

            return response;
        }
        catch (Exception e) {
            throw new ExcepcionPersonalizada(Constantes.TextRespuestaNo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<CategoryResponseRest> createCategory(CategoryDTO categoryDTO) {
        CategoryResponseRest response = new CategoryResponseRest();
        try {

            if (categoryRepository.findByName(categoryDTO.getName()).isPresent()) {
                response.setMetadata("La Categoria: " + categoryDTO.getName()  + " Ya Existe","Error al ingresar: " + categoryDTO.getDescription());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            CategoryEntity newCategory = new CategoryEntity();

            newCategory.setName(categoryDTO.getName());
            newCategory.setDescription(categoryDTO.getDescription());

            categoryRepository.save(newCategory);

            response.getCategoryResponse().setCategoryDTOS(Arrays.asList(categoryDTO));
            response.setMetadata(Constantes.TextRespuesta,"Categoría creada exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        catch (Exception e) {
            throw new ExcepcionPersonalizada(Constantes.TextRespuestaNo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<CategoryResponseRest> editarCategory(CategoryDTO categoryDTO) {
        CategoryResponseRest response = new CategoryResponseRest();
        try {
            CategoryEntity categoryEntity =  categoryRepository.findByName(categoryDTO.getName())
                    .orElseThrow(() -> new ExcepcionPersonalizada("La categoria: " + categoryDTO.getName() + " No existe", HttpStatus.NOT_FOUND));

            categoryEntity.setDescription(categoryDTO.getDescription());

            categoryRepository.save(categoryEntity);

            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setCategoryDTOS(Arrays.asList(categoryDTO));
            response.setCategoryResponse(categoryResponse);

            response.setMetadata(Constantes.TextRespuesta,"Categoría editada exitosamente");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e){
            throw new ExcepcionPersonalizada("Error al Editar Categoria", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<CategoryResponseRest> eliminarCategory(String nameCategory) {
        CategoryResponseRest response = new CategoryResponseRest();
        try {
            CategoryEntity category =  categoryRepository.findByName(nameCategory)
                    .orElseThrow(() -> new ExcepcionPersonalizada("La categoria: " + nameCategory + " No existe", HttpStatus.NOT_FOUND));

            categoryRepository.delete(category);

            response.setMetadata(Constantes.TextRespuesta,"Categoría eliminada exitosamente");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            throw new ExcepcionPersonalizada("Error al Eliminar Categoria", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
