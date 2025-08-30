package com.tallerwebi.dominio.Implementaciones;

import com.tallerwebi.dominio.entidades.Curso;
import com.tallerwebi.dominio.entidades.Estudiante;
import com.tallerwebi.dominio.entidades.Modulo;
import com.tallerwebi.dominio.entidades.Inscripcion;
import com.tallerwebi.dominio.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ServicioCursoImpl implements ServicioCurso {

    private final RepositorioCurso repositorioCurso;
    private final RepositorioModulo repositorioModulo;
    private final RepositorioEstudiante repositorioEstudiante;
    private final RepositorioInscripcion repositorioInscripcion;

    @Autowired
    public ServicioCursoImpl(RepositorioCurso repositorioCurso,
                             RepositorioModulo repositorioModulo,
                             RepositorioEstudiante repositorioEstudiante,
                             RepositorioInscripcion repositorioInscripcion) {
        this.repositorioCurso = repositorioCurso;
        this.repositorioModulo = repositorioModulo;
        this.repositorioEstudiante = repositorioEstudiante;
        this.repositorioInscripcion = repositorioInscripcion;
    }

    @Override
    public Curso crearCurso(Curso curso, List<Modulo> modulos) {

        if (repositorioCurso.buscarPorId(curso.getId()) != null) {
            throw new RuntimeException("El curso ya existe");
        }

        Curso cursoGuardado = repositorioCurso.guardar(curso);

        if (modulos != null && !modulos.isEmpty()) {
            for(Modulo modulo : modulos) {
                modulo.setCurso(cursoGuardado);
                repositorioModulo.guardar(modulo);
            }
        }

        return cursoGuardado;
    }

    @Override
    public void inscribirEstudiante(Long cursoId, Long estudianteId) {
        // Validar parámetros
        if (cursoId == null || estudianteId == null) {
            throw new IllegalArgumentException("ID de curso o estudiante no puede ser nulo");
        }

        // Buscar el curso
        Curso curso = repositorioCurso.buscarPorId(cursoId);
        if (curso == null) {
            throw new RuntimeException("No se encontró el curso con ID: " + cursoId);
        }

        // Buscar el estudiante
        Estudiante estudiante = repositorioEstudiante.buscarPorId(estudianteId);
        if (estudiante == null) {
            throw new RuntimeException("No se encontró el estudiante con ID: " + estudianteId);
        }

        // Verificar si ya está inscrito
        if (repositorioInscripcion.existeInscripcion(cursoId, estudianteId)) {
            throw new RuntimeException("El estudiante ya está inscrito en este curso.");
        }

        // Crear y guardar la nueva inscripción
        Inscripcion inscripcion = new Inscripcion(curso,estudiante,LocalDate.now());
        repositorioInscripcion.guardar(inscripcion);
    }

    @Override
    public Set<Estudiante> obtenerEstudiantesDeCurso(Long cursoId) {
        Curso curso = repositorioCurso.buscarPorId(cursoId);
        if (curso == null) {
            throw new RuntimeException("No se encontró el curso con ID: " + cursoId);
        }
        return repositorioInscripcion.obtenerEstudiantesPorCurso(cursoId);
    }

    @Override
    public void registrar(Curso curso) {

        if (repositorioCurso.buscarPorId(curso.getId()) != null) {
            throw new RuntimeException("El curso ya existe");
        }

        repositorioCurso.guardar(curso);

    }

}
