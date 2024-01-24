package com.company.inventory.service.impl;

import com.company.inventory.constantes.Constantes;
import com.company.inventory.dto.login.HistorialComprasGinDTO;
import com.company.inventory.exception.ExcepcionPersonalizada;
import com.company.inventory.model.HistorialCompras;
import com.company.inventory.model.ProductoEntity;
import com.company.inventory.model.UserEntity;
import com.company.inventory.repository.HistorialComprasRepository;
import com.company.inventory.repository.ProductoRepository;
import com.company.inventory.repository.UserRepository;
import com.company.inventory.response.MensajeResponseRest;
import com.company.inventory.service.HistorialConpraService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.cert.Extension;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HistorialComprasServiceImpl implements HistorialConpraService {
    private final HistorialComprasRepository hitorial;
    private final UserRepository user;
    private final ProductoRepository productoRepository;

    public HistorialComprasServiceImpl(HistorialComprasRepository hitorial, UserRepository user, ProductoRepository productoRepository) {
        this.hitorial = hitorial;
        this.user = user;
        this.productoRepository = productoRepository;
    }
    @Override
    public MensajeResponseRest saveProducto(HistorialComprasGinDTO historialComprasGinDTO){
        MensajeResponseRest mensaje = new MensajeResponseRest();
        try {
            Optional<UserEntity> userObjeto = user.findById(historialComprasGinDTO.getIdUser()) ;
            Optional<ProductoEntity> productoObjeto = productoRepository.findById(historialComprasGinDTO.getIdProducto());

            HistorialCompras historialCompra = new HistorialCompras();
            historialCompra.setUsuario(userObjeto.get());
            historialCompra.setProducto(productoObjeto.get());
            if (productoObjeto.get().getCantidad() < historialComprasGinDTO.getCantidad() ){
                mensaje.setMetadata(Constantes.TextRespuestaNo, "Cantidad de producto no disponible");
                return mensaje;
            }
            Integer cantidadRestante = productoObjeto.get().getCantidad() - historialComprasGinDTO.getCantidad();

            productoObjeto.get().setCantidad(cantidadRestante);
            historialCompra.setCantidad( historialComprasGinDTO.getCantidad());
            historialCompra.setFechaCompra(LocalDateTime.now());

            productoRepository.save(productoObjeto.get());
            hitorial.save(historialCompra);
            mensaje.getMensajeResponse().setHistorialCompra(historialCompra);
            mensaje.setMetadata(Constantes.TextRespuesta, "Producto Guardado Correctamente");
            return mensaje;
        } catch (Exception e) {
            throw new ExcepcionPersonalizada("Error al guardar producto", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public MensajeResponseRest listProduto(String idUser) {
        MensajeResponseRest mensaje = new MensajeResponseRest();
        try{
            List<HistorialCompras> historialOpjeto = hitorial.findByUser(idUser);
            if (historialOpjeto.isEmpty()) {
                mensaje.setMetadata(Constantes.TextRespuestaNo, "Usuario No Existe");
                return mensaje;
            }
            mensaje.getMensajeResponse().setHistorialCompras(historialOpjeto);
            mensaje.setMetadata(Constantes.TextRespuesta, "Producto Guardado Correctamente");
            return mensaje;
        } catch (Exception e) {
            throw new ExcepcionPersonalizada("Error al guardar producto", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
