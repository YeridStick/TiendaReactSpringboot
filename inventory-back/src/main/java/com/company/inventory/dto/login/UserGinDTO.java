package com.company.inventory.dto.login;

import com.company.inventory.model.RolesUserEntity;
import com.company.inventory.model.TipoEntidadEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserGinDTO {
    private String numIdent;

    private String nombre;

    private String password;

    private String correo;

    private Long numeroTelefono;

    private String tipoEntidad;

    //private RolesUserEntity rolesUser;
}
