package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.Proyecto;
import com.tallerwebi.dominio.interfaces.ProyectoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProyectoControllerTest {

    private ProyectoController proyectoController;
    private ProyectoService proyectoService;

    @BeforeEach
    public void setUp() {
        proyectoService = mock(ProyectoService.class);
        proyectoController = new ProyectoController(proyectoService);
    }

    @Test
    public void queSePuedanListarTodosLosProyectos() {
        // Preparación
        List<Proyecto> proyectos = new ArrayList<>();
        proyectos.add(new Proyecto(1L, "Proyecto 1"));
        proyectos.add(new Proyecto(2L, "Proyecto 2"));
        when(proyectoService.obtenerTodos()).thenReturn(proyectos);

        // Ejecución
        ModelAndView modelAndView = proyectoController.listarProyectos();

        // Verificación
        assertEquals("proyectos", modelAndView.getViewName());
        assertEquals(proyectos, modelAndView.getModel().get("proyectos"));
    }

    @Test
    public void queSePuedaMostrarElFormularioParaCrearUnProyecto() {
        // Ejecución
        ModelAndView modelAndView = proyectoController.nuevoProyecto();

        // Verificación
        assertEquals("nuevo-proyecto", modelAndView.getViewName());
    }

    @Test
    public void queSePuedaCrearUnNuevoProyecto() {
        // Preparación
        String nombreProyecto = "Nuevo Proyecto";

        // Ejecución
        ModelAndView modelAndView = proyectoController.crearProyecto(nombreProyecto);

        // Verificación
        assertEquals("redirect:/proyectos", modelAndView.getViewName());
    }

    @Test
    public void queSePuedaVerElDetalleDeUnProyecto() {
        // Preparación
        Long idProyecto = 1L;
        Proyecto proyecto = new Proyecto(idProyecto, "Proyecto 1");
        when(proyectoService.obtenerPorId(idProyecto)).thenReturn(proyecto);

        // Ejecución
        ModelAndView modelAndView = proyectoController.verProyecto(idProyecto);

        // Verificación
        assertEquals("detalle-proyecto", modelAndView.getViewName());
        assertEquals(proyecto, modelAndView.getModel().get("proyecto"));
    }
}
