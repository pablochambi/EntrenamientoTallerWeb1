package com.tallerwebi.dominio.entidades;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Tarea> tareas = new ArrayList<>();

    public Proyecto() {}

    public Proyecto(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }

    public void agregarTarea(Tarea tarea) {
        this.tareas.add(tarea);
        tarea.setProyecto(this);
    }
}
