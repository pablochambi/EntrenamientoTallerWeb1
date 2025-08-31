package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.entidades.Tarea;

import java.util.List;

public interface RepositorioTarea {
    void guardar(Tarea tarea);
    Tarea buscarPorId(Long id);
    List<Tarea> buscarTodas();
    List<Tarea> buscarPorProyecto(Long proyectoId);
    void eliminar(Tarea tarea);
}
