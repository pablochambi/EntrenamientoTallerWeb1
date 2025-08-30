package com.tallerwebi.dominio.Implementaciones;

import com.tallerwebi.dominio.entidades.Inscripcion;
import com.tallerwebi.dominio.interfaces.RepositorioInscripcion;
import com.tallerwebi.dominio.interfaces.ServicioInscripcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioInscripcionImpl implements ServicioInscripcion {

    @Autowired
    private RepositorioInscripcion repositorioInscripcion;

    public ServicioInscripcionImpl(RepositorioInscripcion repositorioInscripcion) {
        this.repositorioInscripcion = repositorioInscripcion;
    }

    @Override
    public void registrar(Inscripcion inscripcion) {

        if (repositorioInscripcion.buscarPorInscripcion(inscripcion)) {
            throw new RuntimeException("La Inscripcion ya existe");
        }

        repositorioInscripcion.guardar(inscripcion);
    }

}
