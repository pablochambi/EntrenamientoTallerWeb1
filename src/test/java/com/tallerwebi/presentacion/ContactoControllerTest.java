package com.tallerwebi.presentacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;

class ContactoControllerTest {

    private ContactoController contactoController;

    @BeforeEach
    void setUp() {
        contactoController = new ContactoController();
    }

    @Test
    void queAlMostrarElFormularioDevuelvaLaVistaDeContacto() {
        ModelAndView mav = whenMostrarFormulario();
        thenLaVistaDebeSer(mav.getViewName(), "contacto");
    }

    @Test
    void queAlMostrarElFormularioElModeloContengaUnContactoVacio() {
        ModelAndView mav = whenMostrarFormulario();
        thenElModeloContieneUnContactoVacio(mav.getModel().get("contacto"));
    }

    private void thenElModeloContieneUnContactoVacio(Object contactoObtenido) {
        Contacto contacto = (Contacto) contactoObtenido;
        assertThat(contacto.getNombre(), equalTo(null));
        assertThat(contacto.getEmail(), equalTo(null));
        assertThat(contacto.getMensaje(), equalTo(null));
    }


    @Test
    void queAlProcesarElFormularioRedirijaAVistaConfirmacion() {
        Contacto contacto = givenUnFormularioDeContactoValido();
        ModelAndView mav = whenProcesarFormulario(contacto);
        thenLaVistaDebeSer(mav.getViewName(), "confirmacion");
    }

    private Contacto givenUnFormularioDeContactoValido() {
        Contacto contacto = new Contacto();
        contacto.setNombre("Alberto");
        contacto.setEmail("alb@gmail.com");
        contacto.setMensaje("Un mensaje");
        return contacto;
    }

    @Test
    void queAlProcesarElFormularioSeAgregueElContactoAlModelo() {
        Contacto contacto = givenUnFormularioDeContactoValido();

        ModelMap modelo = new ModelMap();
        modelo.put("contacto", contacto);

        ModelAndView mav = whenProcesarFormulario(contacto);

        thenElModeloContieneElContacto(mav.getModel().get("contacto"),contacto);
    }

    private void thenElModeloContieneElContacto(Object contactoObtenido, Contacto contactoEsperado) {
        Contacto actual = (Contacto) contactoObtenido;
        assertThat(actual.getNombre(), equalTo(contactoEsperado.getNombre()));
        assertThat(actual.getEmail(), equalTo(contactoEsperado.getEmail()));
        assertThat(actual.getMensaje(), equalTo(contactoEsperado.getMensaje()));
    }


    private ModelAndView whenMostrarFormulario() {
        return contactoController.mostrarFormulario();
    }

    private ModelAndView whenProcesarFormulario(Contacto contacto) {
        return contactoController.procesarFormulario(contacto);
    }

    private void thenLaVistaDebeSer(String vistaObtenida, String vistaEsperada) {
        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
    }

}

