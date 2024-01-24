package com.company.inventory.controller;

import com.company.inventory.dto.ProductoDTO;
import com.company.inventory.response.MensajeResponseRest;
import com.company.inventory.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/producto")
@CrossOrigin(origins = "http://localhost:3000/")
@Tag(name = "Inventario - Productos", description = "crud - de productos")
public class InvetoryProducto {

    private final ProductoService producto;
    @Autowired
    public InvetoryProducto(ProductoService producto) {
        this.producto = producto;
    }

    @Operation(summary = "Obtener productos por categoria", description = "Devuelve una lista de productos de una categoria")
    @GetMapping("productos/{nameCategory}")
    public ResponseEntity<MensajeResponseRest> listForCategory(@PathVariable String nameCategory) {
        return new ResponseEntity<>(producto.listForCategory(nameCategory), HttpStatus.OK).getBody();
    }
    @Operation(summary = "Crear productos por categoria", description = "Crea un producto en una categoria")
    @PostMapping("create-producto")
    public ResponseEntity<MensajeResponseRest> createProduct(@RequestBody ProductoDTO productoDTO) {
        return new ResponseEntity<>(producto.createProduct(productoDTO), HttpStatus.CREATED).getBody();
    }
    @Operation(summary = "Otener todos los productos", description = "Devuelve una lista con todos los productos")
    @GetMapping("productos/listado")
    public ResponseEntity<MensajeResponseRest> listFindAllProductos(){
        return new ResponseEntity<>(producto.listFindAllProductos(), HttpStatus.OK).getBody();
    }

    @GetMapping("buscar-productos/list/{nameProducto}")
    public ResponseEntity<MensajeResponseRest> findByNombreList(@PathVariable String nameProducto){
        return new ResponseEntity<>(producto.findByNombreList(nameProducto), HttpStatus.OK);
    }

    @Operation(summary = "Bucar producto", description = "Devuelve un producto idicado en la categoria")
    @GetMapping("buscar-productos/{nameCategory}/{nameProducto}")
    public ResponseEntity<MensajeResponseRest> buscarProductoInCategory(@PathVariable String nameCategory, @PathVariable String nameProducto){
        return new ResponseEntity<>(producto.buscarProductoInCategory(nameCategory, nameProducto), HttpStatus.OK).getBody();
    }
    @Operation(summary = "Actualizar producto", description = "Devuelve los datos actualizados del producto")
    @PutMapping("actualizar-producto")
    public ResponseEntity<MensajeResponseRest> editarProducto(@RequestBody ProductoDTO productoDTO){
        return new ResponseEntity<>(producto.editarProducto(productoDTO), HttpStatus.OK).getBody();
    }

    @Operation(summary = "Eliminar producto", description = "Elimina un producto, segun el ID ingresado")
    @DeleteMapping("eliminar-producto/{productoId}")
    public ResponseEntity<MensajeResponseRest> eliminarProducto(@PathVariable Long productoId) {
        return new ResponseEntity<>(producto.eliminarProducto(productoId), HttpStatus.OK).getBody();
    }
}
