package com.tallerwebi.dominio.entidades;

public class Tarea {
    private Long id;
    private String nombre;
    private boolean completada;

    public Tarea() {}

    public Tarea(Long id, String nombre, boolean completada) {
        this.id = id;
        this.nombre = nombre;
        this.completada = completada;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public boolean isCompletada() { return completada; }
    public void setCompletada(boolean completada) { this.completada = completada; }
}
