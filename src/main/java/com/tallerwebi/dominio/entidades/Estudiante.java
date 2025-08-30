package com.tallerwebi.dominio.entidades;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Getter
//@Setter
//@Entity
//public class Estudiante {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String nombre;
//    private String email;
//    @ManyToMany(mappedBy = "estudiantes")
//    private List<Curso> cursos;
//}


@Getter
@Setter
@Entity
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;

    // Reemplaza @ManyToMany por esto:
//    @OneToMany(mappedBy = "estudiante")
//    private List<Inscripcion> inscripciones = new ArrayList<>();

    public Estudiante() {
    }

    public Estudiante(String nombre) {
        this.id = null;
        this.nombre = nombre;
    }

    public Estudiante(String nombre, String email) {
        this.id = null;
        this.nombre = nombre;
        this.email = email;
    }
}
