package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.entidades.Tarea;

import java.util.List;

public interface TareaService {

    void agregar(Long proyectoId, Tarea tarea1);

    List<Tarea> obtenerTodas();

    List<Tarea> obtenerPorProyecto(Long proyectoId);

    Tarea marcarComoCompletada(long id);

    Tarea obtenerPorId(Long id);

    Long eliminar(Long id);

    void actualizar(Tarea tarea);
}
