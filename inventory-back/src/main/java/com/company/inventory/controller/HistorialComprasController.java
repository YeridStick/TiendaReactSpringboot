package com.company.inventory.controller;

import com.company.inventory.dto.login.HistorialComprasGinDTO;
import com.company.inventory.response.MensajeResponseRest;
import com.company.inventory.service.HistorialConpraService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/historial")
@CrossOrigin(origins = "http://localhost:3000/")
@Tag(name = "Carrito de Compras", description = "Historial de compras")
public class HistorialComprasController {
    private final HistorialConpraService historilService;

    public HistorialComprasController(HistorialConpraService historilService) {
        this.historilService = historilService;
    }

    @PostMapping("save")
    public ResponseEntity<MensajeResponseRest> saveHistorial(@RequestBody HistorialComprasGinDTO historialComprasGinDTO){
        return new ResponseEntity<>(historilService.saveProducto(historialComprasGinDTO), HttpStatus.OK);
    }

    @GetMapping("list/{idUser}")
    public ResponseEntity<MensajeResponseRest> listProduto(@PathVariable String idUser){
        return new ResponseEntity<>(historilService.listProduto(idUser), HttpStatus.OK);
    }
}
