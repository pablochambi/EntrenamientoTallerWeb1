package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.entidades.Tarea;

import java.util.List;

public interface TareaService {

    void agregar(Tarea tarea1);

    List<Tarea> obtenerTodas();

    Tarea marcarComoCompletada(long id);
}
