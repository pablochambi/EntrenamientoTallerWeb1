package com.tallerwebi.dominio.entidades;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;

    // 👇 Indica que la relación está mapeada por el campo "curso" en Modulo
//    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
//    private List<Modulo> modulos = new ArrayList<>();

    // Reemplaza @ManyToMany por esto:
//    @OneToMany(mappedBy = "curso")
//    private List<Inscripcion> inscripciones = new ArrayList<>();//Curso_Estudiante

    public Curso(String titulo) {
        this.titulo = titulo;
    }

    public Curso(Long id ,String titulo) {
        this.id = id;
        this.titulo = titulo;
    }
    public Curso() {}

}

//⚡ ¿Qué pasa en la base de datos?
//La tabla Modulo tendrá una columna curso_id (FK) que referencia a Curso.
//La tabla Curso no tiene ninguna columna adicional porque la relación está gestionada por Modulo.
