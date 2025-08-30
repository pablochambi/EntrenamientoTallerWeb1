package com.tallerwebi.dominio.excepcion;

public class CampoNuloException extends RuntimeException {
    public CampoNuloException(String mensaje) {
        super(mensaje);
    }
}
