package com.company.inventory.service;

import com.company.inventory.dto.CategoryDTO;
import com.company.inventory.response.CategoryResponseRest;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    /**
     * Listar todas las Categorias
     * @return
     */
    CategoryResponseRest getAllCategories();
    CategoryResponseRest getCategoryByNameCategory(String nameCategory);
    ResponseEntity<CategoryResponseRest> createCategory(CategoryDTO categoryDTO);
    ResponseEntity<CategoryResponseRest> editarCategory(CategoryDTO categoryDTO);
    ResponseEntity<CategoryResponseRest> eliminarCategory(String nameCategory);
}
