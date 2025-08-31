package com.tallerwebi.dominio.Implementaciones;

import com.tallerwebi.dominio.entidades.Proyecto;
import com.tallerwebi.dominio.interfaces.ProyectoService;
import com.tallerwebi.dominio.interfaces.RepositorioProyecto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProyectoServiceImpl implements ProyectoService {

    private final RepositorioProyecto repositorioProyecto;

    @Autowired
    public ProyectoServiceImpl(RepositorioProyecto repositorioProyecto) {
        this.repositorioProyecto = repositorioProyecto;
    }

    @Override
    public void guardar(Proyecto proyecto) {
        repositorioProyecto.guardar(proyecto);
    }

    @Override
    public Proyecto obtenerPorId(Long id) {
        return repositorioProyecto.buscarPorId(id);
    }

    @Override
    public List<Proyecto> obtenerTodos() {
        return repositorioProyecto.buscarTodos();
    }
}
