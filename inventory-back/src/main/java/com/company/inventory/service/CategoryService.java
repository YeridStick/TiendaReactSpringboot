package com.company.inventory.service;

import com.company.inventory.dto.CategoryDTO;
import com.company.inventory.response.MensajeResponseRest;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    /**
     * Listar todas las Categorias
     * @return
     */
    MensajeResponseRest getAllCategories();
    MensajeResponseRest getCategoryByNameCategory(String nameCategory);
    ResponseEntity<MensajeResponseRest> createCategory(CategoryDTO categoryDTO);
    ResponseEntity<MensajeResponseRest> editarCategory(CategoryDTO categoryDTO);
    ResponseEntity<MensajeResponseRest> eliminarCategory(String nameCategory);
}
