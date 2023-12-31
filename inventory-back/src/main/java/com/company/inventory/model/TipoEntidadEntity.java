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
@Table(name = "TIPO_IDENTIDAD")
public class TipoEntidadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDENTIDAD")
    private Long idEntidad;

    @Column(name = "SIGLAS", unique = true)
    private String sigla;

    @Column(name = "NOMBRE")
    private String nombreEntidad;
}
