package com.company.inventory.service;

import com.company.inventory.dto.login.RolesGinDTO;
import com.company.inventory.response.MensajeResponseRest;

public interface RollService {
    MensajeResponseRest createRoll(RolesGinDTO rolesGinDTO);
    MensajeResponseRest listarRoles();
}
