package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.interfaces.RepositorioUsuario;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration( classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioUsuarioTest {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    RepositorioUsuario repositorioUsuario;

    private final String EMAIL = "pablo@gmail.com";
    private final String PASSWORD = "1234";

    @Test
    @Transactional
    @Rollback
    public void puedoGuardarUsuario() {
        Usuario usuario = new Usuario(EMAIL,PASSWORD);

        sessionFactory.getCurrentSession().save(usuario);

        assertThat(usuario.getId(),notNullValue());
        assertThat(usuario.getEmail(),equalTo(EMAIL));

        //sessionFactory.getCurrentSession(): Obtiene la sesión actual de Hibernate desde
        // el objeto SessionFactory. Una sesión en Hibernate es como una conexión a la base de datos que
        // permite realizar operaciones como insertar, actualizar, eliminar y consultar entidades.
        //.save(usuario1): Usa la sesión obtenida para guardar la entidad usuario1 en la BD.

        // El metodo save en Hibernate marca la entidad usuario1 como persistente,
        // lo que significa que Hibernate la insertará en la base de datos temporal.
    }

    @Test
    @Transactional
    @Rollback
    public void puedoBuscarUsuarioPorEmail() {

        repositorioUsuario.guardar(new Usuario("pablo11@gmail.com","1234"));
        repositorioUsuario.guardar(new Usuario(EMAIL,PASSWORD));
        repositorioUsuario.guardar(new Usuario("pablo35@gmail.com","987"));


        Usuario buscado = repositorioUsuario.buscar(EMAIL);
        
        
        assertThat(buscado,notNullValue());
        assertThat(buscado.getId(),notNullValue());
        assertThat(buscado.getEmail(),equalTo(EMAIL));
        assertThat(buscado.getPassword(),equalTo(PASSWORD));
        
    }

    @Test
    @Transactional
    @Rollback
    public void puedoContarLaCantidadDeUsuarios() {

        repositorioUsuario.guardar(new Usuario("pablo11@gmail.com","1234"));
        repositorioUsuario.guardar(new Usuario(EMAIL,PASSWORD));
        repositorioUsuario.guardar(new Usuario("pablo35@gmail.com","987"));

        Long cantidadDeUsuarios = repositorioUsuario.getCantidadDeUsuarios();

        assertThat(cantidadDeUsuarios, equalTo(3L));
    }


}
