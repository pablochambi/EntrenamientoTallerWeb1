package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Implementaciones.TareaServiceImpl;
import com.tallerwebi.dominio.entidades.Proyecto;
import com.tallerwebi.dominio.entidades.Tarea;
import com.tallerwebi.dominio.interfaces.RepositorioProyecto;
import com.tallerwebi.dominio.interfaces.TareaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Service
public class TereaServiceTest {

    private TareaService tareaService;
    private RepositorioProyecto repositorioProyecto;

    @BeforeEach
    public void setUp() {
        repositorioProyecto = mock(RepositorioProyecto.class);
        tareaService = new TareaServiceImpl(repositorioProyecto);
        when(repositorioProyecto.buscarPorId(1L)).thenReturn(new Proyecto(1L, "Proyecto 1"));
    }


    @Test
    public void testObtenerTodasVacio() {
        List<Tarea> result = tareaService.obtenerTodas();
        assertTrue(result.isEmpty());
    }

    @Test
    public void queSePuedaObtenerLasDosTareasAgregadas() {
        givenHay2TareasAgregadasALaBaseDeDatos();
        List<Tarea> result = whenSeObtienenTodasLasTareas();
        thenSeObtienenLasTareas(result);
    }

    private List<Tarea> whenSeObtienenTodasLasTareas() {
        return tareaService.obtenerTodas();
    }

    @Test
    public void testAgregarTarea() {
        givenNoHayTarea();
        Tarea tarea = whenAgregamosUnaTarea(1L, "Nueva Tarea", false);
        thenSeAgregoConExitoUnaTarea(tarea);
    }

    private Tarea whenAgregamosUnaTarea(Long id, String nombre, Boolean completada) {
        Tarea tarea = new Tarea(id, nombre, completada);
        tareaService.agregar(1L, tarea);
        return tarea;
    }

    private void thenSeAgregoConExitoUnaTarea(Tarea tarea) {
        assertThat(tarea, is(notNullValue()));
    }

    @Test
    public void testMarcarComoCompletada() {
        // Prueba: Marcar una tarea existente como completada
        Tarea tarea = new Tarea(1L, "Tarea 1", false);
        tareaService.agregar(1L, tarea);
        Tarea tarea2 = whenMarcarUnaTareaComoCompletada(1L);
        thenSeMarcoCorrectamenteComoCompletada(tarea2);
    }

    private void thenSeMarcoCorrectamenteComoCompletada(Tarea tarea) {
        assertThat(tarea, is(notNullValue()));
        assertTrue(tarea.isCompletada(), "La tarea debería estar completada");
    }

    private Tarea whenMarcarUnaTareaComoCompletada(Long id) {
        return tareaService.marcarComoCompletada(id);
    }

    @Test
    public void testMarcarComoCompletadaTareaNoExistente() {
        // Prueba: Intentar marcar una tarea inexistente (no debería fallar, pero no cambia nada)
        Tarea tarea = tareaService.marcarComoCompletada(999L);
        assertNull(tarea);
    }

    private void givenHay2TareasAgregadasALaBaseDeDatos() {
        Tarea tarea1 = new Tarea(1L, "Tarea 1", false);
        Tarea tarea2 = new Tarea(2L, "Tarea 2", true);
        tareaService.agregar(1L, tarea1);
        tareaService.agregar(1L, tarea2);
    }

    private void thenSeObtienenLasTareas(List<Tarea> result) {
        assertEquals(2, result.size(), "Debería haber 2 tareas");
        assertEquals("Tarea 1", result.get(0).getNombre());
        assertEquals("Tarea 2", result.get(1).getNombre());
        assertFalse(result.get(0).isCompletada());
        assertTrue(result.get(1).isCompletada());
    }

    private void givenNoHayTarea() {
    }
}
