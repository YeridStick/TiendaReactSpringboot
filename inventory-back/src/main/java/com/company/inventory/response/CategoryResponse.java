package com.company.inventory.response;

import com.company.inventory.dto.CategoryDTO;
import com.company.inventory.model.CategoryEntity;
import com.company.inventory.model.ProductoEntity;
import lombok.Data;

import java.util.List;

@Data
public class CategoryResponse {
    private List<CategoryEntity> categoryEntity;
    private List<CategoryDTO> categoryDTOS;
    private List<ProductoEntity> productoEntity;
    private String categoryName;
}
