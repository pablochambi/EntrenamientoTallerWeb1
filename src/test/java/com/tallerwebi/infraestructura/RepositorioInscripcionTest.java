package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Curso;
import com.tallerwebi.dominio.entidades.Estudiante;
import com.tallerwebi.dominio.entidades.Inscripcion;
import com.tallerwebi.dominio.interfaces.*;
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
import java.time.LocalDate;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioInscripcionTest {

    @Autowired
    RepositorioInscripcion repositorioInscripcion;

    @Autowired
    RepositorioCurso repositorioCurso;

    @Autowired
    RepositorioEstudiante repositorioEstudiante;

    @Test
    @Transactional
    @Rollback
    public void puedoGuardarYVerificarInscripcion() {
        Curso curso = new Curso();
        curso.setTitulo("HTML y CSS");
        repositorioCurso.guardar(curso);

        Estudiante estudiante = new Estudiante("Lucía Torres");
        repositorioEstudiante.guardar(estudiante);

        Inscripcion inscripcion = new Inscripcion(curso, estudiante, LocalDate.now());
        repositorioInscripcion.guardar(inscripcion);

        boolean existe = repositorioInscripcion.existeInscripcion(curso.getId(), estudiante.getId());

        assertThat(existe, is(true));
    }


    @Test
    @Transactional
    @Rollback
    public void queAlBuscarUnaInscripcionQueNoExista_MeDevuelvaFalso() {
        Curso curso = new Curso("HTML y CSS");
        repositorioCurso.guardar(curso);

        Estudiante estudiante1 = new Estudiante("Lucía Torres");
        Estudiante estudiante2 = new Estudiante("Lucía Martina");
        repositorioEstudiante.guardar(estudiante1);
        repositorioEstudiante.guardar(estudiante2);

        Inscripcion inscripcion = new Inscripcion(curso, estudiante1, LocalDate.now());
        repositorioInscripcion.guardar(inscripcion);

        boolean existe = repositorioInscripcion.existeInscripcion(curso.getId(), estudiante2.getId());

        assertThat(existe, is(false));
    }

    @Test
    @Transactional
    @Rollback
    public void puedoObtenerEstudiantesDeUnCurso() {
        Curso curso = new Curso();
        curso.setTitulo("Arquitectura de Software");
        repositorioCurso.guardar(curso);

        Estudiante estudiante = new Estudiante("Pedro Fernández");
        repositorioEstudiante.guardar(estudiante);

        Inscripcion inscripcion = new Inscripcion(curso, estudiante, LocalDate.now());
        repositorioInscripcion.guardar(inscripcion);

        Set<Estudiante> estudiantes = repositorioInscripcion.obtenerEstudiantesPorCurso(curso.getId());

        assertThat(estudiantes, hasItem(estudiante));

    }
}
