package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Proyecto;
import com.tallerwebi.dominio.entidades.Tarea;
import com.tallerwebi.dominio.interfaces.RepositorioProyecto;
import com.tallerwebi.dominio.interfaces.RepositorioTarea;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.tallerwebi.infraestructura.RepositorioProyectoImpl;
import com.tallerwebi.infraestructura.RepositorioTareaImpl;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestConfig.class, RepositorioTareaImpl.class, RepositorioProyectoImpl.class})
@Transactional
public class RepositorioTareaTest {

    @Autowired
    private RepositorioTarea repositorioTarea;

    @Autowired
    private RepositorioProyecto repositorioProyecto;

    private Proyecto proyecto;

    @BeforeEach
    public void setUp() {
        proyecto = new Proyecto();
        proyecto.setNombre("Proyecto de Prueba");
        repositorioProyecto.guardar(proyecto);
    }

    @Test
    public void queSePuedaGuardarUnaTarea() {
        Tarea tarea = new Tarea();
        tarea.setNombre("Tarea de prueba");
        tarea.setProyecto(proyecto);
        repositorioTarea.guardar(tarea);

        Tarea tareaGuardada = repositorioTarea.buscarPorId(tarea.getId());
        assertThat(tareaGuardada, notNullValue());
        assertThat(tareaGuardada.getId(), equalTo(tarea.getId()));
    }

    @Test
    public void queAlGuardarUnaTareaNoSeCreenDuplicados() {
        Tarea tarea = new Tarea();
        tarea.setNombre("Tarea de prueba");
        tarea.setProyecto(proyecto);
        repositorioTarea.guardar(tarea);
        repositorioTarea.guardar(tarea);

        List<Tarea> tareas = repositorioTarea.buscarTodas();
        assertThat(tareas, hasSize(1));
    }

    @Test
    public void queSePuedaBuscarUnaTareaPorId() {
        Tarea tarea = new Tarea();
        tarea.setNombre("Tarea de prueba");
        tarea.setProyecto(proyecto);
        repositorioTarea.guardar(tarea);

        Tarea tareaEncontrada = repositorioTarea.buscarPorId(tarea.getId());
        assertThat(tareaEncontrada, notNullValue());
        assertThat(tareaEncontrada.getId(), equalTo(tarea.getId()));
    }

    @Test
    public void queSePuedanBuscarTodasLasTareas() {
        Tarea tarea1 = new Tarea();
        tarea1.setNombre("Tarea 1");
        tarea1.setProyecto(proyecto);
        repositorioTarea.guardar(tarea1);

        Tarea tarea2 = new Tarea();
        tarea2.setNombre("Tarea 2");
        tarea2.setProyecto(proyecto);
        repositorioTarea.guardar(tarea2);

        List<Tarea> tareas = repositorioTarea.buscarTodas();
        assertThat(tareas, hasSize(2));
    }

    @Test
    public void queSePuedanBuscarTareasPorProyecto() {
        Tarea tarea1 = new Tarea();
        tarea1.setNombre("Tarea 1");
        tarea1.setProyecto(proyecto);
        repositorioTarea.guardar(tarea1);

        Proyecto otroProyecto = new Proyecto();
        otroProyecto.setNombre("Otro Proyecto");
        repositorioProyecto.guardar(otroProyecto);

        Tarea tarea2 = new Tarea();
        tarea2.setNombre("Tarea 2");
        tarea2.setProyecto(otroProyecto);
        repositorioTarea.guardar(tarea2);

        List<Tarea> tareas = repositorioTarea.buscarPorProyecto(proyecto.getId());
        assertThat(tareas, hasSize(1));
        assertThat(tareas.get(0).getNombre(), equalTo("Tarea 1"));
    }

    @Test
    public void queSePuedaEliminarUnaTarea() {
        Tarea tarea = new Tarea();
        tarea.setNombre("Tarea a eliminar");
        tarea.setProyecto(proyecto);
        repositorioTarea.guardar(tarea);

        repositorioTarea.eliminar(tarea);

        Tarea tareaEliminada = repositorioTarea.buscarPorId(tarea.getId());
        assertThat(tareaEliminada, nullValue());
    }
}
