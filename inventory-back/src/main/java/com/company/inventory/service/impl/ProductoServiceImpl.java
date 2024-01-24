package com.company.inventory.service.impl;

import com.company.inventory.constantes.Constantes;
import com.company.inventory.dto.ProductoDTO;
import com.company.inventory.exception.ExcepcionPersonalizada;
import com.company.inventory.model.CategoryEntity;
import com.company.inventory.model.ProductoEntity;
import com.company.inventory.model.RolesUserEntity;
import com.company.inventory.repository.CategoryRepository;
import com.company.inventory.repository.ProductoRepository;
import com.company.inventory.response.MensajeResponseRest;
import com.company.inventory.service.ProductoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductoServiceImpl implements ProductoService {
    private final ProductoRepository productRepository;
    private final CategoryRepository categoryRepository;
    private  final ModelMapper modelMapper;
    @Autowired
    public ProductoServiceImpl(ProductoRepository productRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public ResponseEntity<MensajeResponseRest> createProduct(ProductoDTO productoDTO) {
        String errorMessage = "Ya existe un producto con el nombre '" + productoDTO.getNombre() + "' en la categoría '" + productoDTO.getNameCategory() + "'";
        MensajeResponseRest response = new MensajeResponseRest();

        try {
            CategoryEntity categoryEntity = categoryRepository.findByName(productoDTO.getNameCategory())
                    .orElseThrow(() -> new ExcepcionPersonalizada("La categoría '" + productoDTO.getNameCategory() + "' no existe", HttpStatus.NOT_FOUND));

            ProductoEntity existingProducts = productRepository.findByNombreCategoryEndProducto(categoryEntity.getId(), productoDTO.getNombre());

            if (existingProducts != null) {
                response.setMetadata(errorMessage, "Error al ingresar: " + productoDTO.getNombre());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            ProductoEntity product = new ProductoEntity();
            product.setNombre(productoDTO.getNombre());
            product.setDescripcion(productoDTO.getDescripcion());
            product.setUrlImg(productoDTO.getUrlImg());
            product.setPrice(productoDTO.getPrice());
            product.setEsatdo(productoDTO.getEstado());
            if (productoDTO.getCantidad() < 0) {
                response.setMetadata("Cantidad de producto no valida " + productoDTO.getNameCategory(), Constantes.TextRespuesta);
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            product.setCantidad(productoDTO.getCantidad());
            product.setCategory(categoryEntity);

            productRepository.save(product);

            response.setMetadata("Producto creado exitosamente en la categoría: " + productoDTO.getNameCategory(), Constantes.TextRespuesta);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ExcepcionPersonalizada(Constantes.TextRespuestaNo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<MensajeResponseRest> listForCategory(String categoryName) {
        MensajeResponseRest response = new MensajeResponseRest();
        try {
            Optional<CategoryEntity> categoryEntity = categoryRepository.findByName(categoryName);

            if (categoryEntity.isPresent()) {
                CategoryEntity category = categoryEntity.get();

                List<ProductoEntity> producto = productRepository.findByNombreCategory(category.getId());

                response.getMensajeResponse().setCategoryName(category.getName());
                response.getMensajeResponse().setProductoEntity(producto);

                response.setMetadata(Constantes.TextRespuesta,Constantes.ProductosRecuperdaosCorrectamente);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setMetadata(Constantes.RespuestaNoExitosa,Constantes.CategoriaNoEncontrada);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new ExcepcionPersonalizada(Constantes.RespuestaNoExitosa, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public  ResponseEntity<MensajeResponseRest> listFindAllProductos() {
        MensajeResponseRest response = new MensajeResponseRest();
        try{
            List<ProductoEntity> productos = productRepository.findAll();

            response.getMensajeResponse().setProductoEntity(productos);
            response.setMetadata(Constantes.TextRespuesta,Constantes.ProductosRecuperdaosCorrectamente);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new ExcepcionPersonalizada(Constantes.RespuestaNoExitosa, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public MensajeResponseRest findByNombreList(String nombreProducto){
        MensajeResponseRest mensaje = new MensajeResponseRest();
        try {
            List<ProductoEntity> productoEntityList = productRepository.findByNombreList(nombreProducto);

            mensaje.getMensajeResponse().setProductoEntity(productoEntityList);
            mensaje.setMetadata(Constantes.TextRespuesta,Constantes.ProductosRecuperdaosCorrectamente);
            return new ResponseEntity<>(mensaje, HttpStatus.OK).getBody();
        } catch (Exception e) {
            throw new ExcepcionPersonalizada(Constantes.RespuestaNoExitosa, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<MensajeResponseRest> buscarProductoInCategory(String nameCategory, String nameProducto) {
        MensajeResponseRest response = new MensajeResponseRest();
        try {
            CategoryEntity category = categoryRepository.findByName(nameCategory)
                    .orElseThrow(() -> new ExcepcionPersonalizada("No existe la categoria: " + nameCategory, HttpStatus.NOT_FOUND));

            ModelMapper modelMapper = new ModelMapper();
            ProductoEntity producto =  productRepository.findByNombreCategoryEndProducto(category.getId(), nameProducto);

            ProductoDTO productoDTO = modelMapper.map(producto, ProductoDTO.class);

            response.getMensajeResponse().setProductoDTO(productoDTO);
            response.setMetadata(Constantes.TextRespuesta,Constantes.ProductosRecuperdaosCorrectamente);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch ( Exception e) {
            throw new ExcepcionPersonalizada(Constantes.RespuestaNoExitosa, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public ResponseEntity<MensajeResponseRest>  editarProducto(ProductoDTO productoActualizadoDTO) {
        MensajeResponseRest response = new MensajeResponseRest();
        try {
            CategoryEntity category =
                categoryRepository.findByName(productoActualizadoDTO.getNameCategory())
                        .orElseThrow(() -> new ExcepcionPersonalizada("Categoria no encontrada: " + productoActualizadoDTO.getNameCategory(), HttpStatus.NOT_FOUND));

            Optional<ProductoEntity> producto = Optional.ofNullable(productRepository.findByNombreCategoryEndProducto(category.getId(), productoActualizadoDTO.getNombre()));

            if (producto.isPresent()) {
                ProductoEntity productoExistente = productRepository.findById(producto.get().getId())
                        .orElseThrow(() -> new ExcepcionPersonalizada("No se encontró el producto con ID: " + productoActualizadoDTO.getId(), HttpStatus.NOT_FOUND));

                ModelMapper modelMapper = new ModelMapper();
                modelMapper.map(productoActualizadoDTO, productoExistente);

                productRepository.save(productoExistente);

            } else {
                response.setMetadata(Constantes.RespuestaNoExitosa, "Productos no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            response.getMensajeResponse().setProductoDTO(productoActualizadoDTO);
            response.setMetadata(Constantes.TextRespuesta, "Producto actualizado de la categoría: " + productoActualizadoDTO.getNameCategory());
            return new ResponseEntity<>(response, HttpStatus.OK);


        } catch (Exception e) {
            throw new ExcepcionPersonalizada(Constantes.TextRespuestaNo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<MensajeResponseRest>  eliminarProducto(Long productoId) {
        MensajeResponseRest response = new MensajeResponseRest();
        try{
            ProductoEntity productoExistente = productRepository.findById(productoId)
                    .orElseThrow(() -> new ExcepcionPersonalizada("No se encontró el producto con ID: " + productoId, HttpStatus.NOT_FOUND));

            productRepository.delete(productoExistente);

            response.setMetadata(Constantes.TextRespuesta,Constantes.ProductoEliminadoCorrectamente);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            throw new ExcepcionPersonalizada(Constantes.TextRespuestaNo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}