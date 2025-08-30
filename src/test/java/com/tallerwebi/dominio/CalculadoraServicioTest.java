package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Implementaciones.ServicioCalculadoraImpl;
import com.tallerwebi.dominio.excepcion.CampoNuloException;
import com.tallerwebi.dominio.excepcion.DivisionPorCeroException;
import com.tallerwebi.dominio.interfaces.ServicioCalculadora;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class CalculadoraServicioTest {

    private ServicioCalculadora servicioCalculadora;

    @BeforeEach
    public void preparacion() {
        servicioCalculadora = new ServicioCalculadoraImpl();
    }

    // Tests para validarNumeros()
    @Test
    public void queAlValidarNumerosConAmbosNumerosNoNulos_NoLanceExcepcion() {
        assertDoesNotThrow(() -> servicioCalculadora.calcular(5.0, 3.0,"sumar"));
    }

    @Test
    public void queAlValidarNumerosConPrimerNumeroNulo_LanceExcepcion() {

        Exception exception = assertThrows(CampoNuloException.class, () -> {
                                    servicioCalculadora.calcular(null,3.0,"sumar");
                                });
        assertThat(exception.getMessage(), is("El primer número es requerido."));
    }

    @Test
    public void queAlValidarNumerosConSegundoNumeroNulo_LanceExcepcion() {
        Exception exception = assertThrows(CampoNuloException.class, () -> {
                                    servicioCalculadora.calcular(5.0, null,"sumar");
                                });
        assertThat(exception.getMessage(), is("El segundo número es requerido."));
    }

    @Test
    public void queAlValidarNumerosConPrimerYSegundoNumeroNulo_LanceExcepcion() {
        Exception exception = assertThrows(CampoNuloException.class, () -> {
            servicioCalculadora.calcular(null, null,"sumar");
        });

        String mensaje = exception.getMessage();
        assertTrue(mensaje.contains("El primer número es requerido"));
        assertTrue(mensaje.contains("El segundo número es requerido"));
    }


    //Test para validar numeros y operacion
    @Test
    public void queAlValidarNumerosYOperacionNula_LanceExcepcion() {
        Exception exception = assertThrows(CampoNuloException.class, () -> {
            servicioCalculadora.calcular(null, null,"");
        });

        String mensaje = exception.getMessage();
        assertTrue(mensaje.contains("El primer número es requerido"));
        assertTrue(mensaje.contains("El segundo número es requerido"));
        assertTrue(mensaje.contains("Seleccione una operacion"));
    }


    // Tests para calcular() - Operaciones válidas
    @Test
    public void queAlSumarDosNumerosRetorneElResultadoCorrecto() {
        Double resultado = servicioCalculadora.calcular(5.0, 3.0, "sumar");
        assertThat(resultado, is(8.0));
    }

    @Test
    public void queAlRestarDosNumerosRetorneElResultadoCorrecto() {
        Double resultado = servicioCalculadora.calcular(5.0, 3.0, "restar");
        assertThat(resultado, is(2.0));
    }

    @Test
    public void queAlMultiplicarDosNumerosRetorneElResultadoCorrecto() {
        Double resultado = servicioCalculadora.calcular(5.0, 3.0, "multiplicar");
        assertThat(resultado, is(15.0));
    }

    @Test
    public void queAlDividirDosNumerosRetorneElResultadoCorrecto() {
        Double resultado = servicioCalculadora.calcular(6.0, 3.0, "dividir");
        assertThat(resultado, is(2.0));
    }

    //Test division por cero
    @Test
    public void queAlDividirPorCeroLanceExcepcion() {
        Exception exception = assertThrows(DivisionPorCeroException.class, () -> {
            servicioCalculadora.calcular(5.0, 0.0, "dividir");
        });
        assertThat(exception.getMessage(), is("No se puede dividir por cero."));
    }

}
