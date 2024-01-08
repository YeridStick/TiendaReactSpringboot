package com.company.inventory.controller;

import com.company.inventory.dto.login.RolesGinDTO;
import com.company.inventory.response.MensajeResponseRest;
import com.company.inventory.service.RollService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "http://localhost:3000/")
@Tag(name = "Roles")
public class RolController {
    private final RollService rollService;

    public RolController(RollService rollService) {
        this.rollService = rollService;
    }

    @PostMapping("crearRol")
    @Operation(summary = "Crear un Rol")
    public ResponseEntity<MensajeResponseRest> createRoll(@RequestBody RolesGinDTO rolesGinDTO){
        return new ResponseEntity<>(rollService.createRoll(rolesGinDTO), HttpStatus.OK);
    }

    @GetMapping("listar-roles")
    @Operation(summary = "Lista los roles")
    public ResponseEntity<MensajeResponseRest> listarRoles(){
        return  new ResponseEntity<>(rollService.listarRoles(), HttpStatus.OK);
    }
}
