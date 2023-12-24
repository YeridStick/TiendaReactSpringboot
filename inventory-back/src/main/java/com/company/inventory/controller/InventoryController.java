package com.company.inventory.controller;

import com.company.inventory.dto.CategoryDTO;
import com.company.inventory.model.CategoryEntity;
import com.company.inventory.response.CategoryResponseRest;
import com.company.inventory.service.CategoryService;
import com.company.inventory.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
//@CrossOrigin(origins = "http://localhost:4200/")
public class InventoryController {
    @Autowired
    private CategoryService category;

    //Listar todas las categorias
    @GetMapping("category")
    public ResponseEntity<CategoryResponseRest> getAllCategories(){
        ResponseEntity<CategoryResponseRest> response = category.getAllCategories();
        return response;
    }

    //Categoria por nombre
    @GetMapping("category/{nameCategory}")
    public ResponseEntity<CategoryResponseRest> getCategoryByNameCategory(@PathVariable String nameCategory){
        ResponseEntity<CategoryResponseRest> response = category.getCategoryByNameCategory(nameCategory);
        return response;
    }

    //Crear categoria
    @PostMapping("new-category")
    public ResponseEntity<CategoryResponseRest>  createCategory(@RequestBody CategoryDTO categoryDTO) {
        ResponseEntity<CategoryResponseRest>  response = category.createCategory(categoryDTO);
        return response;
    }

    //Editar categoria
    @PutMapping("update-category")
    public ResponseEntity<CategoryResponseRest> editarCategory(@RequestBody CategoryDTO categoryDTO) {
        ResponseEntity<CategoryResponseRest>  response = category.editarCategory(categoryDTO);
        return  response;
    }

    //Eliminar categoria
    @DeleteMapping("delete-category/{nameCategory}")
    public ResponseEntity<CategoryResponseRest> eliminarCategory(@PathVariable String nameCategory) {
        ResponseEntity<CategoryResponseRest>  response = category.eliminarCategory(nameCategory);
        return response;
    }

}
