package com.company.inventory.dto.login;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HistorialComprasGinDTO {
    private String idUser;
    private Long idProducto;
    private Integer cantidad;
}
