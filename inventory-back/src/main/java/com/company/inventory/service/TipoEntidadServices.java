package com.company.inventory.service;

import com.company.inventory.model.TipoEntidadEntity;
import com.company.inventory.response.MensajeResponseRest;

public interface TipoEntidadServices {
    MensajeResponseRest crearTipoEntidad(TipoEntidadEntity tipoEntidadEntity);
    MensajeResponseRest listEntidad();
}
