package com.tallerwebi.dominio.entidades;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@Entity
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Curso curso;

    @ManyToOne
    private Estudiante estudiante;

    // Atributos adicionales (opcional)
    private LocalDate fechaInscripcion;
    private Boolean aprobado;

    public Inscripcion(Curso curso, Estudiante estudiante, LocalDate fechaInscripcion) {
        this.id = null;
        this.curso = curso;
        this.estudiante = estudiante;
        this.fechaInscripcion = fechaInscripcion;
        this.aprobado = false;
    }

    public Inscripcion() {

    }
}
