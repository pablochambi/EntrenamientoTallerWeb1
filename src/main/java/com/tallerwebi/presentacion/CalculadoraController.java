package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.excepcion.CampoNuloException;
import com.tallerwebi.dominio.excepcion.DivisionPorCeroException;
import com.tallerwebi.dominio.interfaces.ServicioCalculadora;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CalculadoraController {

    private ServicioCalculadora servicioCalculadora;
    private static final List<String> OPERACIONES = List.of("sumar", "restar", "multiplicar", "dividir");

    @Autowired
    public CalculadoraController(ServicioCalculadora servicioCalculadora) {
        this.servicioCalculadora = servicioCalculadora;
    }

    @GetMapping("/calculadora")
    public ModelAndView irACalculadora(
            @RequestParam(required = false) Double num1,
            @RequestParam(required = false) Double num2,
            @RequestParam(required = false) String operacion,
            @ModelAttribute("error") String error) {  // Cambiado a @ModelAttribute para flash

        ModelMap model = new ModelMap();
        model.put("operaciones", OPERACIONES);

        repoblarSiSonValoresNoNulos(model,num1, num2, operacion);
        agregarErrorSiNoEsNulo(model, "error", error);

        return new ModelAndView("calculadora", model);
    }


    @GetMapping("/resultado")
    public ModelAndView calcular(
            @RequestParam Double num1,
            @RequestParam Double num2,
            @RequestParam String operacion,
            RedirectAttributes redirectAttributes) {

        try {
            Double resultado = servicioCalculadora.calcular(num1, num2, operacion);
            ModelMap model = crearModeloResultado(num1, num2, operacion, resultado);
            return new ModelAndView("vista_resultado", model);

        } catch (CampoNuloException | DivisionPorCeroException e) {
            // 4. En caso de error: agregar flash message y params para repoblar
            redirigirConError(redirectAttributes, num1, num2, operacion, e.getMessage());
            return new ModelAndView("redirect:/calculadora");
        }
    }


    private void repoblarSiSonValoresNoNulos(ModelMap model,Double num1, Double num2, String operacion) {
        agregarSiNoNulo(model, "num1", num1);
        agregarSiNoNulo(model, "num2", num2);
        agregarSiNoNulo(model, "operacion", operacion);
    }

    /**
     * Crea un ModelMap con los atributos necesarios para mostrar el resultado.
     */
    private ModelMap crearModeloResultado(
            Double num1,
            Double num2,
            String operacion,
            Double resultado) {

        ModelMap model = new ModelMap();
        model.put("num1", num1);
        model.put("num2", num2);
        model.put("operacion", operacion);
        model.put("resultado", resultado);
        return model;
    }

    /**
     * Agrega al RedirectAttributes el flash‐attribute "error" con el mensaje proporcionado,
     * y repuebla los parámetros num1, num2 y operacion como query params.
     */
    private void redirigirConError(
            RedirectAttributes redirectAttributes,
            Double num1,
            Double num2,
            String operacion,
            String mensajeError) {

        redirectAttributes.addFlashAttribute("error", mensajeError);
        redirectAttributes.addAttribute("num1", num1);
        redirectAttributes.addAttribute("num2", num2);
        redirectAttributes.addAttribute("operacion", operacion);
    }





    private void agregarSiNoNulo(ModelMap model, String clave, Object valor) {
        if (valor != null) {
            model.put(clave, valor);
        }
    }
    private void agregarErrorSiNoEsNulo(ModelMap model, String clave, String valor) {
        if (valor != null) {
            model.put(clave, valor);
        }
    }


//    @GetMapping("/resultado")
//    public ModelAndView calcular(
//            @RequestParam Double num1,
//            @RequestParam Double num2,
//            @RequestParam String operacion,
//            RedirectAttributes redirectAttributes) {
//
//        try {
//            Double resultado = servicioCalculadora.calcular(num1, num2, operacion);
//            ModelMap model = new ModelMap();
//            model.put("num1", num1);
//            model.put("num2", num2);
//            model.put("operacion", operacion);
//            model.put("resultado", resultado);
//            return new ModelAndView("vista_resultado", model);
//
//        } catch (CampoNuloException | DivisionPorCeroException e){
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//            redirectAttributes.addAttribute("num1", num1);
//            redirectAttributes.addAttribute("num2", num2);
//            redirectAttributes.addAttribute("operacion", operacion);
//            return new ModelAndView("redirect:/calculadora");
//        }
//
//    }


}

