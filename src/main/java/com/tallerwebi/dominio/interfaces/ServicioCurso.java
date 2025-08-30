package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.entidades.Curso;
import com.tallerwebi.dominio.entidades.Estudiante;
import com.tallerwebi.dominio.entidades.Modulo;

import java.util.List;
import java.util.Set;

public interface ServicioCurso {
    Curso crearCurso(Curso curso, List<Modulo> modulos);
    void inscribirEstudiante(Long cursoId, Long estudianteId);

    Set<Estudiante> obtenerEstudiantesDeCurso(Long cursoId);

    void registrar(Curso curso);
}
