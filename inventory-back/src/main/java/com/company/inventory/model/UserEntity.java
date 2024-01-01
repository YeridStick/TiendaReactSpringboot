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

    @Column(name = "CORREO", unique = true)
    private String correo;

    @Column(name = "TELEFONO", unique = true)
    private Long numeroTelefono;

    @Column(name = "PASSWORD")
    private String password;

    @ManyToOne
    @JoinColumn(name = "TIPOIDENTIDAD")
    private TipoEntidadEntity tipoEntidad;

    @ManyToOne
    @JoinColumn(name = "TIPOROLL")
    private RolesUserEntity rolesUser;
}
