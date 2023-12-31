package com.company.inventory.service.impl;

import com.company.inventory.constantes.Constantes;
import com.company.inventory.exception.ExcepcionPersonalizada;
import com.company.inventory.model.TipoEntidadEntity;
import com.company.inventory.repository.TipoEntidadRepository;
import com.company.inventory.response.MensajeResponseRest;
import com.company.inventory.service.TipoEntidadServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoEntidadServicesImpl implements TipoEntidadServices {
    private final TipoEntidadRepository tipoEntidad;

    public TipoEntidadServicesImpl(TipoEntidadRepository tipoEntidad) {
        this.tipoEntidad = tipoEntidad;
    }
    @Override
    public MensajeResponseRest crearTipoEntidad(TipoEntidadEntity tipoEntidadEntity) {
        MensajeResponseRest response = new MensajeResponseRest();
        try {
            Optional<TipoEntidadEntity> objetoEnitdad = tipoEntidad.findBySigla(tipoEntidadEntity.getSigla());

            if (objetoEnitdad.isPresent() ) {
                response.setMetadata("El tipo de entidad " + tipoEntidadEntity.getNombreEntidad()  + " Ya Existe","Error al ingresar los datos");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR).getBody();
            }

            tipoEntidad.save(tipoEntidadEntity);

            response.setMetadata(Constantes.TextRespuesta, "Tipo documento identidad creado Correctamenete");
            return response;
        }
        catch (Exception e) {
            throw new ExcepcionPersonalizada(Constantes.TextRespuestaNo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public MensajeResponseRest listEntidad() {
        MensajeResponseRest response = new MensajeResponseRest();
        try {
            List<TipoEntidadEntity> tipoEntidadEntityList = tipoEntidad.findAll();

            response.getMensajeResponse().setTipoEntidad(tipoEntidadEntityList);
            response.setMetadata(Constantes.TextRespuesta, "Datos recuperados Correctamenete");
            return response;
        }catch (Exception e) {
            throw new ExcepcionPersonalizada(Constantes.TextRespuestaNo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
