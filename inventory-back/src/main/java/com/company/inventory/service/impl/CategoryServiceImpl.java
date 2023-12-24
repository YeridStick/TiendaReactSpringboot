package com.company.inventory.service.impl;

import com.company.inventory.dto.CategoryDTO;
import com.company.inventory.exception.NotFoundException;
import com.company.inventory.model.CategoryEntity;
import com.company.inventory.repository.CategoryRepository;
import com.company.inventory.response.CategoryResponse;
import com.company.inventory.response.CategoryResponseRest;
import com.company.inventory.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Buscar todas las categorias
     * @return
     */
    @Override
    public ResponseEntity<CategoryResponseRest> getAllCategories() {
        CategoryResponseRest response = new CategoryResponseRest();
        try {
            List<CategoryEntity> category = (List<CategoryEntity>) categoryRepository.findAll();

            response.getCategoryResponse().setCategoryEntity(category);
            response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");
        }
        catch (Exception e) {
            response.setMetadata("Repuesta no Exitosa", "01", "Error en la consulta");
            //e.getStackTrace();
            e.printStackTrace(); // Imprimir la traza de la excepción en la consola
            return  new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }

    /**
     * Buscar categoria por Nombre
     * @param nameCategory
     * @return
     */
    @Override
    public ResponseEntity<CategoryResponseRest> getCategoryByNameCategory(String nameCategory) {
        CategoryResponseRest response = new CategoryResponseRest();
        try {
            Optional<CategoryEntity> category = Optional.ofNullable(categoryRepository.findByName(nameCategory)
                    .orElseThrow(() -> new NotFoundException("Category not found with name: " + nameCategory)));

            response.getCategoryResponse().setCategoryEntity(Arrays.asList(category.orElse(null)));
            response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");
        }
        catch (Exception e) {
            response.setMetadata("Repuesta no Exitosa", "01", "Error en la consulta");
            e.printStackTrace(); // Imprimir la traza de la excepción en la consola
            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }

    /**
     * createCategory
     ** crea una categoria
     * @param categoryDTO
     * @return
     */
    @Override
    public ResponseEntity<CategoryResponseRest> createCategory(CategoryDTO categoryDTO) {
        CategoryResponseRest response = new CategoryResponseRest();
        try {

            // Validar que no exista previamente
            if (categoryRepository.findByName(categoryDTO.getName()).isPresent()) {
                response.setMetadata("La Categoria: " + categoryDTO.getName()  + " Ya Existe", "01", "Error al ingresar: " + categoryDTO.getDescription());
                return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            // Crear y guardar la nueva categoría
            CategoryEntity newCategory = new CategoryEntity();

            // Guardo el name y description de la categoria
            newCategory.setName(categoryDTO.getName());
            newCategory.setDescription(categoryDTO.getDescription());

            CategoryEntity createdCategory = categoryRepository.save(newCategory);

            response.getCategoryResponse().setCategoryEntity(Arrays.asList(createdCategory));
            response.setMetadata("Respuesta ok", "00", "Categoría creada exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        catch (Exception e) {
            // Manejar cualquier otro error
            response.setMetadata("Repuesta no Exitosa", "01", "Error al ingresar: " + categoryDTO.getDescription());
            e.printStackTrace(); // Imprimir la traza de la excepción en la consola
            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Editar Categoria
     ** edita la descripcion de la categoria.
     * @param categoryDTO
     * @return
     */

    @Override
    public ResponseEntity<CategoryResponseRest> editarCategory(CategoryDTO categoryDTO) {
        CategoryResponseRest response = new CategoryResponseRest();
        try {
            CategoryEntity categoryEntity =  categoryRepository.findByName(categoryDTO.getName())
                    .orElseThrow(() -> new NotFoundException("La categoria: " + categoryDTO.getName() + " No existe"));

            // Actualizar propiedades de la categoría con los valores del DTO
            // categoryEntity.setName(categoryEntity.getName());
            categoryEntity.setDescription(categoryDTO.getDescription());

            CategoryEntity updatedCategory = categoryRepository.save(categoryEntity);

            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setCategoryEntity(Arrays.asList(updatedCategory));
            response.setCategoryResponse(categoryResponse);

            response.setMetadata("Respuesta ok", "00", "Categoría editada exitosamente");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e){
            response.setMetadata("Repuesta no Exitosa", "01", "Error al editar Categoria");
            e.printStackTrace(); // Imprimir la traza de la excepción en la consola
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    /**
     * Eliminar Categoria
     * @param nameCategory
     * @return
     */
    @Override
    public ResponseEntity<CategoryResponseRest> eliminarCategory(String nameCategory) {
        CategoryResponseRest response = new CategoryResponseRest();
        try {
            Optional<CategoryEntity> category = categoryRepository.findByName(nameCategory);
            if (category.isPresent()){
                CategoryEntity categoryOptional = category.get();
                categoryRepository.delete(categoryOptional);

                response.setMetadata("Respuesta ok", "00", "Categoría eliminada exitosamente");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response.setMetadata("La Categoria: " + nameCategory + " No Existe", "01", "Error al Eliminar");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.setMetadata("Repuesta no Exitosa", "01", "Error al eliminar Categoria");
            e.printStackTrace(); // Imprimir la traza de la excepción en la consola
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


}
