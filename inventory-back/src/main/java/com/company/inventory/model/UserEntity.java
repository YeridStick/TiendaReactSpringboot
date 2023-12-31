package com.company.inventory.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER")
public class UserEntity {
    @Id
    @Column(name = "NUMEROIDENTIFICACION")
    private String numIdent;

    @Column(name = "NOMBRE")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "TIPOIDENTIDAD")
    private TipoEntidadEntity tipoEntidad;

    @ManyToOne
    @JoinColumn(name = "TIPOROLL")
    private RolesUserEntity rolesUser;
}
