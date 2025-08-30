package com.tallerwebi.presentacion;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

public class Producto {

    private static Long contadorIds = 0L; // arranca en 1
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;

    public Producto() {
        this.id = contadorIds++; // asigna y luego incrementa
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

}
