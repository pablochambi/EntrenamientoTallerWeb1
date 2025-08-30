package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Curso;
import com.tallerwebi.dominio.interfaces.RepositorioCurso;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.hibernate.SessionFactory;
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
public class RepositorioCursoTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private RepositorioCurso repositorioCurso;

    @Test
    @Transactional
    @Rollback
    public void puedoGuardarCurso() {
        Curso curso = new Curso();
        curso.setTitulo("Java Intermedio");

        Curso guardado = repositorioCurso.guardar(curso);

        assertThat(guardado.getId(), notNullValue());
        assertThat(guardado.getTitulo(), equalTo("Java Intermedio"));
    }

    @Test
    @Transactional
    @Rollback
    public void puedoBuscarCursoPorId() {
        Curso curso = new Curso();
        curso.setTitulo("Python Avanzado");

        Curso guardado = repositorioCurso.guardar(curso);
        Curso buscado = repositorioCurso.buscarPorId(guardado.getId());

        assertThat(buscado, notNullValue());
        assertThat(buscado.getId(), equalTo(guardado.getId()));
        assertThat(buscado.getTitulo(), equalTo("Python Avanzado"));
    }


}

