package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Estudiante;
import com.tallerwebi.dominio.entidades.Inscripcion;
import com.tallerwebi.dominio.interfaces.RepositorioInscripcion;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class RepositorioInscripcionImpl implements RepositorioInscripcion {
    
    private SessionFactory sessionFactory;
    
    public RepositorioInscripcionImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean existeInscripcion(Long cursoId, Long estudianteId) {
        Long count = (Long) sessionFactory.getCurrentSession()
                .createCriteria(Inscripcion.class)
                .createAlias("curso", "c")
                .createAlias("estudiante", "e")
                .add(Restrictions.eq("c.id", cursoId))
                .add(Restrictions.eq("e.id", estudianteId))
                .setProjection(Projections.rowCount())
                .uniqueResult();
        return count > 0;
    }

    @Override
    public void guardar(Inscripcion inscripcion) {
        sessionFactory.getCurrentSession().save(inscripcion);
    }

    @Override
    public Set<Estudiante> obtenerEstudiantesPorCurso(Long cursoId) {
        return new HashSet<>(sessionFactory.getCurrentSession()
                .createCriteria(Inscripcion.class, "i")
                .createAlias("i.estudiante", "e")
                .createAlias("i.curso", "c")
                .add(Restrictions.eq("c.id", cursoId))
                .setProjection(Projections.property("e")) //Equivalente: `SELECT e.*`
                .list());
    }
    
//    @Override
//    public Set<Estudiante> obtenerEstudiantesPorCurso(Long cursoId) {
//        return new HashSet<>(sessionFactory.getCurrentSession()
//                .createCriteria(Estudiante.class, "e")
//                .createAlias("e.inscripciones", "i")
//                .createAlias("i.curso", "c")
//                .add(Restrictions.eq("c.id", cursoId))
//                .list());
//    }

    @Override
    public boolean buscarPorId(Long id) {
        return false;
    }

    @Override
    public boolean buscarPorInscripcion(Inscripcion inscripcion) {
        Long count = (Long) sessionFactory.getCurrentSession()
                .createCriteria(Inscripcion.class)
                .add(Restrictions.eq("curso", inscripcion.getCurso()))
                .add(Restrictions.eq("estudiante", inscripcion.getEstudiante()))
                .setProjection(Projections.rowCount())
                .uniqueResult();
        return count > 0;
    }

//    @Override
//    public boolean buscarPorInscripcion(Inscripcion inscripcion) {
//        Long count = (Long) sessionFactory.getCurrentSession()
//                .createCriteria(Inscripcion.class)
//                .createAlias("curso", "c")
//                .createAlias("estudiante", "e")
//                .add(Restrictions.eq("c.id", inscripcion.getCurso().getId()))
//                .add(Restrictions.eq("e.id", inscripcion.getEstudiante().getId()))
//                .setProjection(Projections.rowCount())
//                .uniqueResult();
//        return count > 0;
//    }
    

}
