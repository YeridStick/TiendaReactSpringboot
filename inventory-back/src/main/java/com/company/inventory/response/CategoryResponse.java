package com.company.inventory.response;

import com.company.inventory.model.CategoryEntity;
import lombok.Data;

import java.util.List;

@Data
public class CategoryResponse {
    private List<CategoryEntity> categoryEntity;
}
