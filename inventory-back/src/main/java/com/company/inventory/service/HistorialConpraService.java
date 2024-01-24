package com.company.inventory.service;

import com.company.inventory.dto.login.HistorialComprasGinDTO;
import com.company.inventory.response.MensajeResponseRest;

public interface HistorialConpraService {
    MensajeResponseRest saveProducto(HistorialComprasGinDTO historialComprasGinDTO);
    MensajeResponseRest listProduto(String idUser);
}
