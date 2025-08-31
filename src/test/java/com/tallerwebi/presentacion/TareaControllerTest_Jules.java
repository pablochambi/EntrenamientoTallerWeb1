package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.Proyecto;
import com.tallerwebi.dominio.entidades.Tarea;
import com.tallerwebi.dominio.interfaces.TareaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TareaControllerTest_Jules {

    private TareaController tareaController;
    private TareaService tareaService;

    @BeforeEach
    public void setUp() {
        tareaService = mock(TareaService.class);
        tareaController = new TareaController(tareaService);
    }

    @Test
    public void queSePuedaAgregarUnaTareaAUnProyecto() {
        // Preparación
        Long proyectoId = 1L;
        String nombreTarea = "Nueva Tarea";

        // Ejecución
        ModelAndView modelAndView = tareaController.agregarTarea(proyectoId, nombreTarea);

        // Verificación
        assertEquals("redirect:/proyectos/1/tareas", modelAndView.getViewName());
        verify(tareaService, times(1)).agregar(eq(proyectoId), any(Tarea.class));
    }

    @Test
    public void queSePuedanObtenerTodasLasTareasDeUnProyecto() {
        // Preparación
        Long idProyecto = 1L;
        Proyecto proyecto = new Proyecto(idProyecto, "Proyecto 1");
        List<Tarea> tareas = new ArrayList<>();
        tareas.add(new Tarea(1L, "Tarea 1", false));
        tareas.add(new Tarea(2L, "Tarea 2", false));
        proyecto.setTareas(tareas);

        when(tareaService.obtenerPorProyecto(idProyecto)).thenReturn(tareas);

        // Ejecución
        ModelAndView modelAndView = tareaController.mostrarTareasPorProyecto(idProyecto);

        // Verificación
        assertEquals("tareas", modelAndView.getViewName());
        List<Tarea> tareasObtenidas = (List<Tarea>) modelAndView.getModel().get("tareas");
        assertEquals(2, tareasObtenidas.size());
    }

    @Test
    public void queSePuedaMarcarUnaTareaComoCompletada() {
        // Preparación
        Long idTarea = 1L;
        Long idProyecto = 1L;
        Proyecto proyecto = new Proyecto(idProyecto, "Proyecto 1");
        Tarea tarea = new Tarea(idTarea, "Tarea 1", false);
        tarea.setProyecto(proyecto);
        when(tareaService.marcarComoCompletada(idTarea)).thenReturn(tarea);

        // Ejecución
        ModelAndView modelAndView = tareaController.marcarComoCompletada(idTarea);

        // Verificación
        assertEquals("redirect:/proyectos/1/tareas", modelAndView.getViewName());
        verify(tareaService, times(1)).marcarComoCompletada(idTarea);
    }

    @Test
    public void queSePuedaObtenerUnaTareaPorId() {
        // Preparación
        Long idTarea = 1L;
        Tarea tarea = new Tarea(idTarea, "Tarea 1", false);
        when(tareaService.obtenerPorId(idTarea)).thenReturn(tarea);

        // Ejecución
        ModelAndView modelAndView = tareaController.obtenerTareaPorId(idTarea);

        // Verificación
        assertEquals("detalle-tarea", modelAndView.getViewName());
        assertEquals(tarea, modelAndView.getModel().get("tarea"));
    }

    @Test
    public void queSePuedaEliminarUnaTarea() {
        // Preparación
        Long idTarea = 1L;
        Long idProyecto = 1L;
        when(tareaService.eliminar(idTarea)).thenReturn(idProyecto);

        // Ejecución
        ModelAndView modelAndView = tareaController.eliminarTarea(idTarea);

        // Verificación
        assertEquals("redirect:/proyectos/1/tareas", modelAndView.getViewName());
        verify(tareaService, times(1)).eliminar(idTarea);
    }

    @Test
    public void queSePuedaActualizarUnaTarea() {
        // Preparación
        Long idTarea = 1L;
        Long idProyecto = 1L;
        Proyecto proyecto = new Proyecto(idProyecto, "Proyecto 1");
        Tarea tarea = new Tarea(idTarea, "Tarea 1", false);
        tarea.setProyecto(proyecto);
        when(tareaService.obtenerPorId(idTarea)).thenReturn(tarea);

        // Ejecución
        ModelAndView modelAndView = tareaController.actualizarTarea(idTarea, "Tarea Actualizada", true);

        // Verificación
        assertEquals("redirect:/proyectos/1/tareas", modelAndView.getViewName());
        verify(tareaService, times(1)).actualizar(any(Tarea.class));
    }
}
