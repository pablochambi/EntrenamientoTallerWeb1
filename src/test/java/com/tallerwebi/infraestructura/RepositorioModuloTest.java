package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Curso;
import com.tallerwebi.dominio.entidades.Modulo;
import com.tallerwebi.dominio.interfaces.RepositorioCurso;
import com.tallerwebi.dominio.interfaces.RepositorioModulo;
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
public class RepositorioModuloTest {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    RepositorioModulo repositorioModulo;

    @Autowired
    RepositorioCurso repositorioCurso;

    @Test
    @Transactional
    @Rollback
    public void puedoGuardarModuloConCurso() {

        Curso curso = new Curso("Fullstack Java");
        repositorioCurso.guardar(curso);

        Modulo modulo = new Modulo();
        modulo.setNombre("Spring Boot");
        modulo.setDuracionHoras(10);
        modulo.setCurso(curso);

        repositorioModulo.guardar(modulo);

        assertThat(curso.getId(), notNullValue());
        assertThat(modulo.getId(), notNullValue());
        assertThat(modulo.getCurso().getTitulo(), equalTo("Fullstack Java"));
    }
}

