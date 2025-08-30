package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Estudiante;
import com.tallerwebi.dominio.interfaces.RepositorioEstudiante;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioEstudianteTest {

    @Autowired
    RepositorioEstudiante repositorioEstudiante;

    @Test
    @Transactional
    @Rollback
    public void puedoGuardarYBuscarEstudiante() {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Carlos García");

        repositorioEstudiante.guardar(estudiante);

        Estudiante buscado = repositorioEstudiante.buscarPorId(estudiante.getId());

        assertThat(buscado, notNullValue());
        assertThat(buscado.getId(), notNullValue());
        assertThat(buscado.getNombre(), equalTo("Carlos García"));
    }
}

