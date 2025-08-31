package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.entidades.Proyecto;

import java.util.List;

public interface ProyectoService {
    void guardar(Proyecto proyecto);
    Proyecto obtenerPorId(Long id);
    List<Proyecto> obtenerTodos();
}
