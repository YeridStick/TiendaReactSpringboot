package com.company.inventory.controller;

import com.company.inventory.dto.login.UserGinDTO;
import com.company.inventory.response.MensajeResponseRest;
import com.company.inventory.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000/")
@Tag(name = "Usuario")
public class UserController {
    private final UsuarioService usuarioService;
    private final HttpSession httpSession;

    public UserController(UsuarioService usuarioService, HttpSession httpSession) {
        this.usuarioService = usuarioService;
        this.httpSession = httpSession;
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

    @PostMapping("login")
    @Operation(summary = "Ingreso de usuario")
    public MensajeResponseRest Userloging(@RequestBody UserGinDTO userGinDTO){
        return new ResponseEntity<>(usuarioService.Userloging(userGinDTO), HttpStatus.OK).getBody();
    }

    @GetMapping("logout")
    @Operation(summary = "Salida de usuario")
    public String logout() {
        httpSession.invalidate();
        return "redirect:/";
    }
}
