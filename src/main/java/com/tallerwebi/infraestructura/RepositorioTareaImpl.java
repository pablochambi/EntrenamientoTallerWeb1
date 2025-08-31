package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Tarea;
import com.tallerwebi.dominio.interfaces.RepositorioTarea;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioTareaImpl implements RepositorioTarea {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioTareaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Tarea tarea) {
        sessionFactory.getCurrentSession().save(tarea);
    }

    @Override
    public Tarea buscarPorId(Long id) {
        return sessionFactory.getCurrentSession().get(Tarea.class, id);
    }

    @Override
    public List<Tarea> buscarTodas() {
        return sessionFactory.getCurrentSession().createCriteria(Tarea.class).list();
    }

    @Override
    public List<Tarea> buscarPorProyecto(Long proyectoId) {
        return sessionFactory.getCurrentSession().createCriteria(Tarea.class)
                .createAlias("proyecto", "p")
                .add(Restrictions.eq("p.id", proyectoId))
                .list();
    }

    @Override
    public void eliminar(Tarea tarea) {
        sessionFactory.getCurrentSession().delete(tarea);
    }
}
