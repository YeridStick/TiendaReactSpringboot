package com.company.inventory.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRODUCTO")
public class ProductoEntity implements Serializable {
    //private static final long serialVersionUID = 1L;

    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private long id;

    @Column(name = "NOMBRE", unique = true)
    private String nombre;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "URLIMG")
    private String urlImg;

    @Column(name = "PRICE")
    private Long Price;

    @Column(name = "ESTADO")
    private Boolean Esatdo;

    @Column(name = "CATIDAD")
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "CATEGORIA_ID")
    private CategoryEntity category ;
}
