package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.entidades.Curso;
import com.tallerwebi.dominio.interfaces.RepositorioCurso;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class RepositorioCursoImpl implements RepositorioCurso {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioCursoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Curso guardar(Curso curso) {
        sessionFactory.getCurrentSession().save(curso);
        return curso;
    }

    @Override
    public Curso buscarPorId(Long cursoId) {
        return sessionFactory.getCurrentSession().get(Curso.class, cursoId);
    }

//    @Override
//    public Curso buscarPorId(Long cursoId) {
//        return (Curso) sessionFactory.getCurrentSession().createCriteria(Curso.class)
//                .add(Restrictions.eq("id", cursoId))
//                .uniqueResult();
//    }
    
    
}
