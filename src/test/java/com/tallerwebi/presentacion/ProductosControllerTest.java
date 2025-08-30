package com.tallerwebi.presentacion;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class ProductosControllerTest {

    private ProductoController productoController;

    @BeforeEach
    void setUp() {
        productoController = new ProductoController();
    }

    @Test
    void queAlListarProductosSeDevuelvaLaVistaProductos() {
        ModelAndView mav = whenListarProductos();
        thenLaVistaDebeSer(mav.getViewName(), "productos");
    }

    @Test
    void queAlListarProductosElModeloContengaLaListaDeProductos() {
        ModelAndView mav = whenListarProductos();
        thenElModeloContieneListaDeProductos(mav.getModel().get("productos"));
    }

    @Test
    void queAlListarProductosSeIncluyaUnaListaDeProductosEnElModelo() {
        ModelAndView mav = whenListarProductos();
        thenDebeHaberUnaListaDeProductos(mav);
    }

    private void thenDebeHaberUnaListaDeProductos(ModelAndView mav) {
        Object productos = mav.getModel().get("productos");
        assertThat(productos, is(notNullValue()));
        assertThat(productos, instanceOf(List.class));
        List<?> lista = (List<?>) productos;
        assertThat(lista, is(not(empty())));
    }


    @Test
    void queAlVerDetalleDeProductoExistenteSeMuestreVistaDetalle() {
        Long idExistente = givenUnProductoExistente().getId();
        ModelAndView mav = whenVerDetalleDeProducto(idExistente);
        thenLaVistaDebeSer(mav.getViewName(), "detalle");
    }

    @Test
    void queAlVerDetalleDeProductoExistenteElModeloContengaElProducto() {
        Producto productoEsperado = givenUnProductoExistente();
        ModelAndView mav = whenVerDetalleDeProducto(productoEsperado.getId());
        thenElModeloContieneElProducto((Producto) mav.getModel().get("producto"), productoEsperado);
    }

    @Test
    void queAlVerDetalleDeProductoInexistenteElModeloNoContengaProducto() {
        Long idInexistente = 99L;
        ModelAndView mav = whenVerDetalleDeProducto(idInexistente);
        thenElModeloNoContieneProducto((Producto) mav.getModel().get("producto"));
    }

    // --- Givens ---

    private Producto givenUnProductoExistente() {
        System.out.println(productoController.listarProductosHardcodeados().get(0));
        return productoController.listarProductosHardcodeados().get(0); // asumo el primero
    }

    private Long givenUnIdDeProductoInexistente() {
        return 3L; // id que no existe en la lista hardcodeada
    }

    // --- Whens ---

    private ModelAndView whenListarProductos() {
        return productoController.listarProductos();
    }

    private ModelAndView whenVerDetalleDeProducto(Long id) {
        return productoController.verDetalle(id);
    }

    // --- Thens ---

    private void thenLaVistaDebeSer(String vistaObtenida, String vistaEsperada) {
        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
    }

    private void thenElModeloContieneListaDeProductos(Object productos) {
        assertThat(productos instanceof List, equalTo(true));
        assertThat(!((List<?>) productos).isEmpty(), equalTo(true));
    }

    private void thenElModeloContieneElProducto(Producto productoObtenido, Producto productoEsperado) {
        Producto actual = (Producto) productoObtenido;
        assertThat(actual.getId(), equalTo(productoEsperado.getId()));
        assertThat(actual.getNombre(), equalTo(productoEsperado.getNombre()));
        assertThat(actual.getDescripcion(), equalTo(productoEsperado.getDescripcion()));
        assertThat(actual.getPrecio(), equalTo(productoEsperado.getPrecio()));
    }

    private void thenElModeloNoContieneProducto(Producto producto) {
        assertThat( producto, is(nullValue()) );
    }






}

