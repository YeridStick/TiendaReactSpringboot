package com.company.inventory.service;

import com.company.inventory.dto.login.UserGinDTO;
import com.company.inventory.response.MensajeResponseRest;

public interface UsuarioService {
    MensajeResponseRest crearUsuario(UserGinDTO userGinDTO);
    MensajeResponseRest listarUsuarios();

}
