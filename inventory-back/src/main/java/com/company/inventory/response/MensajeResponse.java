package com.company.inventory.response;

import com.company.inventory.dto.CategoryDTO;
import com.company.inventory.dto.ProductoDTO;
import com.company.inventory.dto.logout.RolesOutDTO;
import com.company.inventory.dto.logout.UserOutDTO;
import com.company.inventory.model.*;
import lombok.Data;

import java.util.List;

@Data
public class MensajeResponse {
    private List<CategoryDTO> categoryDTOS;
    private List<ProductoEntity> productoEntity;
    private ProductoDTO productoDTO;
    private String categoryName;
    private List<TipoEntidadEntity> tipoEntidad;
    private UserEntity user;
    private HistorialCompras historialCompra;
    private List<HistorialCompras> historialCompras;
    private List<UserOutDTO> users;
    private List<RolesOutDTO> rolesUser;
}
