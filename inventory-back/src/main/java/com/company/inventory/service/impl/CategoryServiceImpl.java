package com.company.inventory.service.impl;

import com.company.inventory.dto.CategoryDTO;
import com.company.inventory.exception.NotFoundException;
import com.company.inventory.model.CategoryEntity;
import com.company.inventory.repository.CategoryRepository;
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
    //Lista todas las categorias
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
    //Bucar por categoria
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

    //Guardar Categoria
    @Override
    public ResponseEntity<CategoryResponseRest> createCategory(CategoryDTO categoryDTO) {
        CategoryResponseRest response = new CategoryResponseRest();
        try {

            // Validar que no exista previamente
            if (categoryRepository.findByName(categoryDTO.getName()).isPresent()) {
                throw new NotFoundException("Category not found with name: " + categoryDTO.getName());
            }
            /*
              PUT
              categoryRepository.findByName(nameCategory).orElseThrow(() -> new NotFoundException("Category not found with name: " + nameCategory));
            */

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


    @Override
    public CategoryDTO editarCategory(CategoryDTO categoryDTO) {
        // Encontrar la entidad de categoría a editar
        CategoryEntity categoryEntity =  categoryRepository.findByName(categoryDTO.getName())
                .orElseThrow(() -> new NotFoundException("La categoria: " + categoryDTO.getName() + " No existe"));

        // Mapear propiedades del DTO a la entidad
        categoryEntity.setName(categoryDTO.getName());
        categoryEntity.setDescription(categoryDTO.getDescription());

        // Mapear entidad actualizada a DTO
        CategoryDTO responseDTO = new CategoryDTO();
        responseDTO.setName(categoryEntity.getName());
        responseDTO.setDescription(categoryEntity.getDescription());

        // Devolver DTO actualizado
        return responseDTO;
    }

    @Override
    public void eliminarCategory(CategoryDTO categoryDTO) {

        CategoryEntity category = categoryRepository.findByName(categoryDTO.getName())
                .orElseThrow(() -> new NotFoundException("No existe una categoria: " + categoryDTO.getName()));

        categoryRepository.delete(category);
    }

}
