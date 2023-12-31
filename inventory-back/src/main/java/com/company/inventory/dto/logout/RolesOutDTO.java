package com.company.inventory.dto.logout;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RolesOutDTO {
    private String idRoll;
    private String cargo;
    private String descripcion;
}
