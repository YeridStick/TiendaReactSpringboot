package com.company.inventory.exception;

import org.springframework.http.HttpStatus;

public class ExcepcionPersonalizada extends RuntimeException {
    private final HttpStatus httpStatus;

    public ExcepcionPersonalizada(String mensaje, HttpStatus httpStatus) {
        super(mensaje);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
