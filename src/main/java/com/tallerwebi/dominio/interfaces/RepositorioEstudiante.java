package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.entidades.Estudiante;

public interface RepositorioEstudiante {
    Estudiante buscarPorId(Long estudianteId);

    void guardar(Estudiante estudiante);
}
