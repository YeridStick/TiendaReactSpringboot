package com.company.inventory.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class HistorialCompras {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USUARIO_ID")
    private UserEntity usuario;

    @ManyToOne
    @JoinColumn(name = "PRODUCTO_ID")
    private ProductoEntity producto;

    @Column(name = "FECHA_COMPRA")
    private LocalDateTime fechaCompra;

    @Column(name = "CANTIDAD")
    private Integer cantidad;
}
