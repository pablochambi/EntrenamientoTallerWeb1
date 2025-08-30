package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.entidades.Estudiante;
import com.tallerwebi.dominio.entidades.Inscripcion;

import java.util.Set;

public interface RepositorioInscripcion {
    boolean existeInscripcion(Long cursoId, Long estudianteId);

    void guardar(Inscripcion inscripcion);

    Set<Estudiante> obtenerEstudiantesPorCurso(Long cursoId);

    boolean buscarPorId(Long id);

    boolean buscarPorInscripcion(Inscripcion inscripcion);
}
