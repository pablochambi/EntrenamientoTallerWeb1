package com.tallerwebi.dominio.Implementaciones;

import com.tallerwebi.dominio.entidades.Proyecto;
import com.tallerwebi.dominio.entidades.Tarea;
import com.tallerwebi.dominio.interfaces.RepositorioProyecto;
import com.tallerwebi.dominio.interfaces.RepositorioTarea;
import com.tallerwebi.dominio.interfaces.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TareaServiceImpl implements TareaService {

    private final RepositorioProyecto repositorioProyecto;
    private final RepositorioTarea repositorioTarea;

    @Autowired
    public TareaServiceImpl(RepositorioProyecto repositorioProyecto, RepositorioTarea repositorioTarea) {
        this.repositorioProyecto = repositorioProyecto;
        this.repositorioTarea = repositorioTarea;
    }

    @Override
    public void agregar(Long proyectoId, Tarea tarea) {
        Proyecto proyecto = repositorioProyecto.buscarPorId(proyectoId);
        if (proyecto != null) {
            tarea.setProyecto(proyecto);
            repositorioTarea.guardar(tarea);
        }
    }

    @Override
    public List<Tarea> obtenerTodas() {
        return repositorioTarea.buscarTodas();
    }

    @Override
    public List<Tarea> obtenerPorProyecto(Long proyectoId) {
        return repositorioTarea.buscarPorProyecto(proyectoId);
    }

    @Override
    public Tarea marcarComoCompletada(long id) {
        Tarea tarea = repositorioTarea.buscarPorId(id);
        if (tarea != null) {
            tarea.setCompletada(true);
            repositorioTarea.guardar(tarea);
        }
        return tarea;
    }

    @Override
    public Tarea obtenerPorId(Long id) {
        return repositorioTarea.buscarPorId(id);
    }

    @Override
    public Long eliminar(Long id) {
        Tarea tarea = repositorioTarea.buscarPorId(id);
        if (tarea != null) {
            Long proyectoId = tarea.getProyecto().getId();
            repositorioTarea.eliminar(tarea);
            return proyectoId;
        }
        return null;
    }

    @Override
    public void actualizar(Tarea tarea) {
        repositorioTarea.guardar(tarea);
    }
}
