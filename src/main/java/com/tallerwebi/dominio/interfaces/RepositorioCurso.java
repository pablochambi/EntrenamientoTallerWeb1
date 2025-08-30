package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.entidades.Curso;

public interface RepositorioCurso {
    Curso guardar(Curso curso);

    Curso buscarPorId(Long cursoId);
}
