package com.tallerwebi.dominio.entidades;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class Modulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer duracionHoras;

    // 👇 Este es el campo que "mapea" la relación (tiene @JoinColumn)
    @ManyToOne
//    @JoinColumn(name = "curso_id")  // Columna FK en la tabla Modulo
    private Curso curso;  // Este campo da nombre al mappedBy

    public Modulo(Long id, String nombre, Integer duracionHoras) {
        this.id = id;
        this.nombre = nombre;
        this.duracionHoras = duracionHoras;
    }

    public Modulo() {

    }
}