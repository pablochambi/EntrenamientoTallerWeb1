package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Modulo;
import com.tallerwebi.dominio.interfaces.RepositorioModulo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioModuloImpl implements RepositorioModulo {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioModuloImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Modulo modulo) {
        sessionFactory.getCurrentSession().save(modulo);
    }
}
