package com.company.inventory.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CATEGORY")
public class CategoryEntity implements Serializable {
    //private static final long serialVersionUID = 1L;

    @Column(name = "ID")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Long id;
    @Column(name = "NAME", unique = true)
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;

    /*@OneToMany(mappedBy = "category")
    private List<ProductoEntity> productos;*/
}
