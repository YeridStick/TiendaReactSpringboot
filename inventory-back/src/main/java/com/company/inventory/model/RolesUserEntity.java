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
@Table(name = "ROLESUSER")
public class RolesUserEntity {
    @Id
    @Column(name = "IDROLL")
    private String idRoll;

    @Column(name = "CARGO")
    private String cargo;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @PrePersist
    public void generateId() {
        String randomPart = Long.toHexString(Double.doubleToLongBits(Math.random()));
        String timeStampPart = Long.toHexString(System.currentTimeMillis());

        this.idRoll = (randomPart + timeStampPart).substring(0, 8);
    }
}
