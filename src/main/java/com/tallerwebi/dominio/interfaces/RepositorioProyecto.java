package com.tallerwebi.dominio.interfaces;

import com.tallerwebi.dominio.entidades.Proyecto;

import java.util.List;

public interface RepositorioProyecto {
    void guardar(Proyecto proyecto);
    Proyecto buscarPorId(Long id);
    List<Proyecto> buscarTodos();
}
