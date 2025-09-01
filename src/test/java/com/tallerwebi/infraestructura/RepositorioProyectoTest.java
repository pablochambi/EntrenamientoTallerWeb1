package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Proyecto;
import com.tallerwebi.dominio.interfaces.RepositorioProyecto;
import com.tallerwebi.integracion.config.HibernateTestConfig;
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

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestConfig.class, RepositorioProyectoImpl.class})
@Transactional
public class RepositorioProyectoTest {

    @Autowired
    private RepositorioProyecto repositorioProyecto;

    @Test
    public void queSePuedaGuardarUnProyecto() {
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre("Proyecto de Prueba");
        repositorioProyecto.guardar(proyecto);

        Proyecto proyectoGuardado = repositorioProyecto.buscarPorId(proyecto.getId());
        assertThat(proyectoGuardado, notNullValue());
        assertThat(proyectoGuardado.getId(), equalTo(proyecto.getId()));
    }

    @Test
    public void queAlGuardarUnProyectoNoSeCreenDuplicados() {
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre("Proyecto de Prueba");
        repositorioProyecto.guardar(proyecto);
        repositorioProyecto.guardar(proyecto);

        List<Proyecto> proyectos = repositorioProyecto.buscarTodos();
        assertThat(proyectos, hasSize(1));
    }

    @Test
    public void queSePuedaBuscarUnProyectoPorId() {
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre("Proyecto de Prueba");
        repositorioProyecto.guardar(proyecto);

        Proyecto proyectoEncontrado = repositorioProyecto.buscarPorId(proyecto.getId());
        assertThat(proyectoEncontrado, notNullValue());
        assertThat(proyectoEncontrado.getId(), equalTo(proyecto.getId()));
    }

    @Test
    public void queSePuedanBuscarTodosLosProyectos() {
        Proyecto proyecto1 = new Proyecto();
        proyecto1.setNombre("Proyecto 1");
        repositorioProyecto.guardar(proyecto1);

        Proyecto proyecto2 = new Proyecto();
        proyecto2.setNombre("Proyecto 2");
        repositorioProyecto.guardar(proyecto2);

        List<Proyecto> proyectos = repositorioProyecto.buscarTodos();
        assertThat(proyectos, hasSize(2));
    }
}
