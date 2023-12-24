package com.company.inventory.controller;

import com.company.inventory.service.CategoryService;
import com.company.inventory.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/producto")
public class InvetoryProducto {
    @Autowired
    private ProductoService producto;

    @Autowired
    private CategoryService category;


}
