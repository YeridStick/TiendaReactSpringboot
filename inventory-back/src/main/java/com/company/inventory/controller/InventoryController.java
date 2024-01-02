package com.company.inventory.controller;

import com.company.inventory.dto.CategoryDTO;
import com.company.inventory.response.MensajeResponseRest;
import com.company.inventory.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
//@CrossOrigin(origins = "http://localhost:4200/")
@Tag(name = "Inventario - Categoria", description = "crud - de categorias")
public class InventoryController {

    private final CategoryService category;
    @Autowired
    public InventoryController(CategoryService category) {
        this.category = category;
    }

    @Operation(summary = "Obtener Categorias", description = "Devuelve una lista de todos las categorias")
    @GetMapping("category")
    public ResponseEntity<MensajeResponseRest> getAllCategories(){
        return new ResponseEntity<>(category.getAllCategories(), HttpStatus.OK);
    }

    @Operation(summary = "Buscar Categoria", description = "Devuelve una categoria segun el nombre")
    @GetMapping("category/{nameCategory}")
    public MensajeResponseRest getCategoryByNameCategory(@PathVariable String nameCategory){
        return new ResponseEntity<>(category.getCategoryByNameCategory(nameCategory), HttpStatus.OK).getBody();
    }

    @Operation(summary = "private/Crear Categoria", description = "crea una nueva categoria")
    @PostMapping("new-category")
    public ResponseEntity<MensajeResponseRest>  createCategory(@RequestBody CategoryDTO categoryDTO) {
        return new ResponseEntity<>(category.createCategory(categoryDTO), HttpStatus.CREATED).getBody();
    }

    @Operation(summary = "Editar Categoria", description = "actualiza la descricion de la categoria")
    @PutMapping("update-category")
    public ResponseEntity<MensajeResponseRest> editarCategory(@RequestBody CategoryDTO categoryDTO) {
        return new ResponseEntity<>(category.editarCategory(categoryDTO), HttpStatus.OK).getBody();
    }

    @Operation(summary = "Eliminar Categoria", description = "Elimina la categoria")
    @DeleteMapping("delete-category/{nameCategory}")
    public ResponseEntity<MensajeResponseRest> eliminarCategory(@PathVariable String nameCategory) {
        return new ResponseEntity<>(category.eliminarCategory(nameCategory), HttpStatus.OK).getBody();
    }
}
