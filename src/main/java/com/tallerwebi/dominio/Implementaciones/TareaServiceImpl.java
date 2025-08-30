package com.tallerwebi.dominio.Implementaciones;

import com.tallerwebi.dominio.entidades.Tarea;
import com.tallerwebi.dominio.interfaces.TareaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TareaServiceImpl implements TareaService {

    private final List<Tarea> tareas = new ArrayList<>();
    private long nextId = 1;

    @Override
    public void agregar(Tarea tarea) {
        if (tarea.getId() == null) {
            tarea.setId(nextId++);
        }
        tareas.add(tarea);
    }

    @Override
    public List<Tarea> obtenerTodas() {
        return new ArrayList<>(tareas);
    }

    @Override
    public Tarea marcarComoCompletada(long id) {
        for (Tarea tarea : tareas) {
            if (tarea.getId().equals(id)) {
                tarea.setCompletada(true);
                return tarea;
            }
        }
        return null;
    }



}
