package com.company.inventory.service;

import com.company.inventory.dto.CategoryDTO;
import com.company.inventory.model.CategoryEntity;
import com.company.inventory.response.CategoryResponseRest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    ResponseEntity<CategoryResponseRest> getAllCategories();
    ResponseEntity<CategoryResponseRest> getCategoryByNameCategory(String nameCategory);
    ResponseEntity<CategoryResponseRest> createCategory(CategoryDTO categoryDTO);
    CategoryDTO editarCategory(CategoryDTO categoryDTO);
    void eliminarCategory(CategoryDTO categoryDTO) ;
}
