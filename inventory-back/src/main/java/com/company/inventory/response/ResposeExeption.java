package com.company.inventory.response;

import com.company.inventory.exception.ExcepcionPersonalizada;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ResposeExeption {
    @ExceptionHandler(ExcepcionPersonalizada.class)
    public ResponseEntity<Map<String, String>> manejoExepcion(ExcepcionPersonalizada e) {
        Map<String, String > response = new HashMap<>();

        response.put("Respuesta", e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(response);
    }
}
