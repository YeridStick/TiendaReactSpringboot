package com.company.inventory.service;

import com.company.inventory.dto.ProductoDTO;
import com.company.inventory.response.MensajeResponseRest;
import org.springframework.http.ResponseEntity;

public interface ProductoService {
    ResponseEntity<MensajeResponseRest> createProduct(ProductoDTO productoDTO);
    ResponseEntity<MensajeResponseRest> listForCategory(String categoryName);
    ResponseEntity<MensajeResponseRest> buscarProductoInCategory(String nameCategory, String nameProducto);
    ResponseEntity<MensajeResponseRest>  editarProducto(ProductoDTO productoActualizadoDTO);
    ResponseEntity<MensajeResponseRest>  eliminarProducto(Long productoId);
    MensajeResponseRest findByNombreList(String nombreProducto);
    ResponseEntity<MensajeResponseRest> listFindAllProductos();
}
