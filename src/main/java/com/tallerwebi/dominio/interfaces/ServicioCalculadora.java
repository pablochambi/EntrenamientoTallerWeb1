package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.excepcion.DivisionPorCeroException;
import com.tallerwebi.dominio.excepcion.CampoNuloException;

public interface ServicioCalculadora {
    Double calcular(Double num1, Double num2, String operacion) throws CampoNuloException, DivisionPorCeroException;
}
