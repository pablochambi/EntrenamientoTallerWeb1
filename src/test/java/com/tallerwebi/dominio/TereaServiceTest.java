package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Implementaciones.TareaServiceImpl;
import com.tallerwebi.dominio.entidades.Tarea;
import com.tallerwebi.dominio.interfaces.TareaService;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@Service
public class TereaServiceTest {

    //private TareaService tareaService = Mockito.mock(TareaService.class);
    private TareaService tareaService = new TareaServiceImpl();

    private List<Tarea> tareas = new ArrayList<>();;


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
        Tarea tarea = new Tarea(1L, "Nueva Tarea", false);
        tareaService.agregar(tarea);
        return tarea;
    }

    private void thenSeAgregoConExitoUnaTarea(Tarea tarea) {

        assertThat(tarea, is(notNullValue()) );

        //List<Tarea> result = tareaService.obtenerTodas();
        // assertEquals(1, result.size(), "Debería haber 1 tarea");
       // assertEquals("Nueva Tarea", result.get(0).getNombre());
       // assertFalse(result.get(0).isCompletada());
    }


    @Test
    public void testMarcarComoCompletada() {
        // Prueba: Marcar una tarea existente como completada
        Tarea tarea = new Tarea(1L, "Tarea 1", false);
        tareaService.agregar(tarea);
        Tarea tarea2 = whenMarcarUnaTareaComoCompletada(1L);
        thenSeMarcoCorrectamenteComoCompletada(tarea2);
    }

    private void thenSeMarcoCorrectamenteComoCompletada(Tarea tarea) {
        //List<Tarea> result = tareaService.obtenerTodas();
        assertThat(tarea, is(notNullValue()));
        assertTrue(tarea.isCompletada(), "La tarea debería estar completada");
    }

    private Tarea whenMarcarUnaTareaComoCompletada(Long id) {
        return tareaService.marcarComoCompletada(1L);
    }

    @Test
    public void testMarcarComoCompletadaTareaNoExistente() {
        // Prueba: Intentar marcar una tarea inexistente (no debería fallar, pero no cambia nada)
        tareaService.marcarComoCompletada(999L);

        List<Tarea> result = tareaService.obtenerTodas();
        assertTrue(result.isEmpty(), "La lista debería seguir vacía");
    }

    private void givenHay2TareasAgregadasALaBaseDeDatos() {
        Tarea tarea1 = new Tarea(1L, "Tarea 1", false);
        Tarea tarea2 = new Tarea(2L, "Tarea 2", true);
        tareaService.agregar(tarea1);
        tareaService.agregar(tarea2);
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
