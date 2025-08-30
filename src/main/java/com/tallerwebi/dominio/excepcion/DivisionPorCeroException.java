package com.tallerwebi.dominio.excepcion;

public class DivisionPorCeroException extends RuntimeException {
    public DivisionPorCeroException(String mensaje) {
        super(mensaje);
    }
}
