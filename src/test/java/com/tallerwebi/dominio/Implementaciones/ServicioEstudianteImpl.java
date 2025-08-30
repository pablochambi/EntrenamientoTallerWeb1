package com.tallerwebi.dominio.Implementaciones;

import com.tallerwebi.dominio.entidades.Estudiante;
import com.tallerwebi.dominio.interfaces.RepositorioEstudiante;
import com.tallerwebi.dominio.interfaces.ServicioEstudiante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioEstudianteImpl implements ServicioEstudiante {

    @Autowired
    private RepositorioEstudiante repositorioEstudiantes;

    public ServicioEstudianteImpl(RepositorioEstudiante repositorioEstudiantes) {
        this.repositorioEstudiantes = repositorioEstudiantes;
    }

    @Override
    public void registrar(Estudiante estudiante) {
        if (repositorioEstudiantes.buscarPorId(estudiante.getId()) != null) {
            throw new RuntimeException("El curso ya existe");
        }
        repositorioEstudiantes.guardar(estudiante);
    }
}
