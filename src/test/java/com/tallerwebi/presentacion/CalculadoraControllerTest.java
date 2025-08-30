package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.excepcion.CampoNuloException;
import com.tallerwebi.dominio.excepcion.DivisionPorCeroException;
import com.tallerwebi.dominio.interfaces.ServicioCalculadora;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.Mockito.*;


public class CalculadoraControllerTest {

    private ServicioCalculadora servicioCalculadora;
    private CalculadoraController calculadoraController;
    private RedirectAttributes mockRedirectAttributes;
    private final Double NUMERO1 = 5.0;
    private final Double NUMERO2 = 3.0;

    @BeforeEach
    public void inicializacion() {
        servicioCalculadora = mock(ServicioCalculadora.class);
        calculadoraController = new CalculadoraController(servicioCalculadora);
        mockRedirectAttributes = mock(RedirectAttributes.class);
    }

    // 1) Tests para la carga de la vista de calculadora
    @Test
    public void queSePuedaIrALaPaginaDeCalculadora_ConLaListaDeOperaciones() {
        ModelAndView mav = calculadoraController.irACalculadora(null,null,null,null);

        assertThat(mav.getViewName(), equalToIgnoringCase("calculadora"));
        assertThat(mav.getModelMap().get("operaciones"), notNullValue());
        assertThat(mav.getModelMap().get("operaciones"), instanceOf(List.class));
        assertThat(mav.getModelMap().containsKey("operaciones"), is(true));
    }

    @Test
    public void queElFormularioTengaLosElementosEsperados() {
        ModelAndView mav = calculadoraController.irACalculadora(null,null,null,null);

        @SuppressWarnings("unchecked")
        List<String> operaciones = (List<String>) mav.getModelMap().get("operaciones");

        assertThat(operaciones, not(empty()) );
        assertThat(operaciones, containsInAnyOrder(
                "sumar",
                "restar",
                "multiplicar",
                "dividir"
        ));
    }

    // 2) Tests para repoblamiento de parámetros y mensajes de error en la vista

    @Test
    public void queSeMuestreErrorEnVistaCalculadora_CuandoExiste() {

        String error = "No se puede dividir por cero";

        ModelAndView mav = calculadoraController.irACalculadora(5.0, 0.0, "dividir", error);

        assertThat(mav.getViewName(), equalToIgnoringCase("calculadora"));
        assertThat(mav.getModelMap().get("error"), is("No se puede dividir por cero"));
        assertThat(mav.getModelMap().get("num1"), is(5.0));
        assertThat(mav.getModelMap().get("num2"), is(0.0));
        assertThat(mav.getModelMap().get("operacion"), is("dividir"));
    }

    // 3) Tests para operaciones exitosas
    @Test
    public void queAlSumarDosNumerosElResultadoSeaExitoso() {
        testOperacionExitosa("sumar", NUMERO1 + NUMERO2);
    }

    @Test
    public void queAlRestarDosNumerosElResultadoSeaExitoso() {
        testOperacionExitosa("restar", NUMERO1 - NUMERO2);
    }
    @Test
    public void queAlMultiplicarDosNumerosElResultadoSeaExitoso() {
        testOperacionExitosa("multiplicar", NUMERO1 * NUMERO2);
    }
    @Test
    public void queAlDividirDosNumerosElResultadoSeaExitoso() {
        testOperacionExitosa("dividir", NUMERO1 / NUMERO2);
    }

    //Test para campos nulos
    @Test
    public void siNoSeIngresaElNumero1_DeberiaMostrarError() {
        testCampoNulo(null, 3.0, "sumar", "El primer número es requerido");
    }
    @Test
    public void siNoSeIngresaElNumero1y2_DeberiaMostrarError() {
        testCampoNulo(null, null, "sumar", "El primer número es requerido.\n El segundo número es requerido");
    }

    @Test
    public void siNoSeIngresaElNumero2_DeberiaMostrarError() {
        testCampoNulo(5.0, null, "restar", "El segundo número es requerido");
    }

    @Test
    public void siNoSeIngresaLaOperacion_DeberiaMostrarError() {
        testCampoNulo(5.0, 3.0, "", "Seleccione una operacion.");
    }

    private void testCampoNulo(Double n1, Double n2, String operacion, String mensajeEsperado) {
        // Arrange
        doThrow(new CampoNuloException(mensajeEsperado))
                .when(servicioCalculadora)
                .calcular(n1, n2, operacion);

        // Act
        ModelAndView mav = calculadoraController.calcular(n1, n2, operacion, mockRedirectAttributes);

        // Assert
        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/calculadora"));

        verify(mockRedirectAttributes).addFlashAttribute("error", mensajeEsperado);
        verify(mockRedirectAttributes).addAttribute("num1", n1);
        verify(mockRedirectAttributes).addAttribute("num2", n2);
        verify(mockRedirectAttributes).addAttribute("operacion", operacion);
    }


    @Test
    public void queDivisionPorCeroManejeError() {
        when(servicioCalculadora.calcular(anyDouble(), eq(0.0), eq("dividir")))
                .thenThrow(new DivisionPorCeroException("División por cero"));

        ModelAndView mav = calculadoraController.calcular(10.0, 0.0, "dividir", mockRedirectAttributes);

        // Verificar que se agrega mensaje de error y se redirige
        verify(mockRedirectAttributes).addFlashAttribute("error", "División por cero");
        assertThat(mav.getViewName(), is("redirect:/calculadora"));
    }


    private void testOperacionExitosa(String operacion, Double resultadoEsperado) {
        // Configurar el mock
        when(servicioCalculadora.calcular(NUMERO1, NUMERO2, operacion))
                .thenReturn(resultadoEsperado);

        // Ejecutar
        ModelAndView mav = calculadoraController.calcular(NUMERO1, NUMERO2, operacion,mockRedirectAttributes);

        // Verificar
        ModelMap model = mav.getModelMap();
        assertThat(mav.getViewName(), equalToIgnoringCase("vista_resultado"));
        assertThat(model.get("num1"), is(NUMERO1));
        assertThat(model.get("num2"), is(NUMERO2));
        assertThat(model.get("operacion"), is(operacion));
        assertThat(model.get("resultado"), is(resultadoEsperado));
        verify(servicioCalculadora).calcular(NUMERO1, NUMERO2, operacion);
    }


}
