package com.tallerwebi.dominio.Implementaciones;

import com.tallerwebi.dominio.excepcion.CampoNuloException;
import com.tallerwebi.dominio.excepcion.DivisionPorCeroException;
import com.tallerwebi.dominio.interfaces.ServicioCalculadora;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioCalculadoraImpl implements ServicioCalculadora {

    @Override
    public Double calcular(Double num1, Double num2, String operacion)
            throws CampoNuloException, DivisionPorCeroException {

        validarCampos(num1, num2, operacion);
        return realizarCalculo(num1, num2, operacion);
    }

    private void validarCampos(Double n1, Double n2, String operacion) throws CampoNuloException {
        List<String> errores = new ArrayList<>();

        if (n1 == null) errores.add("El primer número es requerido.");
        if (n2 == null) errores.add("El segundo número es requerido.");
        if (operacion == null || operacion.trim().isEmpty()) errores.add("Seleccione una operacion.");


        if (!errores.isEmpty()) {
            throw new CampoNuloException(String.join("\n", errores));
        }
    }

    private Double realizarCalculo(Double num1, Double num2, String operacion) throws DivisionPorCeroException {
        switch (operacion.toLowerCase()) {
            case "sumar": return num1 + num2;
            case "restar": return num1 - num2;
            case "multiplicar": return num1 * num2;
            case "dividir":
                if (num2 == 0) throw new DivisionPorCeroException("No se puede dividir por cero.");
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Operación no válida: " + operacion);
        }
    }



}
