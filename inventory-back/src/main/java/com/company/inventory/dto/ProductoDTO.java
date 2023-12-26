package com.company.inventory.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductoDTO {

    private Long id;

    private String nombre;

    private String descripcion;

    private String urlImg;

    private Long Price;

    private String nameCategory;
}
