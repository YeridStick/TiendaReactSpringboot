package com.company.inventory.controller;

import com.company.inventory.model.TipoEntidadEntity;
import com.company.inventory.response.MensajeResponseRest;
import com.company.inventory.service.TipoEntidadServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tipo-entidad")
@Tag(name = "Tipo Documento Entidad")
public class TipoEntidadController {
    private final TipoEntidadServices tipoEntidadServices;

    public TipoEntidadController(TipoEntidadServices tipoEntidadServices) {
        this.tipoEntidadServices = tipoEntidadServices;
    }

    @PostMapping("agregar")
    @Operation(summary = "Crear un tipo de entidad")
    public ResponseEntity<MensajeResponseRest> crearTipoEntidad(@RequestBody TipoEntidadEntity tipoEntidad){
        return new ResponseEntity<>(tipoEntidadServices.crearTipoEntidad(tipoEntidad), HttpStatus.OK);
    }

    @GetMapping("listar")
    @Operation(summary = "Obtener listado de tipo de entidades", description = "Devuelve una lista de entidades")
    public ResponseEntity<MensajeResponseRest> listEntidad(){
        return new ResponseEntity<>(tipoEntidadServices.listEntidad(), HttpStatus.OK);
    }
}
