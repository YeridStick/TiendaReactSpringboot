package com.company.inventory.dto.logout;

import com.company.inventory.model.RolesUserEntity;
import com.company.inventory.model.TipoEntidadEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserOutDTO {

    private String numIdent;

    private String nombre;

    private String correo;

    private Long numeroTelefono;

    private TipoEntidadEntity tipoEntidad;

    private String cargoRol;
}
