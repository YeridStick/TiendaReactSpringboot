package com.company.inventory.controller;

import com.company.inventory.dto.ProductoDTO;
import com.company.inventory.model.ProductoEntity;
import com.company.inventory.response.CategoryResponseRest;
import com.company.inventory.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/producto")
public class InvetoryProducto {
    @Autowired
    private ProductoService producto;

    @GetMapping("productos/{nameCategory}")
    public ResponseEntity<CategoryResponseRest> listForCategory(@PathVariable String nameCategory) {
        ResponseEntity<CategoryResponseRest> response = producto.listForCategory(nameCategory);
        return response;
    }

    @PostMapping("create-producto")
    public ResponseEntity<CategoryResponseRest> createProduct(@RequestBody ProductoDTO productoDTO) {
        ResponseEntity<CategoryResponseRest> response = producto.createProduct(productoDTO);
        return response;
    }

    @GetMapping("productos/listado")
    public ResponseEntity<CategoryResponseRest> listFindAllProductos(){
        ResponseEntity<CategoryResponseRest> response = producto.listFindAllProductos();
        return response;
    }

}
