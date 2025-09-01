package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Proyecto;
import com.tallerwebi.dominio.interfaces.RepositorioProyecto;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioProyectoImpl implements RepositorioProyecto {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioProyectoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Proyecto proyecto) {
        sessionFactory.getCurrentSession().save(proyecto);
    }

    @Override
    public Proyecto buscarPorId(Long id) {
        return sessionFactory.getCurrentSession().get(Proyecto.class, id);
    }

    @Override
    public List<Proyecto> buscarTodos() {
        return sessionFactory.getCurrentSession().createCriteria(Proyecto.class).list();
    }
}
