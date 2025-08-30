package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Estudiante;
import com.tallerwebi.dominio.interfaces.RepositorioEstudiante;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioEstudiantesImpl implements RepositorioEstudiante {
    
    private SessionFactory sessionFactory;
    
    @Autowired
    public RepositorioEstudiantesImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Estudiante buscarPorId(Long estudianteId) {
        return sessionFactory.getCurrentSession().get(Estudiante.class, estudianteId);
    }

    @Override
    public void guardar(Estudiante estudiante) {
        sessionFactory.getCurrentSession().save(estudiante);
    }
}
