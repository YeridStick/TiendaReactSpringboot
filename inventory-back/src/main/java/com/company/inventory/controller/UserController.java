package com.company.inventory.controller;

import com.company.inventory.dto.login.UserGinDTO;
import com.company.inventory.response.MensajeResponseRest;
import com.company.inventory.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "Usuario")
public class UserController {
    private final UsuarioService usuarioService;

    public UserController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("agregar")
    @Operation(summary = "Crear un nuevo usuario")
    public ResponseEntity<MensajeResponseRest> crearUsuario(@RequestBody UserGinDTO userGinDTO){
        return new ResponseEntity<>(usuarioService.crearUsuario(userGinDTO), HttpStatus.OK);
    }

    @GetMapping("user/listar")
    @Operation(summary = "Obtener listado de Usuarios", description = "Devuelve una lista de usuarios registrados")
    public ResponseEntity<MensajeResponseRest> listarUsuarios(){
        return new ResponseEntity<>(usuarioService.listarUsuarios(), HttpStatus.OK);
    }

    @GetMapping("login/{correo}/{password}")
    @Operation(summary = "ingreso de usuarios")
    public MensajeResponseRest Userloging(@PathVariable String correo, @PathVariable String password){
        return new ResponseEntity<>(usuarioService.Userloging(correo, password), HttpStatus.OK).getBody();
    }
}
